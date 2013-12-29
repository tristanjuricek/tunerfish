package com.tristanjuricek.tunerfish;

import be.hogent.tarsos.dsp.AudioDispatcher;
import be.hogent.tarsos.dsp.AudioEvent;
import be.hogent.tarsos.dsp.pitch.PitchDetectionHandler;
import be.hogent.tarsos.dsp.pitch.PitchDetectionResult;
import be.hogent.tarsos.dsp.pitch.PitchProcessor;
import com.tristanjuricek.tunerfish.swing.PitchModel;
import com.tristanjuricek.tunerfish.swing.PitchUtils;

import javax.sound.sampled.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manages an instance of AudioDispatcher that runs in the background.
 * <p/>
 * This will switch the audio mixer being used (the input source, basically) and
 * then fan out any pitch detection changes it receives.
 */
public class AudioDispatcherManager implements
        MixerListAdaptor.SelectionChangedListener, PitchDetectionHandler,
        PitchModel {

    private ExecutorService executorService;
    private AudioDispatcher audioDispatcher;
    private List<PitchDetectionHandler> handlerList;
    private float pitch;
    private List<ChangeListener> pitchModelListeners;

    public AudioDispatcherManager() {
        // Ensure that we shutdown and restart the audio dispatcher for each new
        // mixer
        executorService = Executors.newFixedThreadPool(1);

        audioDispatcher = null;

        handlerList = new ArrayList<PitchDetectionHandler>();

        pitch = Float.MIN_VALUE;

        pitchModelListeners = new ArrayList<ChangeListener>();
    }

    public void addPitchDetectionHandler(PitchDetectionHandler handler) {
        handlerList.add(handler);
    }

    public void removePitchDetectionHandler(PitchDetectionHandler handler) {
        handlerList.remove(handler);
    }

    /**
     * Notification received when the UI selects a new mixer.
     *
     * @see MixerListAdaptor
     */
    @Override
    public void newMixer(MixerListAdaptor.MixerInfoEvent event) {
        startReadingMixer(event.getInfo());
    }

    /**
     * Restarts the background thread processing that reads the new mixer source
     */
    public void startReadingMixer(Mixer.Info info) {
        if (audioDispatcher != null) {
            audioDispatcher.stop();
        }

        Mixer mixer = AudioSystem.getMixer(info);

        float sampleRate = 44100;
        int bufferSize = 1024;
        int overlap = 0;

        final AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,
                true);
        final DataLine.Info dataLineInfo = new DataLine.Info(
                TargetDataLine.class, format);
        TargetDataLine line;
        try {
            line = (TargetDataLine) mixer.getLine(dataLineInfo);
            final int numberOfSamples = bufferSize;
            line.open(format, numberOfSamples);
        } catch (LineUnavailableException e) {
            throw new IllegalStateException(e);
        }
        line.start();
        final AudioInputStream stream = new AudioInputStream(line);

        // create a new dispatcher
        try {
            audioDispatcher = new AudioDispatcher(stream, bufferSize,
                    overlap);
        } catch (UnsupportedAudioFileException e) {
            throw new IllegalStateException(e);
        }

        // add a processor
        PitchProcessor pitchProcessor = new PitchProcessor(
                PitchProcessor.PitchEstimationAlgorithm.YIN, sampleRate,
                bufferSize, this);

        audioDispatcher.addAudioProcessor(pitchProcessor);

        executorService.submit(audioDispatcher);
    }

    @Override
    public void handlePitch(PitchDetectionResult pitchDetectionResult,
                            AudioEvent audioEvent) {

        pitch = pitchDetectionResult.getPitch();

        for (PitchDetectionHandler handler : handlerList) {
            handler.handlePitch(pitchDetectionResult, audioEvent);
        }

        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : pitchModelListeners) {
            listener.stateChanged(event);
        }
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
        pitchModelListeners.add(changeListener);
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
        pitchModelListeners.remove(changeListener);
    }

    @Override
    public String getPitchName() {
        return PitchUtils.getPitchName(pitch);
    }

    @Override
    public float getDistanceToPitch() {
        return PitchUtils.getDistanceToPitch(pitch);
    }

    @Override
    public int getPitchOctave() {
        return PitchUtils.getPitchOctave(pitch);
    }
}
