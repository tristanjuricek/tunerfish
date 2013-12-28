package com.tristanjuricek.tunerfish.swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Swing component that represents the current pitch, that can slide based on
 * changes.
 */
public class JPitchSlider extends JComponent implements ChangeListener {

    private static final String uiClassID = "JPitchSliderUI";

    private PitchModel pitchModel;

    public JPitchSlider() {
        this.pitchModel = new DefaultPitchModel();
        init();
    }

    private void init() {
        setOpaque(true);
        updateUI();
    }

    public void setUI(JPitchSliderUI ui) {
        super.setUI(ui);
    }

    public void updateUI() {
        if (UIManager.get(getUIClassID()) != null) {
            setUI((JPitchSliderUI) UIManager.getUI(this));
        } else {
            setUI(new JPitchSliderUI());
        }
    }

    public JPitchSliderUI getUI() {
        return (JPitchSliderUI) ui;
    }

    public String getUIClassID() {
        return uiClassID;
    }

    public PitchModel getPitchModel() {
        return pitchModel;
    }

    public void setPitchModel(PitchModel pitchModel) {
        this.pitchModel = pitchModel;
        this.pitchModel.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.updateUI();
    }
}
