package org.usfirst.frc.team2910.robot.commands.autonomous.stage2;


import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants;
import org.usfirst.frc.team2910.robot.commands.autonomous.DriveForDistanceCommand;
import org.usfirst.frc.team2910.robot.util.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Stage2SwitchCommand extends CommandGroup{
	private final Robot robot;
	
	public Stage2SwitchCommand(Robot robot, Side side) {
		this.robot = robot;
		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, -(AutonomousConstants.WALL_TO_PLATFORM_ZONE - AutonomousConstants.WALL_TO_SWITCH)));
		if(side == Side.LEFT) {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), -AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL, 0));
		} else {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL, 0));
		}
		
		//TODO: Place cube
		
		if(side == Side.LEFT) {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL, 0));
		} else {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), -AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL, 0));
		}
		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, AutonomousConstants.WALL_TO_PLATFORM_ZONE - AutonomousConstants.WALL_TO_SWITCH));
	}

}
