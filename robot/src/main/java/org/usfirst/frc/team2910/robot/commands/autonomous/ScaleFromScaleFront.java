package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.CalibrateElevatorEncoderCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScaleFromScaleFront extends CommandGroup {
	public static final double ELEVATOR_WAIT = 1;

	public ScaleFromScaleFront(Robot robot, Side scaleSide) {
		Path pathToScale = null;
		if (scaleSide == Side.LEFT)
			pathToScale = AutonomousPaths.LEFT_SCALE_FRONT_TO_LEFT_SCALE;
		else
			pathToScale = AutonomousPaths.RIGHT_SCALE_FRONT_TO_RIGHT_SCALE;


		CommandGroup zeroElevatorGroup = new CommandGroup();
		zeroElevatorGroup.addSequential(new WaitCommand(ELEVATOR_WAIT));
		zeroElevatorGroup.addSequential(new SetElevatorPositionCommand(robot.getElevator(),
				ElevatorSubsystem.GROUND_POSITION + 15));
		zeroElevatorGroup.addSequential(new WaitForElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.GROUND_POSITION + 15));
		zeroElevatorGroup.addSequential(new CalibrateElevatorEncoderCommand(robot.getElevator()));

		addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToScale));
		addParallel(zeroElevatorGroup);
		addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(), 180), 4);
	}
}
