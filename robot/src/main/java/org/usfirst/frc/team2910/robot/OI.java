package org.usfirst.frc.team2910.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.commands.*;
import org.usfirst.frc.team2910.robot.input.DPadButton;
import org.usfirst.frc.team2910.robot.input.IGamepad;
import org.usfirst.frc.team2910.robot.input.XboxGamepad;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private IGamepad primaryController = new XboxGamepad(0);
	private IGamepad secondaryController = new XboxGamepad(1);

	private Robot mRobot;

	public OI(Robot robot) {
		mRobot = robot;
	}

	public void registerControls() {
		{
			CommandGroup group = new CommandGroup();
			group.addSequential(new ResetDrivetrainEncoderCommand(mRobot.getDrivetrain()));
			group.addSequential(new SetFieldOrientedCommand(mRobot.getDrivetrain()));
			primaryController.getRightBumperButton().whenPressed(group);
		}
		primaryController.getStartButton().whenPressed(new ToggleFieldOrientedCommand(mRobot.getDrivetrain()));

		// TODO: PrimaryController: A launches cube out of carriage for x seconds
		primaryController.getAButton().whenPressed(new LaunchCubeCommand(mRobot.getGatherer(), 1));
		primaryController.getBButton().whenPressed(new SetElevatorPositionCommand(mRobot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
		primaryController.getXButton().whenPressed(new SetElevatorPositionCommand(mRobot.getElevator(), ElevatorSubsystem.SCORE_SCALE_POSITION));
		primaryController.getYButton().whenPressed(new SetElevatorPositionCommand(mRobot.getElevator(), ElevatorSubsystem.GROUND_POSITION));

		// TODO: PrimaryController: DPad snaps to angle and doesn't interfere with joystick drive

		// TODO: PrimaryController: Triggers make micro-adjustments on elevator height (include trigger value)

		secondaryController.getDPadButton(DPadButton.Direction.UP).whenPressed(new SetElevatorPositionCommand(mRobot.getElevator(), ElevatorSubsystem.SCORE_SCALE_POSITION));
		secondaryController.getDPadButton(DPadButton.Direction.RIGHT).whenPressed(new SetElevatorPositionCommand(mRobot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
		secondaryController.getDPadButton(DPadButton.Direction.DOWN).whenPressed(new SetElevatorPositionCommand(mRobot.getElevator(), ElevatorSubsystem.GROUND_POSITION));

		// TODO: Secondary Controller: Toggles elevator climbing mode (low gear, fast right joystick Y adjustments, lock when no input)
		secondaryController.getLeftBumperButton().whenPressed(new ChangeElevatorModeCommand(mRobot.getElevator(), ElevatorSubsystem.Mode.Climbing));
		secondaryController.getRightBumperButton().whenPressed(new ChangeElevatorModeCommand(mRobot.getElevator(), ElevatorSubsystem.Mode.Regular));
		secondaryController.getDPadButton(DPadButton.Direction.LEFT).toggleWhenPressed(new ToggleElevatorModeCommand(mRobot.getElevator()));

		secondaryController.getBackButton().whenPressed(new CalibrateElevatorEncoderCommand(mRobot.getElevator()));

		secondaryController.getLeftTriggerButton().whileHeld(new ActuateGathererCommand(mRobot.getGatherer(), true));
		secondaryController.getRightTriggerButton().whileHeld(new ActuateGathererCommand(mRobot.getGatherer(), false));

		// TODO: Secondary Controller: Right Joystick Y makes micro-adjustments on elevator height
	}

	public IGamepad getPrimaryController() {
		return primaryController;
	}

	public IGamepad getSecondaryController() {
		return secondaryController;
	}
}
