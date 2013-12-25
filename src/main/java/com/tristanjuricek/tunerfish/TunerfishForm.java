/*
 * Created by JFormDesigner on Sun Dec 22 18:23:14 PST 2013
 */

package com.tristanjuricek.tunerfish;

import be.hogent.tarsos.dsp.AudioEvent;
import be.hogent.tarsos.dsp.pitch.PitchDetectionHandler;
import be.hogent.tarsos.dsp.pitch.PitchDetectionResult;
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
    private JLabel audioSourceLabel;
    private JComboBox audioSourceComboBox;

    private JLabel tmpLabel;

    public TunerfishForm(MixerListAdaptor mixerListAdaptor,
                         AudioDispatcherManager audioDispatcherManager) {
        this.mixerListAdaptor = mixerListAdaptor;

        this.audioDispatcherManager = audioDispatcherManager;

        this.audioDispatcherManager.addPitchDetectionHandler(this);
        this.mixerListAdaptor.addSelectionChangedListener(this.audioDispatcherManager);

        initComponents();
    }

    private void initComponents() {
        ResourceBundle bundle = ResourceBundle.getBundle("com.tristanjuricek.tunerfish.TunerfishForm");
        audioSourceLabel = new JLabel();
        audioSourceComboBox = new JComboBox();

        tmpLabel = new JLabel();
        tmpLabel.setText("C1");

        setBackground(new Color(255, 255, 0));
        Container contentPane = getContentPane();

        audioSourceLabel.setText(bundle.getString("TunerfishForm.audioSourceLabel.text"));

        MigLayout layout = new MigLayout(new LC().insetsAll("20"), new AC().grow());
        contentPane.setLayout(layout);

        add(audioSourceLabel, new CC().wrap());
        add(audioSourceComboBox, new CC().growX(100f).wrap());
        add(tmpLabel, new CC().alignX("center"));

        contentPane.setBackground(new Color(255, 255, 0));
        pack();
        setLocationRelativeTo(getOwner());

        audioSourceComboBox.setModel(mixerListAdaptor.getMixerDescriptions());
    }

    @Override
    public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
        // TODO, we'll eventualy want to convert this
        tmpLabel.setText(Float.toString(pitchDetectionResult.getPitch()));
    }
}
