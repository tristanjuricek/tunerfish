package com.tristanjuricek.tunerfish.swing;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Pitch utils
 */
public class PitchUtilsTest {

    @Test
    public void pitchNumberOfA440Is49() {
        int n = PitchUtils.getPitchNumber(440f);
        assertEquals(n, 49);
    }

    @Test
    public void pitchNumberOfA439IsAlso49() {
        int n = PitchUtils.getPitchNumber(439f);
        assertEquals(n, 49);
    }

    @Test
    public void pitchNumberOfEb79_5Is19() {
	int n = PitchUtils.getPitchNumber(79.5f);
	assertEquals(n, 19);
    }

    @Test
    public void distanceToEb79_5isPlus37() {
	float dist = PitchUtils.getDistanceToPitch(79.5f);
	assertEquals((int)dist,37);
    }

    @Test
    public void distanceTo443is11() {
        float dist = PitchUtils.getDistanceToPitch(443f);
        assertEquals((int)dist, 11);
    }

}
