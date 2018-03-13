package org.usfirst.frc.team2910.robot.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2910.robot.commands.SwerveModuleCommand;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class SwerveDriveModule extends Subsystem {
    private static final long STALL_TIMEOUT = 2000;

    private long mStallTimeBegin = Long.MAX_VALUE;

    private double mLastError = 0, lastTargetAngle = 0;

    private final int moduleNumber;

    private final double mZeroOffset;

    private final TalonSRX mAngleMotor;
    private final TalonSRX mDriveMotor;

    private boolean driveInverted = false;
    private double driveGearRatio = 1;
    private double driveWheelRadius = 2;
    private boolean angleMotorJam = false;

    private Queue<TrajectoryPoint> motionPointQueue = new LinkedList<>();

    public SwerveDriveModule(int moduleNumber, TalonSRX angleMotor, TalonSRX driveMotor, double zeroOffset) {
        this.moduleNumber = moduleNumber;

        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;

        mZeroOffset = zeroOffset;

        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        angleMotor.setSensorPhase(true);
        angleMotor.config_kP(0, 30, 0);
        angleMotor.config_kI(0, 0.001, 0);
        angleMotor.config_kD(0, 200, 0);
        angleMotor.setNeutralMode(NeutralMode.Brake);
        angleMotor.set(ControlMode.Position, 0);

        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        driveMotor.configNeutralDeadband(0.05, 40);
//        driveMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
        driveMotor.config_kP(0, 2.0, 0);
        driveMotor.config_kI(0, 0.0, 0);
        driveMotor.config_kD(0, 20.0, 0);
        driveMotor.config_kF(0, 0.076, 0);

        driveMotor.configMotionProfileTrajectoryPeriod(0, 40);
        driveMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 40);
        driveMotor.changeMotionControlFramePeriod(20);


        driveMotor.configMotionCruiseVelocity(720, 0);
        driveMotor.configMotionAcceleration(240, 0);

        driveMotor.setNeutralMode(NeutralMode.Brake);

        // Set amperage limits
        angleMotor.configContinuousCurrentLimit(30, 0);
        angleMotor.configPeakCurrentLimit(30, 0);
        angleMotor.configPeakCurrentDuration(100, 0);
        angleMotor.enableCurrentLimit(true);

        driveMotor.configContinuousCurrentLimit(30, 0);
        driveMotor.configPeakCurrentLimit(50, 0);
        driveMotor.configPeakCurrentDuration(100, 0);
        driveMotor.enableCurrentLimit(true);
        
    	SmartDashboard.putBoolean("Motor Jammed" + moduleNumber, angleMotorJam);
    }

    private double encoderTicksToInches(double ticks) {
        return ticks / 37.8;
    }

    private double inchesToEncoderTicks(double inches) {
        return inches * 37.8;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SwerveModuleCommand(this));
    }

    public TalonSRX getAngleMotor() {
        return mAngleMotor;
    }

    /**
     * Get the current angle of the swerve module
     *
     * @return An angle in the range [0, 360)
     */
    public double getCurrentAngle() {
        double angle = mAngleMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
        angle -= mZeroOffset;
        angle %= 360;
        if (angle < 0) angle += 360;

        return angle;
    }

    public double getDriveDistance() {
        return encoderTicksToInches(mDriveMotor.getSelectedSensorPosition(0));
    }

    public TalonSRX getDriveMotor() {
        return mDriveMotor;
    }

    public double getTargetAngle() {
        return lastTargetAngle;
    }

    public void robotDisabledInit() {
        mStallTimeBegin = Long.MAX_VALUE;
    }

    public void setDriveGearRatio(double ratio) {
        driveGearRatio = ratio;
    }

    public void setDriveInverted(boolean inverted) {
        driveInverted = inverted;
    }

    public double getDriveWheelRadius() {
        return driveWheelRadius;
    }

    public void setDriveWheelRadius(double radius) {
        driveWheelRadius = radius;
    }

    public void setTargetAngle(double targetAngle) {
    	if(angleMotorJam) {
    		mAngleMotor.set(ControlMode.Disabled, 0);
    		return;
    	}
    	
        lastTargetAngle = targetAngle;

        targetAngle %= 360;

        SmartDashboard.putNumber("Module Target Angle " + moduleNumber, targetAngle % 360);

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
            if (mStallTimeBegin == Long.MAX_VALUE) {
            	mStallTimeBegin = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - mStallTimeBegin > STALL_TIMEOUT) {
            	angleMotorJam = true;
            	mAngleMotor.set(ControlMode.Disabled, 0);
            	mDriveMotor.set(ControlMode.Disabled, 0);
            	SmartDashboard.putBoolean("Motor Jammed" + moduleNumber, angleMotorJam);
            	return;
            }
        } else {
            mStallTimeBegin = Long.MAX_VALUE;
        }
        mLastError = currentError;
        targetAngle *= 1024.0 / 360.0;
        mAngleMotor.set(ControlMode.Position, targetAngle);
    }

    public void setTargetDistance(double distance) {
    	if(angleMotorJam) {
    		mDriveMotor.set(ControlMode.Disabled, 0);
    		return;
    	}
        if (driveInverted) distance = -distance;

//        distance /= 2 * Math.PI * driveWheelRadius; // to wheel rotations
//        distance *= driveGearRatio; // to encoder rotations
//        distance *= 80; // to encoder ticks

        distance = inchesToEncoderTicks(distance);

        SmartDashboard.putNumber("Module Ticks " + moduleNumber, distance);

        mDriveMotor.set(ControlMode.MotionMagic, distance);
    }

    public void setTargetSpeed(double speed) {
    	if(angleMotorJam) {
    		mDriveMotor.set(ControlMode.Disabled, 0);
    		return;
    	}
        if (driveInverted) speed = -speed;

        mDriveMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setTargetVelocity(double velocity) {
        if (angleMotorJam) {
            mDriveMotor.set(ControlMode.Disabled, 0);
        }
        if (driveInverted) velocity = -velocity;

        mDriveMotor.set(ControlMode.Velocity, inchesToEncoderTicks(velocity));
    }

    public void zeroDistance() {
        mDriveMotor.setSelectedSensorPosition(0, 0, 0);
    }
    
    public void resetMotor() {
    	angleMotorJam = false;
    	mStallTimeBegin = Long.MAX_VALUE;
    	SmartDashboard.putBoolean("Motor Jammed" + moduleNumber, angleMotorJam);
    }

    public void pushMotionPoint(TrajectoryPoint angle, TrajectoryPoint drive) {
        angle.position = inchesToEncoderTicks(angle.position);
        angle.velocity = inchesToEncoderTicks(angle.velocity) / 10;
        drive.position = inchesToEncoderTicks(drive.position);
        drive.velocity = inchesToEncoderTicks(drive.velocity) / 10;

        motionPointQueue.add(drive);
    }

    public void processMotionBuffer() {
        while (!mDriveMotor.isMotionProfileTopLevelBufferFull() && !motionPointQueue.isEmpty()) {
            mDriveMotor.pushMotionProfileTrajectory(motionPointQueue.remove());
        }

        SmartDashboard.putNumber(String.format("Module %d top points", moduleNumber), motionPointQueue.size());

        mAngleMotor.processMotionProfileBuffer();
        mDriveMotor.processMotionProfileBuffer();

        MotionProfileStatus status = new MotionProfileStatus();
        mDriveMotor.getMotionProfileStatus(status);

        SmartDashboard.putNumber(String.format("Module %d middle points", moduleNumber), mDriveMotor.getMotionProfileTopLevelBufferCount());
        SmartDashboard.putNumber(String.format("Module %d bottom points", moduleNumber), status.btmBufferCnt);
    }

    public boolean isMotionProfileComplete() {
        MotionProfileStatus status = new MotionProfileStatus();
        mAngleMotor.getMotionProfileStatus(status);

        if (status.isLast) {
            mDriveMotor.getMotionProfileStatus(status);

            return status.isLast;
        }
        return false;
    }

    public void enableMotionProfiling() {
        mDriveMotor.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
    }

    public void clearMotionProfilingBuffer() {
        mAngleMotor.clearMotionProfileTrajectories();
        mDriveMotor.clearMotionProfileTrajectories();

        motionPointQueue.clear();

        mAngleMotor.clearMotionProfileHasUnderrun(0);
        mDriveMotor.clearMotionProfileHasUnderrun(0);
    }

    public double getCurrentSpeed() {
        return encoderTicksToInches(mDriveMotor.getSensorCollection().getQuadratureVelocity()) * 10;
    }
}
