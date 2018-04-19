package org.usfirst.frc.team2910.robot.subsystems;

import org.usfirst.frc.team2910.robot.commands.HolonomicDriveCommand;

public abstract class HolonomicDrivetrain extends Drivetrain {

	private double mAdjustmentAngle = 0;
	private boolean mFieldOriented = true;

	public HolonomicDrivetrain(double width, double length) {
		super(width, length);
	}


	public double getAdjustmentAngle() {
		return mAdjustmentAngle;
	}

	public abstract double getGyroAngle();

	public abstract double getRawGyroAngle();

	public void holonomicDrive(double forward, double strafe, double rotation) {
		holonomicDrive(forward, strafe, rotation, isFieldOriented());
	}

	public abstract void holonomicDrive(double forward, double strafe, double rotation, boolean fieldOriented);

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new HolonomicDriveCommand(this));
	}

	public boolean isFieldOriented() {
		return mFieldOriented;
	}

	public void setAdjustmentAngle(double adjustmentAngle) {
		System.out.printf("New Adjustment Angle: % .3f\n", adjustmentAngle);
		mAdjustmentAngle = adjustmentAngle;
	}

	public void setFieldOriented(boolean fieldOriented) {
		mFieldOriented = fieldOriented;
	}

	public abstract void stopDriveMotors();

	public void zeroGyro() {
		setAdjustmentAngle(getRawGyroAngle());
	}
}
