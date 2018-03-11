package org.usfirst.frc.team2910.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.usfirst.frc.team2910.robot.math.Matrix2x2;
import org.usfirst.frc.team2910.robot.math.Vector2;

public class SwerveDriveSubsystem extends HolonomicDrivetrain {
    public enum DriveMode {
        ANGLE_ONLY,
        DISTANCE,
        PERCENTAGE,
    }

    public static final double WHEELBASE = 20.5;  // Swerve bot: 14.5
    public static final double TRACKWIDTH = 25.5; // Swerve bot: 13.5

    public static final double WIDTH = 37;  // Swerve bot: 20
    public static final double LENGTH = 32; // Swerve bot: 19


    private SwerveDriveModule frontLeftModule = new SwerveDriveModule(0, new TalonSRX(33), new TalonSRX(31), 321.328);
    private SwerveDriveModule frontRightModule = new SwerveDriveModule(1, new TalonSRX(23), new TalonSRX(26), 245.742);
    private SwerveDriveModule backLeftModule = new SwerveDriveModule(2, new TalonSRX(24), new TalonSRX(25), 88.242);
    private SwerveDriveModule backRightModule = new SwerveDriveModule(3, new TalonSRX(34), new TalonSRX(32), 234.492);

    //    private SwerveDriveModule[] mSwerveModules = new SwerveDriveModule[]{
//            new SwerveDriveModule(0, new TalonSRX(6), new TalonSRX(5), 253.47),
//            new SwerveDriveModule(1, new TalonSRX(3), new TalonSRX(4), 337.5),
//            new SwerveDriveModule(2, new TalonSRX(2), new TalonSRX(1), 11.95),
//            new SwerveDriveModule(3, new TalonSRX(7), new TalonSRX(8), 16.17),
//    };

    private SwerveDriveModule[] modules = new SwerveDriveModule[]{frontRightModule, frontLeftModule, backLeftModule, backRightModule};

    private AHRS mNavX = new AHRS(SPI.Port.kMXP, (byte) 200);

    public SwerveDriveSubsystem() {
        super(WIDTH, LENGTH);
        zeroGyro();

        frontLeftModule.setDriveInverted(true);
        backLeftModule.setDriveInverted(true);
//      frontRightModule.setDriveInverted(true);
//      backRightModule.setDriveInverted(true);

        for (SwerveDriveModule module : modules) {
            module.setTargetAngle(0);
            module.setDriveGearRatio(5.7777);
            module.setDriveWheelRadius(module.getDriveWheelRadius() * 1.05);
        }
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
        return modules[i];
    }

    @Override
    public void holonomicDrive(double forward, double strafe, double rotation) {
        holonomicDrive(new Vector2(forward, strafe), rotation, DriveMode.PERCENTAGE);
    }

    /**
     * Drive the robot at a certain velocity with a certain rate of rotation in radians per second
     * <p>
     * <a href="https://www.chiefdelphi.com/media/papers/2426">Lots of good information can be found here</a>
     *
     * @param velocity The target velocity of the robot
     * @param rotation The clockwise rate of robot rotation in radians per second
     */
    public void holonomicDrive(Vector2 velocity, double rotation, DriveMode mode) {
        velocity = velocity.copy().multiply(getSpeedMultiplier());

        if (isFieldOriented()) {
            double angle = Math.toRadians(getGyroAngle());
            velocity = new Matrix2x2(new double[][]{
                    new double[]{Math.cos(angle), -Math.sin(angle)},
                    new double[]{Math.sin(angle), Math.cos(angle)}
            }).multiply(velocity);
        }

        Vector2 frontLeftVelocity = new Vector2(-TRACKWIDTH / 2, WHEELBASE / 2).multiply(rotation).add(velocity);
        Vector2 frontRightVelocity = new Vector2(TRACKWIDTH / 2, WHEELBASE / 2).multiply(rotation).add(velocity);
        Vector2 backLeftVelocity = new Vector2(-TRACKWIDTH / 2, -WHEELBASE / 2).multiply(rotation).add(velocity);
        Vector2 backRightVelocity = new Vector2(TRACKWIDTH / 2, -WHEELBASE / 2).multiply(rotation).add(velocity);
        frontLeftModule.setTargetAngle(Math.toDegrees(frontLeftVelocity.getAngle()));
        frontRightModule.setTargetAngle(Math.toDegrees(frontRightVelocity.getAngle()));
        backLeftModule.setTargetAngle(Math.toDegrees(backLeftVelocity.getAngle()));
        backRightModule.setTargetAngle(Math.toDegrees(backRightVelocity.getAngle()));

        switch (mode) {
            case ANGLE_ONLY:
                break;
            case DISTANCE:
                frontLeftModule.setTargetDistance(frontLeftVelocity.getMagnitude());
                frontRightModule.setTargetDistance(frontRightVelocity.getMagnitude());
                backLeftModule.setTargetDistance(backLeftVelocity.getMagnitude());
                backRightModule.setTargetDistance(backRightVelocity.getMagnitude());
                break;
            case PERCENTAGE:
                frontLeftModule.setTargetSpeed(frontLeftVelocity.getMagnitude());
                frontRightModule.setTargetSpeed(frontRightVelocity.getMagnitude());
                backLeftModule.setTargetSpeed(backLeftVelocity.getMagnitude());
                backRightModule.setTargetSpeed(backRightVelocity.getMagnitude());
                break;
        }
    }

    @Override
    public void stopDriveMotors() {
        for (SwerveDriveModule module : modules) {
            module.setTargetSpeed(0);
        }
    }

    public void resetMotors() {
        for (SwerveDriveModule module : modules) {
            module.resetMotor();
        }
    }
}
