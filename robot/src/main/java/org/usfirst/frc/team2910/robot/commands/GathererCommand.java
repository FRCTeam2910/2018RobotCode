package org.usfirst.frc.team2910.robot.commands;

import org.usfirst.frc.team2910.robot.Utilities;
import org.usfirst.frc.team2910.robot.input.IGamepad;
import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class GathererCommand extends Command{
	private GathererSubsystem gatherer;
	private IGamepad controller;
	
	public GathererCommand(GathererSubsystem gatherer, IGamepad controller) {
		this.gatherer = gatherer;
		this.controller = controller;
		requires(gatherer);
	}
	
	protected void execute() {
		gatherer.activateGatherer(
				Utilities.deadband(controller.getLeftYValue()),
				Utilities.deadband(controller.getLeftXValue()));
	}
	
	protected void interrupted() {
		end();
	}
	
	protected void end() {
		gatherer.activateGatherer(0, 0);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
