package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.HolonomicDrivetrain;

public class ZeroDrivetrainGyroCommand extends Command {
	private HolonomicDrivetrain mDrivetrain;

	public ZeroDrivetrainGyroCommand(HolonomicDrivetrain drivetrain) {
		mDrivetrain = drivetrain;
	}

	@Override
	public void execute() {
		mDrivetrain.zeroGyro();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
