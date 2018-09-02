package org.usfirst.frc.team2910.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int ELEVATOR_SHIFTER = 3;
    public static final int ELEVATOR_LOCKER = 2;

	public static final int GATHERER_LEFT_SOLENOID = 1;
	public static final int GATHERER_RIGHT_SOLENOID = 0;

	public static final int GATHERER_LEFT_MOTOR = 30;
    public static final int GATHERER_RIGHT_MOTOR = 29;

	public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 25;
	public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 23;
	public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 34;
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 32;
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 26;
    public static final int DRIVETRAIn_BACK_LEFT_ANGLE_MOTOR = 24;
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 33;
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 31;


	public static final int ELEVATOR_MASTER_MOTOR = 28;
	public static final int ELEVATOR_SLAVE_MOTOR = 27;

	public static final int CARRIAGE_LEFT_MOTOR = 21;
	public static final int CARRIAGE_RIGHT_MOTOR = 22;
}
