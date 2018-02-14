package org.usfirst.frc.team2910.robot.commands.autonomous.stage1;

import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.SWITCH_LENGTH;
import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL;
import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.SWITCH_SIDE_TO_PLATFORM_ZONE;
import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.WALL_TO_PLATFORM_ZONE;
import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.WALL_TO_SWITCH_SIDE_DISTANCE;

import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.SetFieldOrientedAngleCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.DriveForDistanceCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.SetDrivetrainAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Stage1SwitchCommand extends CommandGroup {

    private final Robot robot;

    public Stage1SwitchCommand(Robot robot, StartingPosition startingPosition, char switchPosition) {
        this.robot = robot;
        
        // TODO: Move elevator to switch position

        System.out.printf("Gyro angle: % .3f\n", robot.getDrivetrain().getRawGyroAngle());
        addSequential(new SetFieldOrientedAngleCommand(robot.getDrivetrain(), robot.getDrivetrain().getRawGyroAngle() + 90));
        
        // Move to switch scoring position
        switch (startingPosition) {
            case CENTER:
                // TODO: Center Stage 1 Switch
                break;
            case LEFT:
                if (switchPosition == 'L') {
                    driveSideToNearSwitch(startingPosition);
                } else {
                    driveSideToFarSwitch(startingPosition);
                }
                break;
            case RIGHT:
                if (switchPosition == 'L') {
                    driveSideToFarSwitch(startingPosition);
                } else {
                    driveSideToNearSwitch(startingPosition);
                }
                break;
        }

        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startingPosition == StartingPosition.LEFT ? -1 : 1) * SWITCH_SCORE_TO_SWITCH_WALL,
                0));
        
        // TODO: Score cube

        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startingPosition == StartingPosition.LEFT ? 1 : -1) * SWITCH_SCORE_TO_SWITCH_WALL,
                0));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                WALL_TO_PLATFORM_ZONE - WALL_TO_SWITCH_SIDE_DISTANCE));
    }

    private void driveSideToFarSwitch(StartingPosition startPos) {
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                WALL_TO_PLATFORM_ZONE));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? -1 : 1) * (SWITCH_LENGTH + 2*SWITCH_SCORE_TO_SWITCH_WALL),
                0));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                -SWITCH_SIDE_TO_PLATFORM_ZONE));
        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? 270 : 90)));
    }

    private void driveSideToNearSwitch(StartingPosition startPos) {
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                WALL_TO_SWITCH_SIDE_DISTANCE));
    }
}
