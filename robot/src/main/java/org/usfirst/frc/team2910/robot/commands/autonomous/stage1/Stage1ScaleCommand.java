package org.usfirst.frc.team2910.robot.commands.autonomous.stage1;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants;
import org.usfirst.frc.team2910.robot.commands.autonomous.DriveForDistanceCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.SetDrivetrainAngleCommand;

import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.*;

public class Stage1ScaleCommand extends CommandGroup {

    private final Robot robot;

    public Stage1ScaleCommand(Robot robot, StartingPosition startPos, char scalePos) {
        this.robot = robot;

        // TODO: Move elevator to switch position

        // Move to switch scoring position
        switch (startPos) {
            case CENTER:
                // TODO: Center Stage 1 Scale
                break;
            case LEFT:
                if (scalePos == 'L') {
                    driveSideToNearScale(startPos);
                } else {
                    driveSideToFarScale(startPos);
                }
                break;
            case RIGHT:
                if (scalePos == 'L') {
                    driveSideToFarScale(startPos);
                } else {
                    driveSideToNearScale(startPos);
                }
                break;
        }

        // TODO: Score cube
    }

    private void driveSideToFarScale(StartingPosition startPos) {
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                WALL_TO_PLATFORM_ZONE));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? 1 : -1) * (SWITCH_LENGTH + 2*SWITCH_SCORE_TO_SWITCH_WALL + START_POS_TO_SCALE_SCORE),
                0));
        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? 270 : 90)));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                0,
                WALL_TO_SCALE - WALL_TO_PLATFORM_ZONE));
    }

    private void driveSideToNearScale(StartingPosition startPos) {
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(),
                (startPos == StartingPosition.LEFT ? -1 : 1) * START_POS_TO_SCALE_SCORE,
                AutonomousConstants.WALL_TO_SCALE));
    }
}
