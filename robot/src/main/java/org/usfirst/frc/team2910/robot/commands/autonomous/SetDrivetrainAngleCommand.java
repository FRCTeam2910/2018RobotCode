package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class SetDrivetrainAngleCommand extends Command {

    private final SwerveDriveSubsystem drivetrain;
    private final double targetAngle;

    public SetDrivetrainAngleCommand(SwerveDriveSubsystem drivetrain, double targetAngle) {
        this.drivetrain = drivetrain;
        this.targetAngle = targetAngle;

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        double angleDiff = Math.toRadians(targetAngle - drivetrain.getGyroAngle());
        double arcLength = SwerveDriveSubsystem.TURNING_RADIUS * angleDiff;
        SmartDashboard.putNumber("Arc Length", arcLength);


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
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
