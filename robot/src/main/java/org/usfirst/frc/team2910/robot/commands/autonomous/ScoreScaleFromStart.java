package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreScaleFromStart extends CommandGroup {
	private static final double SAME_SIDE_ELEVATOR_WAIT = 1.0;
	private static final double OPPOSITE_SIDE_ELEVATOR_WAIT = 6.0;

	public ScoreScaleFromStart(Robot robot, Side startSide, Side scaleSide, StartingOrientation orientation) {
		if (startSide == Side.LEFT) {
			if (scaleSide == Side.LEFT) {
				// Same side
				addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION, SAME_SIDE_ELEVATOR_WAIT));
				if (orientation == StartingOrientation.FORWARDS) {
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_START_FORWARD_TO_LEFT_SCALE_FRONT));
				} else {
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_START_TO_LEFT_SCALE_SIDE_STEP_1));
				}
			} else {
				// Opposite side
				if (orientation == StartingOrientation.FORWARDS) {
					addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION, OPPOSITE_SIDE_ELEVATOR_WAIT));
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_START_FORWARD_TO_RIGHT_SCALE_FRONT));
				} else {
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_1));
					addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION));
					addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(), 270));
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_2));
				}
			}
		} else {
			if (scaleSide == Side.LEFT) {
				// Opposite side
				if (orientation == StartingOrientation.FORWARDS) {
					addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION, OPPOSITE_SIDE_ELEVATOR_WAIT));
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_START_FORWARD_TO_LEFT_SCALE_FRONT));
				} else {
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_1));
					addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION));
					addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(), 270));
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_2));
				}
			} else {
				// Same side
				addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION, SAME_SIDE_ELEVATOR_WAIT));
				if (orientation == StartingOrientation.FORWARDS) {
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_START_FORWARD_TO_RIGHT_SCALE_FRONT));
				} else {
					addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_1));
				}
			}
		}

		addSequential(new LaunchCubeCommand(robot.getGatherer(), 1));
	}
}