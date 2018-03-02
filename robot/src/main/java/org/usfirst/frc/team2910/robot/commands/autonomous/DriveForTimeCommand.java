package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class DriveForTimeCommand extends Command {

    private final Timer timer = new Timer();
    private final SwerveDriveSubsystem drivetrain;
    private final double time;
    private final double forward;
    private final double strafe;

    public DriveForTimeCommand(SwerveDriveSubsystem drivetrain, double time, double forward, double strafe) {
        this.drivetrain = drivetrain;
        this.time = time;
        this.forward = forward;
        this.strafe = strafe;

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        timer.reset();
        timer.start();

        drivetrain.holonomicDrive(forward, strafe, 0);
    }

    @Override
    protected boolean isFinished() {
        return timer.hasPeriodPassed(time);
    }

    @Override
    protected void end() {
        timer.stop();
        timer.reset();
        drivetrain.holonomicDrive(0, 0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
