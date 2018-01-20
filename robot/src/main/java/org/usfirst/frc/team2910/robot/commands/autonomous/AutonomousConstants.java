package org.usfirst.frc.team2910.robot.commands.autonomous;

public class AutonomousConstants {
    public static final double WALL_TO_SWITCH_SIDE_DISTANCE = 14 * 12; // Switch is centered 14 feet away from wall
    public static final double SWITCH_SIDE_TO_PLATFORM_ZONE = 87.25;
    public static final double WALL_TO_PLATFORM_ZONE = 231.25;
    public static final double SWITCH_LENGTH = 144;
    public static final double SWITCH_SCORE_TO_SWITCH_WALL = 4;
    public static final double WALL_TO_SCALE = 250;
    public static final double START_POS_TO_SCALE_SCORE = 8;

    public static final double CUBE_WIDTH = 13;
    public static final double GAP_BETWEEN_PLATFORM_CUBES = (SWITCH_LENGTH - 6 * CUBE_WIDTH) / 5;
}
