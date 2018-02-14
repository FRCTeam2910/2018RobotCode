package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class DriveForDistanceCommand extends Command {
    private static final double TARGET_DISTANCE_BUFFER = 0.3;
    private static final double DISTANCE_CHECK_TIME = 0.5;

    private final SwerveDriveSubsystem drivetrain;
    private final double angle;
    private final double distance;
    private final Timer finishTimer = new Timer();
    private boolean isTimerStarted = false;

    public DriveForDistanceCommand(SwerveDriveSubsystem drivetrain, double distance) {
        this(drivetrain, 0, distance);
    }

    public DriveForDistanceCommand(SwerveDriveSubsystem drivetrain, double distLeft, double distForward) {
        System.out.println("FWD: " + distForward);
    	
    	this.drivetrain = drivetrain;
        this.angle = Math.toDegrees(Math.atan2(distLeft, distForward));
        
        this.distance = Math.sqrt(distLeft*distLeft + distForward*distForward);

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        finishTimer.stop();
        finishTimer.reset();
        isTimerStarted = false;

        for (int i = 0; i < 4; i++) {
            drivetrain.getSwerveModule(i).setTargetAngle(angle + drivetrain.getGyroAngle());
            drivetrain.getSwerveModule(i).zeroDistance();
            drivetrain.getSwerveModule(i).setTargetDistance(distance);
        }
        
        System.out.printf("Module Angles: % .3f\n", angle);
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
    }

    @Override
    protected void interrupted() {
        end();
    }
}