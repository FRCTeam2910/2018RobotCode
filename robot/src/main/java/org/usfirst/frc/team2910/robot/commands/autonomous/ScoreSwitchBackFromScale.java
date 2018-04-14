package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.*;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.motion.Trajectory;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreSwitchBackFromScale extends CommandGroup {
	private static final double INTAKE_TIME = 2.5;

	public ScoreSwitchBackFromScale(Robot robot, Side switchSide, Side scaleSide) {
		{
			CommandGroup zeroGroup = new CommandGroup();
			zeroGroup.addSequential(new SetElevatorPositionCommand(robot.getElevator(),
					ElevatorSubsystem.GROUND_POSITION + 5));
			zeroGroup.addSequential(new CalibrateElevatorEncoderCommand(robot.getElevator()));
			addParallel(zeroGroup);
		}
		// Face towards driverstation
		addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(), 180));

		Path pathToCube;
		if (scaleSide == Side.LEFT) {
			if (switchSide == Side.LEFT) {
				pathToCube = AutonomousPaths.LEFT_SCALE_TO_LEFT_SWITCH_BACK;
			} else {
				pathToCube = AutonomousPaths.LEFT_SCALE_TO_RIGHT_SWITCH_BACK;
			}
		} else {
			if (switchSide == Side.LEFT) {
				pathToCube = AutonomousPaths.RIGHT_SCALE_TO_LEFT_SWITCH_BACK;
			} else {
				pathToCube = AutonomousPaths.RIGHT_SCALE_TO_RIGHT_SWITCH_BACK;
			}
		}
		Trajectory trajectoryToCube = new Trajectory(pathToCube, robot.getDrivetrain().getMaxAcceleration(), robot.getDrivetrain().getMaxVelocity());

		CommandGroup intakeGroup = new CommandGroup();
		intakeGroup.addSequential(new WaitCommand(Math.max(0, trajectoryToCube.getDuration() - INTAKE_TIME)));
		intakeGroup.addSequential(new IntakeCubeCommand(robot.getGatherer(), INTAKE_TIME));

		CommandGroup launchGroup = new CommandGroup();
		launchGroup.addSequential(new WaitForElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
		launchGroup.addSequential(new LaunchCubeCommand(robot.getGatherer(), 0.75));

		addParallel(intakeGroup);
		addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToCube));
		addSequential(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
		addSequential(launchGroup);
	}
}
