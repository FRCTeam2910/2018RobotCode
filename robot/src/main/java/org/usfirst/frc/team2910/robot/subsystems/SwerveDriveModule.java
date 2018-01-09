package org.usfirst.frc.team2910.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2910.robot.commands.SwerveModuleCommand;
import org.usfirst.frc.team2910.robot.util.MotorStallException;

public class SwerveDriveModule extends Subsystem {
	private static final long STALL_TIMEOUT = 2000;

	private long mStallTimeBegin = Long.MAX_VALUE;

	private double mLastError = 0, mLastTargetAngle = 0;

	private final int mModuleNumber;

	private final double mZeroOffset;

	private final TalonSRX mAngleMotor;
	private final TalonSRX mDriveMotor;

	public SwerveDriveModule(int moduleNumber, TalonSRX angleMotor, TalonSRX driveMotor, double zeroOffset) {
		mModuleNumber = moduleNumber;

		mAngleMotor = angleMotor;
		mDriveMotor = driveMotor;

		mZeroOffset = zeroOffset;

		angleMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog,0, 0);
		angleMotor.setSensorPhase(true);
		angleMotor.config_kP(0, 20, 0);
		angleMotor.config_kI(0, 0, 0);
		angleMotor.config_kD(0, 200, 0);
		angleMotor.set(ControlMode.Position, 0);

		driveMotor.setNeutralMode(NeutralMode.Brake);

		// Set amperage limits
		angleMotor.configContinuousCurrentLimit(30, 0);
		angleMotor.configPeakCurrentLimit(50, 0);
		angleMotor.configPeakCurrentDuration(100, 0);
		angleMotor.enableCurrentLimit(true);

		driveMotor.configContinuousCurrentLimit(30, 0);
		driveMotor.configPeakCurrentLimit(50, 0);
		driveMotor.configPeakCurrentDuration(100, 0);
		driveMotor.enableCurrentLimit(true);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new SwerveModuleCommand(this));
	}

	public TalonSRX getAngleMotor() {
		return mAngleMotor;
	}

	public TalonSRX getDriveMotor() {
		return mDriveMotor;
	}

	public double getTargetAngle() {
		return mLastTargetAngle;
	}

	public void robotDisabledInit() {
		mStallTimeBegin = Long.MAX_VALUE;
	}

	public void setTargetAngle(double targetAngle) {
		mLastTargetAngle = targetAngle;

		targetAngle %= 360;
		targetAngle += mZeroOffset;

		double currentAngle = mAngleMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
		double currentAngleMod = currentAngle % 360;
		if (currentAngleMod < 0) currentAngleMod += 360;

		double delta = currentAngleMod - targetAngle;

		if (delta > 180) {
			targetAngle += 360;
		} else if (delta < -180) {
			targetAngle -= 360;
		}

		delta = currentAngleMod - targetAngle;
		if (delta > 90 || delta < -90) {
			if (delta > 90)
				targetAngle += 180;
			else if (delta < -90)
				targetAngle -= 180;
			mDriveMotor.setInverted(false);
		} else {
			mDriveMotor.setInverted(true);
		}

		targetAngle += currentAngle - currentAngleMod;

		double currentError = mAngleMotor.getClosedLoopError(0);
		if (Math.abs(currentError - mLastError) < 7.5 &&
				Math.abs(currentAngle - targetAngle) > 5) {
			if (mStallTimeBegin == Long.MAX_VALUE) mStallTimeBegin = System.currentTimeMillis();
			if (System.currentTimeMillis() - mStallTimeBegin > STALL_TIMEOUT) {
				throw new MotorStallException(String.format("Angle motor on swerve module '%d' has stalled.",
						mModuleNumber));
			}
		} else {
			mStallTimeBegin = Long.MAX_VALUE;
		}
		mLastError = currentError;


		targetAngle *= 1024.0 / 360.0;
		mAngleMotor.set(ControlMode.Position, targetAngle);
	}

	public void setTargetSpeed(double speed) {
		mDriveMotor.set(ControlMode.PercentOutput, speed);
	}
}
