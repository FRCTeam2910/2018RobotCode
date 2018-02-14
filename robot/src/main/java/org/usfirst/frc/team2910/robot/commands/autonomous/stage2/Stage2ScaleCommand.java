package org.usfirst.frc.team2910.robot.commands.autonomous.stage2;

import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants;
import org.usfirst.frc.team2910.robot.commands.autonomous.DriveForDistanceCommand;
import org.usfirst.frc.team2910.robot.util.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Stage2ScaleCommand extends CommandGroup{
	private final Robot robot;
	public Stage2ScaleCommand(Robot robot, Side side) {
		this.robot = robot;
		if(side == Side.LEFT){
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), AutonomousConstants.START_POS_TO_SCALE_SCORE, 0));
		} else {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), -AutonomousConstants.START_POS_TO_SCALE_SCORE, 0));
		}
		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, AutonomousConstants.WALL_TO_SCALE - AutonomousConstants.WALL_TO_PLATFORM_ZONE));
		
		if(side == Side.LEFT) {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, AutonomousConstants.SCORE_SCALE));
		} else {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, -AutonomousConstants.SCORE_SCALE));
		}
		//		Score

		if(side == Side.LEFT) {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, -AutonomousConstants.SCORE_SCALE));
		} else {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, AutonomousConstants.SCORE_SCALE));
		}
		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, -(AutonomousConstants.WALL_TO_SCALE - AutonomousConstants.WALL_TO_PLATFORM_ZONE)));
		
		if(side == Side.LEFT){
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), -AutonomousConstants.START_POS_TO_SCALE_SCORE, 0));
		} else {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), AutonomousConstants.START_POS_TO_SCALE_SCORE, 0));
		}
	}
}
