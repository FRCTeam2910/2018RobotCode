package org.usfirst.frc.team2910.robot.commands.autonomous;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveModule;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class DriveForDistanceCommand extends Command {
    private final SwerveDriveSubsystem drivetrain;
    private final double angle;
    private final double distance;

    public DriveForDistanceCommand(SwerveDriveSubsystem drivetrain, double distance) {
        this(drivetrain, 0, distance);
    }

    public DriveForDistanceCommand(SwerveDriveSubsystem drivetrain, double distLeft, double distForward) {
        this.drivetrain = drivetrain;
        this.angle = Math.toDegrees(Math.atan2(distLeft, distForward));
        this.distance = Math.sqrt(distLeft*distLeft + distForward*distForward);

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        System.out.printf("Auto angle: %.3f\n", angle);

        for (int i = 0; i < 4; i++) {
            drivetrain.getSwerveModule(i).setTargetAngle(angle);
            drivetrain.getSwerveModule(i).zeroDistance();
            drivetrain.getSwerveModule(i).setTargetDistance(distance);
        }
    }

    @Override
    protected boolean isFinished() {
        return false; // TODO
    }
}