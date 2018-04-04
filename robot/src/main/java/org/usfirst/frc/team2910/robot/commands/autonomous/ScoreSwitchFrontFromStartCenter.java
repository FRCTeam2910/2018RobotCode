package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreSwitchFrontFromStartCenter extends CommandGroup {
    public ScoreSwitchFrontFromStartCenter(Robot robot, Side switchSide) {
        addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
        if (switchSide == Side.LEFT)
            addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.CENTER_START_TO_LEFT_SWITCH));
        else
            addSequential(new FollowPathCommand(robot.getDrivetrain(), AutonomousPaths.CENTER_START_TO_RIGHT_SWITCH));
        addSequential(new LaunchCubeCommand(robot.getGatherer(), 0.5));
    }
}
