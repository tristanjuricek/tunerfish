package com.tristanjuricek.tunerfish.swing;

import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tristan on 12/26/13.
 */
public class DefaultPitchModel implements PitchModel {

    private float pitch;
    private List<ChangeListener> listeners;

    public DefaultPitchModel() {
        listeners = new ArrayList<ChangeListener>();
        pitch = 0.0f;
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
        listeners.add(changeListener);
    }

    @Override
    public float getPitch() {
        return 0;
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
        listeners.remove(changeListener);
    }

    @Override
    public String getPitchName() {
        return PitchUtils.getPitchName(pitch);
    }

    @Override
    public int getCents() {
        return 0;
    }

    @Override
    public int getPitchOctave() {
        return PitchUtils.getPitchOctave(pitch);
    }
}
