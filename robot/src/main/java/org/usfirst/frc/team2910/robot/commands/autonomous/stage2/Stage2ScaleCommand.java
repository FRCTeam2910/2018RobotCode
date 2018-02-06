package org.usfirst.frc.team2910.robot.commands.autonomous.stage2;

import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.autonomous.DriveForDistanceCommand;
import org.usfirst.frc.team2910.robot.util.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.*;

public class Stage2ScaleCommand extends CommandGroup{
	private final Robot robot;
	public Stage2ScaleCommand(Robot robot, Side side) {
		this.robot = robot;
		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
				(side == Side.LEFT ? 1 : -1) * START_POS_TO_SCALE_SCORE, WALL_TO_SCALE - WALL_TO_PLATFORM_ZONE));
		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
				(side == Side.LEFT ? -1 : 1) * SCORE_SCALE, 0));

		// TODO: Score

		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
				(side == Side.LEFT ? 1 : -1) * SCORE_SCALE, 0));
		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
				(side == Side.LEFT ? -1 : 1) * START_POS_TO_SCALE_SCORE, -(WALL_TO_SCALE - WALL_TO_PLATFORM_ZONE)));
	}
}
