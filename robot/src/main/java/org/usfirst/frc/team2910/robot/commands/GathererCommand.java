package org.usfirst.frc.team2910.robot.commands;

import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.input.IGamepad;
import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class GathererCommand extends Command{
	
	private GathererSubsystem gatherer;
	private IGamepad controller;
	
	public GathererCommand(GathererSubsystem gatherer, IGamepad controller) {
		this.gatherer = gatherer;
		this.controller = controller;
		requires(gatherer);
	}

	protected void initialize() {
		gatherer.readyGatherer();
	}
	
	protected void execute() {
		gatherer.activateGatherer(controller.getLeftYValue(), controller.getRightYValue());
	}
	
	protected void interrupted() {
		end();
	}
	
	protected void end() {
		gatherer.activateGatherer(0, 0);
		gatherer.readyGatherer();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
