package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.motion.Trajectory;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreScaleFrontFromStartForward extends CommandGroup {
	/**
	 * Time from the end of the path that the elevator's position is set
	 */
	private static final double ELEVATOR_TIME = 4;

	/**
	 * Time from the end of the path that the cube is launched
	 */
	private static final double LAUNCH_TIME = 0.5;

	public ScoreScaleFrontFromStartForward(Robot robot, Side startSide, Side scaleSide) {
		Path pathToScale;
		if (startSide == Side.LEFT) {
			if (scaleSide == Side.LEFT) {
				pathToScale = AutonomousPaths.LEFT_START_FORWARD_TO_LEFT_SCALE_FRONT;
			} else {
				pathToScale = AutonomousPaths.LEFT_START_FORWARD_TO_RIGHT_SCALE_FRONT;
			}
		} else {
			if (scaleSide == Side.LEFT) {
				pathToScale = AutonomousPaths.RIGHT_START_FORWARD_TO_LEFT_SCALE_FRONT;
			} else {
				pathToScale = AutonomousPaths.RIGHT_START_FORWARD_TO_RIGHT_SCALE_FRONT;
			}
		}

		Trajectory trajectoryToScale = new Trajectory(pathToScale, robot.getDrivetrain().getMaxAcceleration(), robot.getDrivetrain().getMaxVelocity());

		// Wait until x seconds are left in the path to set the elevator position
		CommandGroup elevatorPositionGroup = new CommandGroup();
		elevatorPositionGroup.addSequential(new WaitCommand(Math.max(0, trajectoryToScale.getDuration() - ELEVATOR_TIME)));
		elevatorPositionGroup.addSequential(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION));

		// Wait until x seconds are left in the path to launch the cube.
		CommandGroup launchCubeGroup = new CommandGroup();
		elevatorPositionGroup.addSequential(new WaitCommand(Math.max(0, trajectoryToScale.getDuration() - LAUNCH_TIME)));
		elevatorPositionGroup.addSequential(new LaunchCubeCommand(robot.getGatherer(), 0.5));

		addParallel(elevatorPositionGroup);
		addParallel(launchCubeGroup);
		addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToScale));
	}
}
