package org.usfirst.frc.team2910.robot.commands.autonomous.stage1;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants;
import org.usfirst.frc.team2910.robot.commands.autonomous.DriveForDistanceCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.SetDrivetrainAngleCommand;

public class Stage1SwitchAroundCommand extends CommandGroup {

    private final Robot robot;

    public Stage1SwitchAroundCommand(Robot robot, StartingPosition startingPosition, char switchPosition) {
        this.robot = robot;

        // TODO: Move elevator to switch position

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

        // TODO: Score cube
    }

    private void driveSideToFarSwitch(StartingPosition startPos) {
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                AutonomousConstants.WALL_TO_PLATFORM_ZONE));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? -1 : 1) * (AutonomousConstants.SWITCH_LENGTH + AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL * 2),
                0));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                -AutonomousConstants.SWITCH_SIDE_TO_PLATFORM_ZONE));
        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? 270 : 90)));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? -1 : 1) * AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL,
                0));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? 1 : -1) * AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL,
                0));
    }

    private void driveSideToNearSwitch(StartingPosition startPos) {
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                AutonomousConstants.WALL_TO_SWITCH_SIDE_DISTANCE));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? 1 : -1) * AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL,
                0));
    }
}
