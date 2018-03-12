package org.usfirst.frc.team2910.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2910.robot.Utilities;

public class SwerveDriveSubsystem extends HolonomicDrivetrain {
    public static final double WHEELBASE = 20.5;  // Swerve bot: 14.5
    public static final double TRACKWIDTH = 25.5; // Swerve bot: 13.5

    public static final double WIDTH = 37;  // Swerve bot: 20
    public static final double LENGTH = 32; // Swerve bot: 19

	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
	private SwerveDriveModule[] mSwerveModules = new SwerveDriveModule[] {
			new SwerveDriveModule(0, new TalonSRX(33), new TalonSRX(31), 321.328),
			new SwerveDriveModule(1, new TalonSRX(23), new TalonSRX(26), 245.742),
			new SwerveDriveModule(2, new TalonSRX(24), new TalonSRX(25), 88.242),
			new SwerveDriveModule(3, new TalonSRX(34), new TalonSRX(32), 234.492),
	};
//    private SwerveDriveModule[] mSwerveModules = new SwerveDriveModule[]{
//            new SwerveDriveModule(0, new TalonSRX(6), new TalonSRX(5), 253.47),
//            new SwerveDriveModule(1, new TalonSRX(3), new TalonSRX(4), 337.5),
//            new SwerveDriveModule(2, new TalonSRX(2), new TalonSRX(1), 11.95),
//            new SwerveDriveModule(3, new TalonSRX(7), new TalonSRX(8), 16.17),
//    };

    private AHRS mNavX = new AHRS(SPI.Port.kMXP, (byte) 200);

    private final PIDController snapController = new PIDController(0.03, 0, 0.075, mNavX, output -> {});

    public SwerveDriveSubsystem() {
        super(WIDTH, LENGTH);
        zeroGyro();

        mSwerveModules[1].setDriveInverted(true);
        mSwerveModules[2].setDriveInverted(true);
//      mSwerveModules[0].setDriveInverted(true);
//      mSwerveModules[3[.setDriveInverted(true);

        for (SwerveDriveModule module : mSwerveModules) {
            module.setTargetAngle(0);
            module.setDriveGearRatio(5.7777);
            module.setDriveWheelRadius(module.getDriveWheelRadius() * 1.05);
        }
    }

    public double[] calculateSwerveModuleAngles(double forward, double strafe, double rotation) {
        if (isFieldOriented()) {
            double angleRad = Math.toRadians(getGyroAngle());
            double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
            strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;
        }

        double a = strafe - rotation * (WHEELBASE / TRACKWIDTH);
        double b = strafe + rotation * (WHEELBASE / TRACKWIDTH);
        double c = forward - rotation * (TRACKWIDTH / WHEELBASE);
        double d = forward + rotation * (TRACKWIDTH / WHEELBASE);

        return new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,
                Math.atan2(a, c) * 180 / Math.PI
        };
    }

    public AHRS getNavX() {
        return mNavX;
    }

    public double getGyroAngle() {
        double angle = mNavX.getAngle() - getAdjustmentAngle();
        angle %= 360;
        if (angle < 0) angle += 360;

        return 360 - angle;
//		return angle;
    }

    public double getGyroRate() {
        return mNavX.getRate();
    }

    public double getRawGyroAngle() {
        double angle = mNavX.getAngle();
        angle %= 360;
        if (angle < 0) angle += 360;

        return angle;
    }

    public SwerveDriveModule getSwerveModule(int i) {
        return mSwerveModules[i];
    }

    @Override
    public void holonomicDrive(double forward, double strafe, double rotation) {
        if (snapController.isEnabled()) {
            if (Utilities.deadband(rotation) == 0) {
                rotation = snapController.get();
            } else {
                snapController.disable();
            }
        }

        forward *= getSpeedMultiplier();
        strafe *= getSpeedMultiplier();
        rotation = -rotation;
        if (isFieldOriented()) {
            double angleRad = Math.toRadians(getGyroAngle());
            double temp = forward * Math.cos(angleRad) +
                    strafe * Math.sin(angleRad);
            strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;
        }

        double a = strafe - rotation * (WHEELBASE / TRACKWIDTH);
        double b = strafe + rotation * (WHEELBASE / TRACKWIDTH);
        double c = forward - rotation * (TRACKWIDTH / WHEELBASE);
        double d = forward + rotation * (TRACKWIDTH / WHEELBASE);

        double[] angles = new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,
                Math.atan2(a, c) * 180 / Math.PI
        };

        double[] speeds = new double[]{
                Math.sqrt(b * b + c * c),
                Math.sqrt(b * b + d * d),
                Math.sqrt(a * a + d * d),
                Math.sqrt(a * a + c * c)
        };

        double max = speeds[0];

        for (double speed : speeds) {
            if (speed > max) {
                max = speed;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (Math.abs(forward) > 0.05 ||
                    Math.abs(strafe) > 0.05 ||
                    Math.abs(rotation) > 0.05) {
                mSwerveModules[i].setTargetAngle(angles[i] + 180);
            } else {
                mSwerveModules[i].setTargetAngle(mSwerveModules[i].getTargetAngle());
            }
            mSwerveModules[i].setTargetSpeed(speeds[i]);
        }
    }

    @Override
    public void stopDriveMotors() {
        for (SwerveDriveModule module : mSwerveModules) {
            module.setTargetSpeed(0);
        }
    }
    
    public void resetMotors() {
    	for(int i = 0; i < mSwerveModules.length; i++) {
    		mSwerveModules[i].resetMotor();
    	}
    }

    public void setSnapAngle(double snapAngle) {
        snapController.setSetpoint(snapAngle);
        snapController.enable();
    }
}
