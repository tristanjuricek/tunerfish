/*
 * Created by JFormDesigner on Sun Dec 22 18:23:14 PST 2013
 */

package com.tristanjuricek.tunerfish;

import be.hogent.tarsos.dsp.AudioEvent;
import be.hogent.tarsos.dsp.pitch.PitchDetectionHandler;
import be.hogent.tarsos.dsp.pitch.PitchDetectionResult;
import com.tristanjuricek.tunerfish.swing.JPitchSlider;
import com.tristanjuricek.tunerfish.swing.MixerComboBoxUI;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class TunerfishForm extends JFrame implements PitchDetectionHandler {

    private AudioDispatcherManager audioDispatcherManager;
    private MixerListAdaptor mixerListAdaptor;
    private JPitchSlider pitchSlider;
    private JComboBox audioSourceComboBox;

    private Color bColor;

    public TunerfishForm(MixerListAdaptor mixerListAdaptor,
                         AudioDispatcherManager audioDispatcherManager) {
        this.mixerListAdaptor = mixerListAdaptor;

        this.audioDispatcherManager = audioDispatcherManager;

        this.audioDispatcherManager.addPitchDetectionHandler(this);
        this.mixerListAdaptor.addSelectionChangedListener(this.audioDispatcherManager);

        this.bColor = new Color(30, 30, 30);
        this.setBackground(this.bColor);

        initComponents();

        setResizable(false);
    }

    private void initComponents() {
        ResourceBundle bundle = ResourceBundle.getBundle("com.tristanjuricek.tunerfish.TunerfishForm");
        pitchSlider = new JPitchSlider();
        audioSourceComboBox = new JComboBox();

        // We use a completely custom UI
        audioSourceComboBox.setUI(new MixerComboBoxUI());

        pitchSlider.setPitchModel(this.audioDispatcherManager);

        setBackground(this.bColor);
        Container contentPane = getContentPane();

        MigLayout layout = new MigLayout(new LC().insetsAll("0"), new AC().grow());

        contentPane.setLayout(layout);

        add(pitchSlider, new CC().wrap());
        add(audioSourceComboBox, new CC().growX(100f).wrap());

        contentPane.setBackground(this.bColor);
        pack();
        setLocationRelativeTo(getOwner());

        audioSourceComboBox.setModel(mixerListAdaptor.getMixerDescriptions());
    }

    @Override
    public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
    }
}
