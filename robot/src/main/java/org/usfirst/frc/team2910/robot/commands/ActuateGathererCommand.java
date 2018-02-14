package org.usfirst.frc.team2910.robot.commands;

import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ActuateGathererCommand extends Command {

	private final GathererSubsystem gatherer;
	private final boolean isLeft;
	
	public ActuateGathererCommand(GathererSubsystem gatherer, boolean isLeft) {
		this.gatherer = gatherer;
		this.isLeft = isLeft;
	}
	
	@Override
	protected void initialize() {
		if (isLeft)
			gatherer.setLeftArm(GathererSubsystem.Position.IN);
		else
			gatherer.setRightArm(GathererSubsystem.Position.OUT);		
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		if (isLeft)
			gatherer.setLeftArm(GathererSubsystem.Position.OUT);
		else
			gatherer.setRightArm(GathererSubsystem.Position.IN);
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
