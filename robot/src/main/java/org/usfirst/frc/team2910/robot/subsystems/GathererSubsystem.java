package org.usfirst.frc.team2910.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.RobotMap;
import org.usfirst.frc.team2910.robot.commands.GathererCommand;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import static org.usfirst.frc.team2910.robot.RobotMap.*;

public class GathererSubsystem  extends Subsystem {
	private static final double CARRIAGE_POSITION_P = 0;
	private static final double CARRIAGE_POSITION_I = 0;
	private static final double CARRIAGE_POSITION_D = 0;
	private static final double CARRIAGE_POSITION_F = 0;

	private static final double CARRIAGE_ENCODER_TICKS_PER_INCH = 1; // TODO: Find value

	public static enum Position {
		IN,
		OUT
	};
	
	private TalonSRX leftIntake = new TalonSRX(GATHERER_LEFT_MOTOR);
	private TalonSRX rightIntake = new TalonSRX(GATHERER_RIGHT_MOTOR);
	
	private WPI_TalonSRX leftCarriage = new WPI_TalonSRX(CARRIAGE_LEFT_MOTOR);
	private WPI_TalonSRX rightCarriage = new WPI_TalonSRX(CARRIAGE_RIGHT_MOTOR);
	
	private Solenoid leftSolenoid = new Solenoid(RobotMap.GATHERER_LEFT_SOLENOID);
	private Solenoid rightSolenoid = new Solenoid(RobotMap.GATHERER_RIGHT_SOLENOID);
	
	private final DifferentialDrive intakeDriver = new DifferentialDrive(leftCarriage, rightCarriage);
	
	public GathererSubsystem() {
		setLeftArm(Position.OUT);
		setRightArm(Position.IN);

		leftCarriage.setInverted(true);
		rightCarriage.setInverted(true);
		leftIntake.setInverted(true);
		rightIntake.setInverted(true);

		// Tell the carriage talons that they have encoders
		leftCarriage.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		rightCarriage.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

		// Configure PID constants for left carriage
		leftCarriage.config_kP(0, CARRIAGE_POSITION_P, 0);
		leftCarriage.config_kI(0, CARRIAGE_POSITION_I, 0);
		leftCarriage.config_kD(0, CARRIAGE_POSITION_D, 0);
		leftCarriage.config_kF(0, CARRIAGE_POSITION_F, 0);

		// Configure PID constants for right carriage
		rightCarriage.config_kP(0, CARRIAGE_POSITION_P, 0);
		rightCarriage.config_kI(0, CARRIAGE_POSITION_I, 0);
		rightCarriage.config_kD(0, CARRIAGE_POSITION_D, 0);
		rightCarriage.config_kF(0, CARRIAGE_POSITION_F, 0);

		leftIntake.follow(leftCarriage);
		rightIntake.follow(rightCarriage);

		intakeDriver.setSafetyEnabled(false);
	}
	
	public void setLeftArm(Position position) {
		leftSolenoid.set(position == Position.OUT);
	}
	
	public void setRightArm(Position position) {
		rightSolenoid.set(position == Position.IN);
	}
	
	public void activateGatherer(double out, double rot) {
		intakeDriver.arcadeDrive(out, rot);
	}

	public void outtakeForDistance(double distance, double maxVelocity, double maxAcceleration) {
		// Zero the carriage encoders
		leftCarriage.setSelectedSensorPosition(0, 0, 0);
		rightCarriage.setSelectedSensorPosition(0, 0, 0);

		// Set the motion constraints on the carriage
		int nativeMaxVelocity = (int) (maxVelocity * CARRIAGE_ENCODER_TICKS_PER_INCH / 10.0);
		int nativeMaxAcceleration = (int) (maxAcceleration * CARRIAGE_ENCODER_TICKS_PER_INCH / 10.0);

		leftCarriage.configMotionCruiseVelocity(nativeMaxVelocity, 0);
		rightCarriage.configMotionCruiseVelocity(nativeMaxVelocity, 0);

		leftCarriage.configMotionAcceleration(nativeMaxAcceleration, 0);
		rightCarriage.configMotionAcceleration(nativeMaxAcceleration, 0);

		// Generate and follow a motion profile
		double nativeDistance = distance * CARRIAGE_ENCODER_TICKS_PER_INCH;

		leftCarriage.set(ControlMode.MotionMagic, nativeDistance);
		rightCarriage.set(ControlMode.MotionMagic, nativeDistance);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new GathererCommand(this, Robot.getOI().getSecondaryController()));	
	}
}
