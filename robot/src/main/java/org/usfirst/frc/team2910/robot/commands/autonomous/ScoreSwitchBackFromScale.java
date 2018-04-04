package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.CalibrateElevatorEncoderCommand;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreSwitchBackFromScale extends CommandGroup {
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

		if (scaleSide == Side.LEFT) {
			if (switchSide == Side.LEFT) {
				// Same side
				addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_SCALE_TO_LEFT_SWITCH_BACK));
			} else {
				// Opposite side
				addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_SCALE_TO_RIGHT_SWITCH_BACK));
			}
		} else {
			if (switchSide == Side.LEFT) {
				// Opposite side
				addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_SCALE_TO_LEFT_SWITCH_BACK));
			} else {
				// Same side
				addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_SCALE_TO_RIGHT_SWITCH_BACK));
			}
		}

		addSequential(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
		addSequential(new LaunchCubeCommand(robot.getGatherer(), 0.5));
	}
}
