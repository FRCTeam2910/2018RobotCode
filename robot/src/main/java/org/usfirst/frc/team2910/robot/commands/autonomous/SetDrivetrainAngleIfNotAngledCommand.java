package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class SetDrivetrainAngleIfNotAngledCommand extends Command {
    private static final double NOT_ANGLED_TOLERANCE = 20;
    private static final double ANGLE_CHECK_TIME = 0.1;
    private static final double TARGET_ANGLE_BUFFER = 5.0;

    private final SwerveDriveSubsystem drivetrain;
    private final double targetAngle;
    private final PIDController angleController;
    private final Timer finishTimer = new Timer();
    private boolean isTimerStarted = false;
    private boolean withinTolerance = false;

    public SetDrivetrainAngleIfNotAngledCommand(SwerveDriveSubsystem drivetrain, double targetAngle) {
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
            for (int i = 0; i < 4; i++)
                drivetrain.getSwerveModule(i).setTargetSpeed(output);
        });
        if (Robot.PRACTICE_BOT)
            angleController.setP(0.025);

        angleController.setInputRange(0, 360);
        angleController.setOutputRange(-0.5, 0.5);
        angleController.setContinuous(true);

        angleController.setSetpoint(targetAngle);

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        withinTolerance = Math.abs(drivetrain.getGyroAngle() - targetAngle) < NOT_ANGLED_TOLERANCE;
        if (withinTolerance) return;

        finishTimer.stop();
        finishTimer.reset();
        isTimerStarted = false;

        double a = -(SwerveDriveSubsystem.WHEELBASE / SwerveDriveSubsystem.TRACKWIDTH);
        double b = (SwerveDriveSubsystem.WHEELBASE / SwerveDriveSubsystem.TRACKWIDTH);
        double c = -(SwerveDriveSubsystem.TRACKWIDTH / SwerveDriveSubsystem.WHEELBASE);
        double d = (SwerveDriveSubsystem.TRACKWIDTH / SwerveDriveSubsystem.WHEELBASE);

        double[] angles = new double[]{
                Math.atan2(b, c),
                Math.atan2(b, d),
                Math.atan2(a, d),
                Math.atan2(a, c)
        };

        for (int i = 0; i < 4; i++) {
            drivetrain.getSwerveModule(i).setTargetAngle(Math.toDegrees(angles[i]));
        }

        angleController.enable();

        System.out.printf("[INFO]: Turning to %.3f%n", targetAngle);
    }

    @Override
    protected boolean isFinished() {
        if (withinTolerance) return true;

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
        System.out.println("[INFO]: Done turning");

        angleController.disable();
        drivetrain.holonomicDrive(0, 0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
