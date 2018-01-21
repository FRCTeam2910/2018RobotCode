package org.usfirst.frc.team2910.robot.commands;

import org.usfirst.frc.team2910.robot.subsystems.HolonomicDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class SetFieldOrientedAngleCommand extends Command {

	private final HolonomicDrivetrain drivetrain;
	private final double angle;
	
	public SetFieldOrientedAngleCommand(HolonomicDrivetrain drivetrain, double angle) {
		this.drivetrain = drivetrain;
		this.angle = angle;
		
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		drivetrain.setAdjustmentAngle(angle);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
