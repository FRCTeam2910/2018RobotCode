package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.IntakeCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreSwitchFrontFromSwitchFront extends CommandGroup {
	public ScoreSwitchFrontFromSwitchFront(Robot robot, Side switchSide) {
		addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.GROUND_POSITION));
		addParallel(new IntakeCubeCommand(robot.getGatherer(), 3));
		if (switchSide == Side.LEFT) {
			addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_1));
		} else {
			addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_1));
		}

		addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));

		if (switchSide == Side.LEFT) {
			addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_2));
		} else {
			addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_2));
		}
	}
}
