package org.usfirst.frc.team2910.robot.subsystems;

import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.RobotMap;
import org.usfirst.frc.team2910.robot.commands.GathererCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class GathererSubsystem  extends Subsystem {
	public static enum Position {
		IN,
		OUT
	};
	
	private WPI_TalonSRX leftMotor = new WPI_TalonSRX(RobotMap.GATHERER_LEFT_MOTOR);
	private WPI_TalonSRX rightMotor = new WPI_TalonSRX(RobotMap.GATHERER_RIGHT_MOTOR);
	
	private TalonSRX leftCarriage = new TalonSRX(22);
	private TalonSRX rightCarriage = new TalonSRX(21);
	
	private Solenoid leftSolenoid = new Solenoid(RobotMap.GATHERER_LEFT_SOLENOID);
	private Solenoid rightSolenoid = new Solenoid(RobotMap.GATHERER_RIGHT_SOLENOID);
	
	private final DifferentialDrive intakeDriver = new DifferentialDrive(leftMotor, rightMotor);
	
	public GathererSubsystem() {
		setLeftArm(Position.OUT);
		setRightArm(Position.IN);
		
		leftCarriage.setInverted(true);
		leftCarriage.follow(leftMotor);
		rightCarriage.setInverted(true);
		rightCarriage.follow(rightMotor);

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

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new GathererCommand(this, Robot.getOI().getSecondaryController()));	
	}
}
