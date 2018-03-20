package org.usfirst.frc.team2910.robot.commands.autonomous;

import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.IntakeCubeCommand;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GrabCubeInFrontCommand extends CommandGroup{
	
	private SwerveDriveSubsystem drivetrain;
	private Robot robot;
	private boolean isFinished = false;
	
	public GrabCubeInFrontCommand(Robot robot) {
		this.drivetrain = robot.getDrivetrain();
		this.robot = robot;
	}
	
	protected void initialize() {
		addSequential(new DriveForDistanceCommand(drivetrain, 3));
		addParallel(new IntakeCubeCommand(robot.getGatherer(), 3));
		addSequential(new DriveForDistanceCommand(drivetrain, -3));
		isFinished = true;
		isFinished();
	}
	
	protected void end() {
	}
	
	protected boolean isFinished() {
		return isFinished;
	}
}
