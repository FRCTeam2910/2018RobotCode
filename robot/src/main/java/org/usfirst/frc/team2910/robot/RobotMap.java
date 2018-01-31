package org.usfirst.frc.team2910.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int[] DRIVETRAIN_LEFT_ENCODER = {0, 1};
	public static final int[] DRIVETRAIN_LEFT_MOTORS = {3, 4, 5};
	public static final int[] DRIVETRAIN_LEFT_SHIFTER = {0, 1};
	public static final int[] DRIVETRAIN_RIGHT_ENCODER = {2, 3};
	public static final int[] DRIVETRAIN_RIGHT_MOTORS = {0, 1, 2};
	public static final int[] DRIVETRAIN_RIGHT_SHIFTER = {6, 7};
	
	//TODO: Get Motor & solenoid values
	public static final int GATHERER_LEFT_MOTOR = 0;
	public static final int GATHERER_RIGHT_MOTOR = 1;
	
	public static final int GATHERER_LEFT_SOLENOID_1 = 0;
	public static final int GATHERER_LEFT_SOLENOID_2 = 1;
	
	public static final int GATHERER_RIGHT_SOLENOID_1 = 3;
	public static final int GATHERER_RIGHT_SOLENOID_2 = 4;
}
