package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.IntakeCubeCommand;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.motion.Trajectory;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreSwitchFrontFromSwitchFront extends CommandGroup {
	private static final double INTAKE_TIME = 1;
	public ScoreSwitchFrontFromSwitchFront(Robot robot, Side switchSide) {
		addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.GROUND_POSITION));
		{
			Path pathToCube;
			if (switchSide == Side.LEFT)
				pathToCube = AutonomousPaths.LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_1;
			else
				pathToCube = AutonomousPaths.RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_1;
			Trajectory trajectoryToCube = new Trajectory(pathToCube, robot.getDrivetrain().getMaxAcceleration(), robot.getDrivetrain().getMaxVelocity());

			CommandGroup intakeGroup = new CommandGroup();
			intakeGroup.addSequential(new WaitCommand(Math.max(0, trajectoryToCube.getDuration() - INTAKE_TIME)));
			intakeGroup.addSequential(new IntakeCubeCommand(robot.getGatherer(), INTAKE_TIME));

			addParallel(intakeGroup);
			addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToCube));
		}

		addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));

		if (switchSide == Side.LEFT) {
			addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_2));
		} else {
			addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_2));
		}

		addSequential(new LaunchCubeCommand(robot.getGatherer(), 0.5));
	}
}