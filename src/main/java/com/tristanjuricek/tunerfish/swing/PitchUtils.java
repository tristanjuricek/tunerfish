package com.tristanjuricek.tunerfish.swing;

/**
 */
public class PitchUtils {

    public static final float A4 = 440f;

    /**
     * A4 = 69
     * A3 = 57
     * A2 = 45
     * A1 = 33, C2 = 36
     *
     * @param pitch
     * @return
     */
    public static int getPitchNumber(float pitch) {
        if (pitch == Float.MIN_VALUE) {
            return -100;
        }
        double x_1 = pitch / A4;
        double x_2 = Math.log(x_1) / Math.log(2d);
	double x_3 = 12d * x_2;
        return (int)Math.round( 49d + x_3 );
    }

    /**
     * Returns approximate cents to the nearest correct pitch frequency. When 0,
     * this is a "correct" approximate pitch.
     */
    public static float getDistanceToPitch(float pitch) {
        int num = getPitchNumber(pitch);
        float localF = getFrequency(num);
        int cents = (int)(3986d * Math.log10(pitch/localF));
        return cents;
    }

    /**
     * Return the frequency of the absolute pitch number.
     *
     * @param pitchNum The pitch number, where 69 = A4
     * @return Approximate frequency, 440 for A4.
     */
    public static float getFrequency(int pitchNum) {
        int x_1 = pitchNum - 49; // A4 = 49
        double x_2 = x_1 / 12d;
        double x_3 = Math.pow(2.0d, x_2);
        return (float)(x_3 * A4);
    }

    public static int getPitchOctave(float pitch) {
        return (getPitchNumber(pitch) - 12) / 12;
    }

    /**
     * Return a simple string representation of the pitch.
     *
     * TODO this should probably be mapped in a different way.
     *
     * @param pitch
     * @return
     */
    public static String getPitchName(float pitch) {
        int num = getPitchNumber(pitch);
        int rem = num % 12;
        switch (rem) {
            case 0:
                return "G#";

            case 1:
                return "A";

            case 2:
                return "A#";

            case 3:
                return "B";

            case 4:
                return "C";

            case 5:
                return "C#";

            case 6:
                return "D";

            case 7:
                return "D#";

            case 8:
                return "E";

            case 9:
                return "F";

            case 10:
                return "F#";

            case 11:
                return "G";
        }
        return "";
    }
}
