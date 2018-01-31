package org.usfirst.frc.team2910.robot.subsystems;

import org.usfirst.frc.team2910.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;

public class GathererSubsystem {
	private Talon leftMotor = new Talon(RobotMap.GATHERER_LEFT_MOTOR);
	private Talon rightMotor = new Talon(RobotMap.GATHERER_RIGHT_MOTOR);
	
	private DoubleSolenoid leftSolenoid = new DoubleSolenoid(RobotMap.GATHERER_LEFT_SOLENOID_1, RobotMap.GATHERER_LEFT_SOLENOID_2);
	private DoubleSolenoid rightSolenoid = new DoubleSolenoid(RobotMap.GATHERER_RIGHT_SOLENOID_1, RobotMap.GATHERER_RIGHT_SOLENOID_2);
	
	public void readyGatherer() {
		if(leftSolenoid.get() == Value.kForward && rightSolenoid.get() == Value.kForward) {
			leftSolenoid.set(Value.kReverse);
			rightSolenoid.set(Value.kReverse);
		} else {
			leftSolenoid.set(Value.kForward);
			rightSolenoid.set(Value.kForward);
		}
	}
	
	public void activateGatherer(double gathererSpeed) {
		leftMotor.set(gathererSpeed);
		rightMotor.set(gathererSpeed);
	}
}
