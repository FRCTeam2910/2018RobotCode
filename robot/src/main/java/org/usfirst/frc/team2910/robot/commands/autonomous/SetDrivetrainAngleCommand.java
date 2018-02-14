package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class SetDrivetrainAngleCommand extends Command {
    private static final double ANGLE_CHECK_TIME = 0.5;
    private static final double TARGET_ANGLE_BUFFER = 0.3;


    private final SwerveDriveSubsystem drivetrain;
    private final double targetAngle;
    private final Timer finishTimer = new Timer();
    private boolean isTimerStarted = false;
    
    private double arcLength = 0;

    public SetDrivetrainAngleCommand(SwerveDriveSubsystem drivetrain, double targetAngle) {
        this.drivetrain = drivetrain;
        this.targetAngle = targetAngle;

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        finishTimer.stop();
        finishTimer.reset();
        isTimerStarted = false;

        double angleDiff = Math.toRadians((targetAngle - drivetrain.getGyroAngle()) % 360);
        arcLength = SwerveDriveSubsystem.TURNING_RADIUS * angleDiff;

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
            drivetrain.getSwerveModule(i).zeroDistance();
            drivetrain.getSwerveModule(i).setTargetDistance(arcLength);
        }
        
        SmartDashboard.putNumber("Set Angle Distance", arcLength);
    }

    @Override
    protected boolean isFinished() {
    	boolean inBuffer = true;
    	for (int i = 0; i < 4; i++) {
    		inBuffer &= Math.abs(arcLength - Math.abs(drivetrain.getSwerveModule(i).getDriveDistance())) < TARGET_ANGLE_BUFFER;
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

        return finishTimer.hasPeriodPassed(ANGLE_CHECK_TIME);
    }

    @Override
    protected void end() {
        drivetrain.holonomicDrive(0, 0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
