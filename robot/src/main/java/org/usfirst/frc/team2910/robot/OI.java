package org.usfirst.frc.team2910.robot;

import org.usfirst.frc.team2910.robot.commands.AdjustFieldOrientedAngleCommand;
import org.usfirst.frc.team2910.robot.commands.ResetDrivetrainEncoderCommand;
import org.usfirst.frc.team2910.robot.commands.ToggleFieldOrientedCommand;
import org.usfirst.frc.team2910.robot.input.DPadButton;
import org.usfirst.frc.team2910.robot.input.IGamepad;
import org.usfirst.frc.team2910.robot.input.XboxGamepad;

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
		primaryController.getAButton().whenPressed(new ResetDrivetrainEncoderCommand(mRobot.getDrivetrain()));
		primaryController.getStartButton().whenPressed(new ToggleFieldOrientedCommand(mRobot.getDrivetrain()));
		primaryController.getDPadButton(DPadButton.Direction.LEFT).whenPressed(new AdjustFieldOrientedAngleCommand(mRobot.getDrivetrain(), false));
		primaryController.getDPadButton(DPadButton.Direction.RIGHT).whenPressed(new AdjustFieldOrientedAngleCommand(mRobot.getDrivetrain(), true));
	}

	public IGamepad getPrimaryController() {
		return primaryController;
	}

	public IGamepad getSecondaryController() {
		return secondaryController;
	}
}
