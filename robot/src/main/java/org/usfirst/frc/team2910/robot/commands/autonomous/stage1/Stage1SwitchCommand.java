package org.usfirst.frc.team2910.robot.commands.autonomous.stage1;

import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.SetFieldOrientedAngleCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.DriveForDistanceCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.SetDrivetrainAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.*;

public class Stage1SwitchCommand extends CommandGroup {

    private final Robot robot;

    public Stage1SwitchCommand(Robot robot, StartingPosition startPos, char switchPosition) {
        this.robot = robot;

        // TODO: Move elevator to switch position

        System.out.printf("Gyro angle: % .3f\n", robot.getDrivetrain().getRawGyroAngle());
        if (startPos == StartingPosition.LEFT)
            addSequential(new SetFieldOrientedAngleCommand(robot.getDrivetrain(), robot.getDrivetrain().getRawGyroAngle() + 90));
        else if (startPos == StartingPosition.RIGHT)
            addSequential(new SetFieldOrientedAngleCommand(robot.getDrivetrain(), robot.getDrivetrain().getRawGyroAngle() - 90));

        if (startPos == StartingPosition.CENTER) {
            addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), SWITCH_LENGTH / 2 + SWITCH_SCORE_TO_SWITCH_WALL + robot.getDrivetrain().getLength() / 2, 0));

            startPos = StartingPosition.RIGHT;
        }

        // Move to switch scoring position
        switch (startPos) {
            case LEFT:
                if (switchPosition == 'L') {
                    driveSideToNearSwitch(startPos);
                } else {
                    driveSideToFarSwitch(startPos);
                }
                break;
            case RIGHT:
                if (switchPosition == 'L') {
                    driveSideToFarSwitch(startPos);
                } else {
                    driveSideToNearSwitch(startPos);
                }
                break;
        }


        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                WALL_TO_PLATFORM_ZONE - (WALL_TO_SWITCH + SWITCH_DEPTH / 2)));
    }

    private void driveSideToFarSwitch(StartingPosition startPos) {
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                WALL_TO_PLATFORM_ZONE - robot.getDrivetrain().getWidth() / 2));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? -1 : 1) * (SWITCH_LENGTH + 2 * SWITCH_SCORE_TO_SWITCH_WALL + robot.getDrivetrain().getLength()),
                0));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                (WALL_TO_SWITCH + SWITCH_DEPTH / 2) - WALL_TO_PLATFORM_ZONE));
        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? 90 : 270)));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? 1 : -1) * SWITCH_SCORE_TO_SWITCH_WALL,
                0));

        // TODO: Score cube

        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? -1 : 1) * SWITCH_SCORE_TO_SWITCH_WALL,
                0));
    }

    private void driveSideToNearSwitch(StartingPosition startPos) {
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                WALL_TO_SWITCH + SWITCH_DEPTH / 2 - robot.getDrivetrain().getWidth() / 2));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? -1 : 1) * SWITCH_SCORE_TO_SWITCH_WALL,
                0));

        // TODO: Score cube

        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? 1 : -1) * SWITCH_SCORE_TO_SWITCH_WALL,
                0));
    }
}
