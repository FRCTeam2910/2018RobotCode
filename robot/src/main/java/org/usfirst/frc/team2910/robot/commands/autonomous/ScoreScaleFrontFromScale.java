package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.IntakeCubeCommand;
import org.usfirst.frc.team2910.robot.commands.IntakeCubeNoRequireCommand;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.motion.Trajectory;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreScaleFrontFromScale extends CommandGroup {
    private static final double LAUNCH_TIME = 1;

    public ScoreScaleFrontFromScale(Robot robot, Side scaleSide) {
        Path pathToScaleFront;
        if (scaleSide == Side.LEFT)
            pathToScaleFront = AutonomousPaths.LEFT_SCALE_TO_LEFT_SCALE_FRONT;
        else
            pathToScaleFront = AutonomousPaths.RIGHT_SCALE_TO_RIGHT_SCALE_FRONT;

        Trajectory trajectoryToScale = new Trajectory(pathToScaleFront, robot.getDrivetrain().getMaxAcceleration(), robot.getDrivetrain().getMaxVelocity());

        addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION));
        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(),
                (scaleSide == Side.LEFT ? 330 : 30)));
        addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToScaleFront));
        addSequential(new LaunchCubeCommand(robot.getGatherer(), LAUNCH_TIME, 0.9));
    }
}
