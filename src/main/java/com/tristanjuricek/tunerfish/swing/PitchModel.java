package com.tristanjuricek.tunerfish.swing;

import javax.swing.event.ChangeListener;

/**
 * Provides a simple interface to the current pitch for use.
 */
public interface PitchModel {

    void addChangeListener(ChangeListener changeListener);

    float getPitch();

    String getPitchName();

    int getPitchOctave();

    int getCents();

    void removeChangeListener(ChangeListener changeListener);
}
