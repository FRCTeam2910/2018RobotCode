package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.math.Vector2;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class DriveForDistanceCommand extends Command {
    private static final double TARGET_DISTANCE_BUFFER = 2;
    private static final double DISTANCE_CHECK_TIME = 0.25;

    private final SwerveDriveSubsystem drivetrain;
    private final double distance;
    private final double distRight, distForward;
    private final PIDController angleErrorController;
    private final Timer finishTimer = new Timer();
    private boolean isTimerStarted = false;

    public DriveForDistanceCommand(SwerveDriveSubsystem drivetrain, double distance) {
        this(drivetrain, 0, distance);
    }

    public DriveForDistanceCommand(SwerveDriveSubsystem drivetrain, double distRight, double distForward) {
        this.drivetrain = drivetrain;

        this.distRight = -distRight;
        this.distForward = distForward;

        this.distance = Math.sqrt(distRight * distRight + distForward * distForward);
        angleErrorController = new PIDController(0.02, 0, 0, new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kDisplacement;
            }

            @Override
            public double pidGet() {
                return drivetrain.getGyroAngle();
            }
        }, output -> {
            drivetrain.holonomicDrive(new Vector2(distRight / distance, -distRight / distance), -output,
                    SwerveDriveSubsystem.DriveMode.ANGLE_ONLY);
        });
        angleErrorController.setInputRange(0, 360);
        angleErrorController.setOutputRange(-0.5, 0.5);
        angleErrorController.setContinuous(true);

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        finishTimer.stop();
        finishTimer.reset();
        isTimerStarted = false;

        angleErrorController.setSetpoint(drivetrain.getGyroAngle());
        angleErrorController.enable();

        drivetrain.holonomicDrive(new Vector2(distRight, distForward), 0, SwerveDriveSubsystem.DriveMode.DISTANCE);
    }

    @Override
    protected boolean isFinished() {
        boolean inBuffer = true;
        for (int i = 0; i < 4; i++) {
            inBuffer &= Math.abs(distance - Math.abs(drivetrain.getSwerveModule(i).getDriveDistance())) < TARGET_DISTANCE_BUFFER;
        }

        if (inBuffer) {
            if (!isTimerStarted) {
                finishTimer.start();
                isTimerStarted = true;
            }
        } else {
            finishTimer.stop();
            finishTimer.reset();
            isTimerStarted = false;
        }

        return finishTimer.hasPeriodPassed(DISTANCE_CHECK_TIME);
    }

    @Override
    protected void end() {
        drivetrain.holonomicDrive(0, 0, 0);

        angleErrorController.disable();
    }

    @Override
    protected void interrupted() {
        end();
    }
}