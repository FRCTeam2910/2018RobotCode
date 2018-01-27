package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.CUBE_WIDTH;
import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.GAP_BETWEEN_PLATFORM_CUBES;

public class GrabCubeFromPlatformZoneCommand extends CommandGroup {
    private static boolean[] availableCubes = {true, true, true, true, true, true};

    public GrabCubeFromPlatformZoneCommand(Robot robot, Side startSide, Side endSide) {
        SwerveDriveSubsystem drivetrain = robot.getDrivetrain();

        int cubeToGrab = getFirstAvailableFromSide(startSide);
        int cubeRelativeToStart = getRelativeCubeForSide(cubeToGrab, startSide);

        // Face towards driverstation
        addSequential(new SetDrivetrainAngleCommand(drivetrain, 180));

        double widthLengthDiff = (drivetrain.getWidth() - drivetrain.getLength()) / 2;
        double distToSwitch = AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL - widthLengthDiff + drivetrain.getWidth() / 2;

        double switchEdgeToCube = cubeRelativeToStart * (GAP_BETWEEN_PLATFORM_CUBES + CUBE_WIDTH) + CUBE_WIDTH / 2;

        // Drive to cube
        addSequential(new DriveForDistanceCommand(drivetrain,
                (startSide == Side.LEFT ? -1 : 1) * (distToSwitch + switchEdgeToCube),
                0));

        // TODO: Grab cube

        int cubeRelativeToEnd = getRelativeCubeForSide(cubeToGrab, endSide);
        double cubeToSwitchEdge = cubeRelativeToEnd * (GAP_BETWEEN_PLATFORM_CUBES + CUBE_WIDTH) + CUBE_WIDTH / 2;

        // Drive to end side
        addSequential(new DriveForDistanceCommand(drivetrain,
                (endSide == Side.LEFT ? 1 : -1) * (distToSwitch + cubeToSwitchEdge)));

        // Face towards center
//        addSequential(new SetDrivetrainAngleCommand(drivetrain));

        // TODO: End up on end side
    }

    private static int getFirstAvailableFromSide(Side side) {
        switch (side) {
            case LEFT:
                for (int i = 0; i < availableCubes.length; i++)
                    if (availableCubes[i]) return i;
                break;
            case RIGHT:
                for (int i = availableCubes.length - 1; i <= 0; i--)
                    if (availableCubes[i]) return i;
                break;
        }

        return 0;
    }

    private static int getRelativeCubeForSide(int absCubeNumber, Side side) {
        switch (side) {
            case LEFT:
                return absCubeNumber;
            case RIGHT:
                return 5 - absCubeNumber;
            default:
                return 0;
        }
    }
}
