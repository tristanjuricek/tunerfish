package com.tristanjuricek.tunerfish.swing;

/**
 * Created by tristan on 12/27/13.
 */
public class PitchUtils {

    public static final float A = 440f;

    public static int getPitchNumber(float pitch) {
        if (pitch == Float.MIN_VALUE) {
            return -10;
        }
        double x_1 = pitch / A;
        double x_2 = Math.log(x_1) / Math.log(2);
        return (int)( 69 + 12 * x_2 );
    }

    public static int getPitchOctave(float pitch) {
        return (getPitchNumber(pitch) - 12) / 12;
    }

    public static String getPitchName(float pitch) {
        int num = getPitchNumber(pitch);
        int rem = num % 12;
        switch (rem) {
            case 0:
                return "C";

            case 1:
                return "C#";

            case 2:
                return "D";

            case 3:
                return "D#";

            case 4:
                return "E";

            case 5:
                return "F";

            case 6:
                return "F#";

            case 7:
                return "G";

            case 8:
                return "G#";

            case 9:
                return "A";

            case 10:
                return "A#";

            case 11:
                return "B";
        }
        return "";
    }
}