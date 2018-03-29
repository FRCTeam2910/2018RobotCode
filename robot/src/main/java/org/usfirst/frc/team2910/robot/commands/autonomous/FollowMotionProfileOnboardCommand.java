package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.frcteam2910.motion_profiling.MotionProfile;
import org.frcteam2910.motion_profiling.MotionProfileFollower;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class FollowMotionProfileOnboardCommand extends Command {
    public static final double FOLLOWER_P = 1.0 / 60.0, FOLLOWER_I = 0, FOLLOWER_D = 0, FOLLOWER_V = 1.0 / 200.0, FOLLOWER_A = 0;

    private final SwerveDriveSubsystem drivetrain;
    private final MotionProfileFollower frontLeft, frontRight, backLeft, backRight;

    private final PIDController angleErrorController;
    private double rotationFactor;
    
    private Writer frontLeftWriter, frontRightWriter, backLeftWriter, backRightWriter;
    
    private double startTime;
    
    public FollowMotionProfileOnboardCommand(SwerveDriveSubsystem drivetrain, List<MotionProfile> profiles) {
        this(drivetrain, profiles.toArray(new MotionProfile[4]));
    }

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

        angleErrorController = new PIDController(0.02, 0, 0, new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) { }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kDisplacement;
            }

            @Override
            public double pidGet() {
                return drivetrain.getGyroAngle();
            }
        }, output -> {
            rotationFactor = output;
        });
        angleErrorController.setInputRange(0, 360);
        angleErrorController.setOutputRange(-0.5, 0.5);

        try {
            frontLeftWriter = new FileWriter(new File("/tmp/velocity_fl.csv"));
            frontRightWriter = new FileWriter(new File("/tmp/velocity_fr.csv"));
            backLeftWriter = new FileWriter(new File("tmp/velocity_bl.csv"));
            backRightWriter = new FileWriter(new File("/tmp/velocity_br.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        angleErrorController.setSetpoint(drivetrain.getGyroAngle());
        angleErrorController.enable();
        
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
        drivetrain.getFrontLeftModule().setTargetSpeed(frontLeft.calculate(-drivetrain.getFrontLeftModule().getDriveDistance()));

        double[] moduleAngles = drivetrain.calculateSwerveModuleAngles(
                Math.cos(frontLeft.getHeading()),
                Math.sin(frontLeft.getHeading()),
                0
        );

        drivetrain.getFrontLeftModule().setTargetAngle(moduleAngles[1]);

        drivetrain.getFrontRightModule().setTargetSpeed(frontRight.calculate(-drivetrain.getFrontRightModule().getDriveDistance()));
        drivetrain.getFrontRightModule().setTargetAngle(moduleAngles[0]);

        drivetrain.getBackLeftModule().setTargetSpeed(backLeft.calculate(-drivetrain.getBackLeftModule().getDriveDistance()));
        drivetrain.getBackLeftModule().setTargetAngle(moduleAngles[2]);

        drivetrain.getBackRightModule().setTargetSpeed(backRight.calculate(-drivetrain.getBackRightModule().getDriveDistance()));
        drivetrain.getBackRightModule().setTargetAngle(moduleAngles[3]);

        try {
            double elapsed = Timer.getFPGATimestamp();
            frontLeftWriter.write(String.format("%f,%f%n", elapsed, drivetrain.getFrontLeftModule().getCurrentSpeed()));
            frontRightWriter.write(String.format("%f,%f%n", elapsed, drivetrain.getFrontRightModule().getCurrentSpeed()));
            backLeftWriter.write(String.format("%f,%f%n", elapsed, drivetrain.getBackLeftModule().getCurrentSpeed()));
            backRightWriter.write(String.format("%f,%f%n", elapsed, drivetrain.getBackRightModule().getCurrentSpeed()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isFinished() {
        return frontLeft.isFinished() && frontRight.isFinished() && backLeft.isFinished() && backRight.isFinished();
    }

    @Override
    protected void end() {
        angleErrorController.disable();
    }
}
