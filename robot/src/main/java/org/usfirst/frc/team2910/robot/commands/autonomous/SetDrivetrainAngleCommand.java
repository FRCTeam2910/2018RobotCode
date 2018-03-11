package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.math.Vector2;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class SetDrivetrainAngleCommand extends Command {
    private static final double ANGLE_CHECK_TIME = 0.5;
    private static final double TARGET_ANGLE_BUFFER = 3.0;

    private final SwerveDriveSubsystem drivetrain;
    private final double targetAngle;
    private final PIDController angleController;
    private final Timer finishTimer = new Timer();
    private boolean isTimerStarted = false;

    public SetDrivetrainAngleCommand(SwerveDriveSubsystem drivetrain, double targetAngle) {
        this.drivetrain = drivetrain;

        if (targetAngle < 0)
            targetAngle += 360;
        this.targetAngle = targetAngle;

        angleController = new PIDController(0.03, 0, 0.075, new PIDSource() {
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
            drivetrain.holonomicDrive(new Vector2(), output, SwerveDriveSubsystem.DriveMode.PERCENTAGE);
        });
        angleController.setInputRange(0, 360);
        angleController.setOutputRange(-0.5, 0.5);
        angleController.setContinuous(true);

        angleController.setSetpoint(targetAngle);

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        finishTimer.stop();
        finishTimer.reset();
        isTimerStarted = false;

        angleController.enable();
    }

    @Override
    protected boolean isFinished() {
        double currentAngle = drivetrain.getGyroAngle();
        double currentError = currentAngle - targetAngle;

        boolean inTargetBuffer = Math.abs(currentError) < TARGET_ANGLE_BUFFER
                | 360 - Math.abs(currentError) < TARGET_ANGLE_BUFFER;

        if (inTargetBuffer) {
            if (!isTimerStarted) {
                finishTimer.start();
                isTimerStarted = true;
            }
        } else {
            finishTimer.stop();
            finishTimer.reset();
            isTimerStarted = false;
        }

        return finishTimer.hasPeriodPassed(ANGLE_CHECK_TIME);
    }

    @Override
    protected void end() {
        angleController.disable();
        drivetrain.holonomicDrive(0, 0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
