package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class SnapToAngleCommand extends Command {
    private final SwerveDriveSubsystem drivetrain;
    private final double angle;

    public SnapToAngleCommand(SwerveDriveSubsystem drivetrain, double angle) {
        this.drivetrain = drivetrain;
        this.angle = angle;
    }

    @Override
    protected void initialize() {
        drivetrain.setSnapAngle(angle);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
