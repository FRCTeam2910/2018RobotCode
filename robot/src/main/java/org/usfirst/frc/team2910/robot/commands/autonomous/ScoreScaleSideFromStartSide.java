package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.CalibrateElevatorEncoderCommand;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.motion.Trajectory;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

import static org.usfirst.frc.team2910.robot.motion.AutonomousPaths.*;

public class ScoreScaleSideFromStartSide extends CommandGroup {
    public static final double ELEVATOR_WAIT = 3;
    
    public ScoreScaleSideFromStartSide(Robot robot, Side startSide, Side scaleSide, double oppositeWaitTime) {
        double angle = scaleSide == Side.LEFT ? 270 : 90;
        
        Path step1, step2, step3, step4;
        
        if (startSide == Side.LEFT) {
            if (scaleSide == Side.LEFT) {
                step1 = LEFT_START_TO_LEFT_SCALE_SIDE_STEP_1;
                step2 = LEFT_START_TO_LEFT_SCALE_SIDE_STEP_2;
                step3 = LEFT_START_TO_LEFT_SCALE_SIDE_STEP_3;
                step4 = null;
            } else {
                step1 = LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_1;
                step2 = LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_2;
                step3 = LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_3;
                step4 = LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_4;
            }
        } else {
            if (scaleSide == Side.LEFT) {
                step1 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_1;
                step2 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_2;
                step3 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_3;
                step4 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_4;
            } else {
                step1 = RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_1;
                step2 = RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_2;
                step3 = RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_3;
                step4 = null;
            }
        }
        
        if (startSide == scaleSide) {
            scoreSame(robot, step1, step2, step3, angle);
        } else {
            scoreOpposite(robot, step1, step2, step3, step4, angle, oppositeWaitTime);
        }
    }

    private void scoreSame(Robot robot, Path step1, Path step2, Path step3, double angle) {

        Trajectory step2Trajectory = new Trajectory(step2, robot.getDrivetrain().getMaxAcceleration(), robot.getDrivetrain().getMaxVelocity());

        CommandGroup elevatorGroup = new CommandGroup();
        elevatorGroup.addSequential(new WaitCommand(Math.max(0, step2Trajectory.getDuration() - ELEVATOR_WAIT)));
        elevatorGroup.addSequential(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION));

        addParallel(elevatorGroup);
        addSequential(new FollowPathCommand(robot.getDrivetrain(), step1));
        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(), angle));
        addSequential(new FollowPathCommand(robot.getDrivetrain(), step2));
        addSequential(new LaunchCubeCommand(robot.getGatherer(), 0.5));
        addSequential(new FollowPathCommand(robot.getDrivetrain(), step3));
        addSequential(new SetElevatorPositionCommand(robot.getElevator(), 15));
        addSequential(new WaitForElevatorPositionCommand(robot.getElevator(), 15));
        addSequential(new CalibrateElevatorEncoderCommand(robot.getElevator()));
    }
    
    private void scoreOpposite(Robot robot, Path step1, Path step2, Path step3, Path step4, double angle, double waitTime) {
        
        Trajectory step2Trajectory = new Trajectory(step2, robot.getDrivetrain().getMaxAcceleration(), robot.getDrivetrain().getMaxVelocity());
        
        CommandGroup elevatorGroup = new CommandGroup();
        elevatorGroup.addSequential(new WaitCommand(Math.max(0, step2Trajectory.getDuration() - ELEVATOR_WAIT)));
        elevatorGroup.addSequential(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.TOP_POSITION));
        
        addSequential(new FollowPathCommand(robot.getDrivetrain(), step1));
        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(), angle));
        addSequential(new WaitForTimerCommand(robot.getAutoTimer(), waitTime));
        addParallel(elevatorGroup);
        addSequential(new FollowPathCommand(robot.getDrivetrain(), step2));
        addSequential(new FollowPathCommand(robot.getDrivetrain(), step3));
        addSequential(new LaunchCubeCommand(robot.getGatherer(), 0.5));
        addSequential(new FollowPathCommand(robot.getDrivetrain(), step4));
        addSequential(new SetElevatorPositionCommand(robot.getElevator(), 15));
        addSequential(new WaitForElevatorPositionCommand(robot.getElevator(), 15));
        addSequential(new CalibrateElevatorEncoderCommand(robot.getElevator()));
    }
}
