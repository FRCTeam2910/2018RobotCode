package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.IntakeCubeCommand;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.motion.Trajectory;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreSwitchFrontFromSwitchFront extends CommandGroup {
    private static final double INTAKE_TIME = 2.5;
    private static final double LAUNCH_TIME = 0.5;
    private static final double ELEVATOR_WAIT = 1;

    public ScoreSwitchFrontFromSwitchFront(Robot robot, Side switchSide) {
        addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.GROUND_POSITION));

        Path pathToCube;
        Path pathToSwitch;
        if (switchSide == Side.LEFT) {
            pathToCube = AutonomousPaths.LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_1;
            pathToSwitch = AutonomousPaths.LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_2;
        } else {
            pathToCube = AutonomousPaths.RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_1;
            pathToSwitch = AutonomousPaths.RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_2;
        }

        Trajectory trajectoryToCube = new Trajectory(pathToCube, robot.getDrivetrain().getMaxAcceleration(), robot.getDrivetrain().getMaxVelocity());
        Trajectory trajectoryToSwitch = new Trajectory(pathToSwitch, robot.getDrivetrain().getMaxAcceleration(), robot.getDrivetrain().getMaxVelocity());

        CommandGroup intakeGroup = new CommandGroup();
        intakeGroup.addSequential(new WaitCommand(Math.max(0, trajectoryToCube.getDuration() - INTAKE_TIME / 2)));
        intakeGroup.addSequential(new IntakeCubeCommand(robot.getGatherer(), INTAKE_TIME));

        CommandGroup scoreGroup = new CommandGroup();
        scoreGroup.addSequential(new WaitCommand(Math.max(0, trajectoryToSwitch.getDuration() - LAUNCH_TIME)));
        scoreGroup.addSequential(new LaunchCubeCommand(robot.getGatherer(),  LAUNCH_TIME));

        CommandGroup elevatorGroup = new CommandGroup();
        elevatorGroup.addSequential(new WaitCommand(ELEVATOR_WAIT));
        elevatorGroup.addSequential(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));

        addParallel(intakeGroup);
        addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToCube));
        addParallel(elevatorGroup);
        addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToSwitch));
        addParallel(scoreGroup);
        addSequential(new SetDrivetrainAngleIfNotAngledCommand(robot.getDrivetrain(), 0));
        addSequential(new WaitForElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
        addSequential(new LaunchCubeCommand(robot.getGatherer(), 0.5));
    }
}