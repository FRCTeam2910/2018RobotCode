package org.usfirst.frc.team2910.robot.commands;

import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class GathererCommand extends Command{
	
	private GathererSubsystem gatherer;
	public static final double GATHERER_SPEED = 0.7;
	
	public GathererCommand(GathererSubsystem gatherer) {
		this.gatherer = gatherer;
	}

	protected void initialize() {
		gatherer.readyGatherer();
	}
	
	protected void execute() {
		gatherer.activateGatherer(GATHERER_SPEED);
	}
	
	protected void interrupted() {
		end();
	}
	
	protected void end() {
		gatherer.activateGatherer(GATHERER_SPEED);
		gatherer.readyGatherer();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
