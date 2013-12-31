package com.tristanjuricek.tunerfish.swing;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by tristan on 12/30/13.
 */
public class PitchUtilsTest {

    @Test
    public void pitchNumberOfA440Is69() {
        int n = PitchUtils.getPitchNumber(440f);
        assertEquals(n, 69);
    }

    @Test
    public void pitchNumberOfA439IsAlso69() {
        int n = PitchUtils.getPitchNumber(439f);
        assertEquals(n, 69);
    }

    @Test
    public void distanceTo443is11() {
        float dist = PitchUtils.getDistanceToPitch(443f);
        assertEquals((int)dist, 11);
    }
}
