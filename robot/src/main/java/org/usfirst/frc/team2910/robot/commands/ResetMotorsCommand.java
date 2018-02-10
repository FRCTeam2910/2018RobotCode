package org.usfirst.frc.team2910.robot.commands;

import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ResetMotorsCommand extends Command{
	private SwerveDriveSubsystem drivetrain;
	
	public ResetMotorsCommand(SwerveDriveSubsystem drivetrain) {
		this.drivetrain = drivetrain;
		
		requires(drivetrain);
	}
	
	protected void initialize() {
		drivetrain.resetMotors();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
}
