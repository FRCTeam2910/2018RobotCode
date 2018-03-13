package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.frcteam2910.motion_profiling.MotionProfile;
import org.frcteam2910.motion_profiling.MotionProfileFollower;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class FollowMotionProfileOnboardCommand extends Command {
    public static final double FOLLOWER_P = 1.0 / 30.0, FOLLOWER_I = 0, FOLLOWER_D = 0, FOLLOWER_V = 1.0 / 200.0, FOLLOWER_A = 0;


    private final SwerveDriveSubsystem drivetrain;
    private final MotionProfileFollower frontLeft, frontRight, backLeft, backRight;

    public FollowMotionProfileOnboardCommand(SwerveDriveSubsystem drivetrain, MotionProfile[] profiles) {
        this.drivetrain = drivetrain;

        frontLeft = new MotionProfileFollower(profiles[0]);
        frontLeft.setPIDVA(FOLLOWER_P, FOLLOWER_I, FOLLOWER_D, FOLLOWER_V, FOLLOWER_A);

        frontRight = new MotionProfileFollower(profiles[1]);
        frontRight.setPIDVA(FOLLOWER_P, FOLLOWER_I, FOLLOWER_D, FOLLOWER_V, FOLLOWER_A);

        backLeft = new MotionProfileFollower(profiles[2]);
        backLeft.setPIDVA(FOLLOWER_P, FOLLOWER_I, FOLLOWER_D, FOLLOWER_V, FOLLOWER_A);

        backRight = new MotionProfileFollower(profiles[3]);
        backRight.setPIDVA(FOLLOWER_P, FOLLOWER_I, FOLLOWER_D, FOLLOWER_V, FOLLOWER_A);

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        frontLeft.reset();
        frontRight.reset();
        backLeft.reset();
        backRight.reset();

        drivetrain.getFrontLeftModule().zeroDistance();
        drivetrain.getFrontRightModule().zeroDistance();
        drivetrain.getBackLeftModule().zeroDistance();
        drivetrain.getBackRightModule().zeroDistance();
    }
    @Override
    protected void execute() {
        drivetrain.getFrontLeftModule().setTargetSpeed(frontLeft.calculate(drivetrain.getFrontLeftModule().getDriveDistance()));
        drivetrain.getFrontLeftModule().setTargetAngle(Math.toDegrees(frontLeft.getHeading()));

        drivetrain.getFrontRightModule().setTargetSpeed(frontRight.calculate(-drivetrain.getFrontRightModule().getDriveDistance()));
        drivetrain.getFrontRightModule().setTargetAngle(Math.toDegrees(frontRight.getHeading()));

        drivetrain.getBackLeftModule().setTargetSpeed(backLeft.calculate(-drivetrain.getBackLeftModule().getDriveDistance()));
        drivetrain.getBackLeftModule().setTargetAngle(Math.toDegrees(backLeft.getHeading()));

        drivetrain.getBackRightModule().setTargetSpeed(backRight.calculate(drivetrain.getBackRightModule().getDriveDistance()));
        drivetrain.getBackRightModule().setTargetAngle(Math.toDegrees(backRight.getHeading()));

    }

    @Override
    protected boolean isFinished() {
        return frontLeft.isFinished() && frontRight.isFinished() && backLeft.isFinished() && backRight.isFinished();
    }
}
