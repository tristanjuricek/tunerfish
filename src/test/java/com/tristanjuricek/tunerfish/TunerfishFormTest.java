package com.tristanjuricek.tunerfish;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testng.testcase.FestSwingTestngTestCase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 *
 */
public class TunerfishFormTest extends FestSwingTestngTestCase {

    final AudioDispatcherManager audioDispatcherManager = new AudioDispatcherManager();
    final MixerListAdaptor mixerListAdaptor = new MixerListAdaptor();
    TunerfishForm tunerfishForm;
    private FrameFixture window;

    protected void onSetUp() {
        tunerfishForm = GuiActionRunner.execute(new GuiQuery<TunerfishForm>() {
            protected TunerfishForm executeInEDT() {
                return new TunerfishForm(mixerListAdaptor, audioDispatcherManager);
            }
        });
        // IMPORTANT: note the call to 'robot()'
        // we must use the Robot from FestSwingTestngTestCase
        window = new FrameFixture(robot(), tunerfishForm);
        window.show(); // shows the frame to test
    }

    @Test
    public void changeAudioSelection() {
        String mixerName = "Built-in Input";
        window.comboBox("audioSourceComboBox").selectItem(mixerName);

        String currentName = GuiActionRunner.execute(new GuiQuery<String>() {
            @Override
            protected String executeInEDT() throws Throwable {
                return tunerfishForm.getSelectedMixerName();
            }
        });
        assertEquals(currentName, mixerName);
    }
}