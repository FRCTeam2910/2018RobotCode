package org.usfirst.frc.team2910.robot.commands.autonomous.stage1;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.commands.SetFieldOrientedAngleCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.DriveForDistanceCommand;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.WALL_TO_SWITCH;

public class Stage1CenterSwitchCommand extends CommandGroup {
    public Stage1CenterSwitchCommand(Robot robot, Side switchSide) {
        System.out.println("CENTER SWITCH");
        addSequential(new SetFieldOrientedAngleCommand(robot.getDrivetrain(), robot.getDrivetrain().getRawGyroAngle()));

        addParallel(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (switchSide == Side.LEFT ? 1.2 : -1) * 50,
                WALL_TO_SWITCH - robot.getDrivetrain().getWidth() + 3));
        addSequential(new LaunchCubeCommand(robot.getGatherer(), 1));
    }
}
