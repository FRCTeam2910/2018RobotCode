package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.motion.AutonomousPaths;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

public class ScoreSwitchSideFromStartForward extends CommandGroup {
    public ScoreSwitchSideFromStartForward(Robot robot, Side startSide, Side switchSide) {
        Path pathToSwitch;
        Path pathToSwitchSide = switchSide == Side.LEFT ? AutonomousPaths.LEFT_SWITCH_TO_LEFT_SWITCH_SIDE
                : AutonomousPaths.RIGHT_SWITCH_TO_RIGHT_SWITCH_SIDE;
        double switchAngle = switchSide == Side.LEFT ? 270 : 90;

        if (startSide == Side.LEFT) {
            if (switchSide == Side.LEFT) {
                pathToSwitch = AutonomousPaths.LEFT_START_TO_LEFT_SWITCH;
            } else {
                pathToSwitch = AutonomousPaths.LEFT_START_TO_RIGHT_SWITCH;
            }
        } else {
            if (switchSide == Side.LEFT) {
                pathToSwitch = AutonomousPaths.RIGHT_START_TO_LEFT_SWITCH;
            } else {
                pathToSwitch = AutonomousPaths.RIGHT_START_TO_RIGHT_SWITCH;
            }
        }

        addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToSwitch));
        addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(), switchAngle));
        addSequential(new FollowPathCommand(robot.getDrivetrain(), pathToSwitchSide));
        addSequential(new LaunchCubeCommand(robot.getGatherer(), 0.5));
    }
}
