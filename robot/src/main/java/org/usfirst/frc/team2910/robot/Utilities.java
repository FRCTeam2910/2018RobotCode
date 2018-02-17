package org.usfirst.frc.team2910.robot;

public final class Utilities {

    public static double deadband(double in) {
        if (Math.abs(in) < 0.02) return 0;

        return in;
    }
}
