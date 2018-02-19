package org.usfirst.frc.team2910.robot.commands.autonomous.stage2;


import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants;
import org.usfirst.frc.team2910.robot.commands.autonomous.DriveForDistanceCommand;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.SWITCH_DEPTH;
import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.WALL_TO_PLATFORM_ZONE;
import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.WALL_TO_SWITCH;

public class Stage2SwitchCommand extends CommandGroup{
	private final Robot robot;
	
	public Stage2SwitchCommand(Robot robot, Side side) {
		this.robot = robot;
		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0,
				(WALL_TO_SWITCH + SWITCH_DEPTH / 2) - WALL_TO_PLATFORM_ZONE));


		if(side == Side.LEFT) {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), -AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL, 0));
		} else {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL, 0));
		}

		addSequential(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
		addSequential(new LaunchCubeCommand(robot.getGatherer(), 1));
		addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.GROUND_POSITION));
		
		if(side == Side.LEFT) {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL, 0));
		} else {
			addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), -AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL, 0));
		}
		addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, WALL_TO_PLATFORM_ZONE - (WALL_TO_SWITCH + SWITCH_DEPTH / 2)));
	}

}
