package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.*;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.motion.Trajectory;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreSwitchBackFromScale extends CommandGroup {
	private static final double INTAKE_TIME = 2.5;

	public ScoreSwitchBackFromScale(Robot robot, Side switchSide, Side scaleSide) {
        if (switchSide == Side.LEFT && scaleSide == Side.RIGHT) {
            addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.RIGHT_SCALE_TO_LEFT_SCALE));
        } else if (switchSide == Side.RIGHT && scaleSide == Side.LEFT){
            addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.LEFT_SCALE_TO_RIGHT_SCALE));
        }

	    Path pathToCube;
        if (scaleSide == Side.LEFT) {
            pathToCube = AutonomousPaths.LEFT_SCALE_TO_LEFT_CUBE;
        } else {
            pathToCube = AutonomousPaths.RIGHT_SCALE_TO_RIGHT_CUBE;
        }

        Trajectory trajectoryToCube = new Trajectory(pathToCube, robot.getDrivetrain().getMaxAcceleration(), robot.getDrivetrain().getMaxVelocity());

        CommandGroup intakeGroup = new CommandGroup();
        intakeGroup.addSequential(new WaitCommand(Math.max(0, trajectoryToCube.getDuration() - INTAKE_TIME)));
        intakeGroup.addSequential(new IntakeCubeCommand(robot.getGatherer(), INTAKE_TIME + 0.5));

        addSequential(new VisionLineUpWithCubeCommand(robot));
        addParallel(intakeGroup);
        addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToCube));
        addSequential(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
        addSequential(new WaitForElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
        addSequential(new LaunchCubeCommand(robot.getGatherer(), 1, 0.8));
	}
}
