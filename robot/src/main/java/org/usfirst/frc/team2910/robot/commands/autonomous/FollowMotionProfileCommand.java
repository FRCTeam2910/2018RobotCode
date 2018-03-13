package org.usfirst.frc.team2910.robot.commands.autonomous;

import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frcteam2910.motion_profiling.MotionProfile;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class FollowMotionProfileCommand extends Command {

    private final SwerveDriveSubsystem drivetrain;
    private final MotionProfile[] profiles;

    private int currentPoint = 0;

    public FollowMotionProfileCommand(SwerveDriveSubsystem drivetrain, MotionProfile[] profiles) {
        this.drivetrain = drivetrain;
        this.profiles = profiles;

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        currentPoint = 0;
        for (int i = 0; i < 4; i++) {
            drivetrain.getSwerveModule(i).clearMotionProfilingBuffer();
            drivetrain.getSwerveModule(i).zeroDistance();
            for (int j = 0; j < profiles[i].getLength(); j++) {
                TrajectoryPoint drivePoint = new TrajectoryPoint();
                TrajectoryPoint anglePoint = new TrajectoryPoint();

                drivePoint.timeDur = anglePoint.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_40ms;
                drivePoint.position = anglePoint.position = profiles[i].getSegment(j).position * 100;
                drivePoint.velocity = anglePoint.velocity = profiles[i].getSegment(j).velocity * 100;
                drivePoint.headingDeg = anglePoint.headingDeg = 0;

                if (j == 0) {
                    drivePoint.zeroPos = true;
                }
                if (j == profiles[i].getLength() - 1)
                    drivePoint.isLastPoint = anglePoint.isLastPoint = true;
                else
                    drivePoint.isLastPoint = anglePoint.isLastPoint = false;

                drivePoint.profileSlotSelect0 = 0;
                drivePoint.profileSlotSelect1 = 0;

                drivetrain.getSwerveModule(i).pushMotionPoint(anglePoint, drivePoint);
            }

//            drivetrain.getSwerveModule(i).processMotionBuffer();

            drivetrain.getSwerveModule(i).enableMotionProfiling();
        }
    }
    @Override
    protected void execute() {


        for (int i = 0; i < 4; i++) {
            drivetrain.getSwerveModule(i).enableMotionProfiling();
            drivetrain.getSwerveModule(i).processMotionBuffer();
        }
    }

    @Override
    protected boolean isFinished() {
        boolean isDone = false;
        for (int i = 0; i < 4; i++)
            isDone &= drivetrain.getSwerveModule(i).isMotionProfileComplete();
        return isDone;
    }
}
