package org.usfirst.frc.team2910.robot.commands.autonomous;

public class AutonomousConstants {
    public static final double WALL_TO_PLATFORM_ZONE = 20 * 12;

    public static final double SWITCH_SCORE_TO_SWITCH_WALL = 1 * 12 + 1.5;

    public static final double WALL_TO_SWITCH = 12 * 12;
    public static final double SWITCH_LENGTH = 12 * 12 + 9.5;
    public static final double SWITCH_DEPTH = 4 * 12 + 8;

    public static final double WALL_TO_SCALE = 26 * 12;
    public static final double START_POS_TO_SCALE_SCORE = 1.5 * 12;
    public static final double SCORE_SCALE = 1.5 * 12;

    public static final double CUBE_WIDTH = 13;
    public static final double GAP_BETWEEN_PLATFORM_CUBES = (SWITCH_LENGTH - 6 * CUBE_WIDTH) / 5;
}
