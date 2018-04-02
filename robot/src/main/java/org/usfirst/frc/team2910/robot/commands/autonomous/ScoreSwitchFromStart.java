package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.stage1.StartingPosition;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreSwitchFromStart extends CommandGroup {
	public ScoreSwitchFromStart(Robot robot, StartingPosition startingPosition, Side switchSide, StartingOrientation orientation) {
		addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
		switch (startingPosition) {
			case LEFT:
				// TODO: Switch from side
				break;
			case CENTER:
				if (switchSide == Side.LEFT) {
					if (orientation == StartingOrientation.FORWARDS) {
						addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.CENTER_START_TO_LEFT_SWITCH));
					} else {
						throw new IllegalArgumentException("Cannot start in the center while oriented sideways");
					}
				} else {
					if (orientation == StartingOrientation.FORWARDS) {
						addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.CENTER_START_TO_RIGHT_SWITCH));
					} else {
						throw new IllegalArgumentException("Cannot start in the center while oriented sideways");
					}
				}
				break;
			case RIGHT:
				// TODO: Switch from side
				break;
		}
		addSequential(new LaunchCubeCommand(robot.getGatherer(), 1));
	}
}