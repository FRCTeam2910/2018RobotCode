package org.usfirst.frc.team2910.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int[] ELEVATOR_MOTORS = {28, 27};
    public static final int ELEVATOR_SHIFTER = 3;
    public static final int ELEVATOR_LOCKER = 2;

    public static final int ELEVATOR_SHIFTING_SWITCH = 0;

	public static final int GATHERER_LEFT_MOTOR = 31;
	public static final int GATHERER_RIGHT_MOTOR = 29;
	
	public static final int GATHERER_LEFT_SOLENOID = 1;
	public static final int GATHERER_RIGHT_SOLENOID = 0;
}
