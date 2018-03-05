package org.usfirst.frc.team2910.robot.commands.autonomous.stage2;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.IntakeCubeCommand;
import org.usfirst.frc.team2910.robot.commands.LaunchCubeCommand;
import org.usfirst.frc.team2910.robot.commands.SetElevatorPositionCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.*;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

import static org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousConstants.*;

public class Stage2SameSideSwitchCommand extends CommandGroup {
    public Stage2SameSideSwitchCommand(Robot robot, Side side) {
        SwerveDriveSubsystem drivetrain = robot.getDrivetrain();

        int cubeToGrab = GrabCubeFromPlatformZoneCommand.getFirstAvailableFromSide(side);
        GrabCubeFromPlatformZoneCommand.availableCubes[cubeToGrab] = false;

        int cubeRelativeToStart = GrabCubeFromPlatformZoneCommand.getRelativeCubeForSide(cubeToGrab, side);

        double widthLengthDiff = (drivetrain.getWidth() - drivetrain.getLength()) / 2;
        double distToSwitch = AutonomousConstants.SWITCH_SCORE_TO_SWITCH_WALL - widthLengthDiff + drivetrain.getWidth() / 2;

        double switchEdgeToCube = cubeRelativeToStart * (GAP_BETWEEN_PLATFORM_CUBES + CUBE_WIDTH) + CUBE_WIDTH / 2 + 1.5;

//        double angleToCube = Math.toDegrees(Math.atan2(
//                (side == Side.LEFT ? -1 : 1) * ((distToSwitch + switchEdgeToCube) - 11),
//                ((WALL_TO_SWITCH + SWITCH_DEPTH + 11) - WALL_TO_PLATFORM_ZONE)));
        double angleToCube = (side == Side.LEFT ? 205 : 155);
        System.out.println("Cube angle: " + angleToCube);
        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(), angleToCube));

        addParallel(new IntakeCubeCommand(robot.getGatherer(), -1, (side == Side.LEFT ? -1 : 1) * 0.25, 3));
        /* HACK: Added 5 to where we want to be so we don't run into the switch */
        addSequential(new DriveForTimeCommand(drivetrain, 0.95, 0, (side == Side.LEFT ? 1 : -1) * 0.5));
        addSequential(new DriveForDistanceCommand(drivetrain,
                (side == Side.LEFT ? 1 : -1) * 3,
                (WALL_TO_SWITCH + SWITCH_DEPTH + 14) - WALL_TO_PLATFORM_ZONE));
        addSequential(new WaitCommand(0.5));
        addSequential(new SetElevatorPositionCommand(robot.getElevator(), ElevatorSubsystem.SCORE_SWITCH_POISITON));
//        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(),(angleToCube > 0 ? 15 : -15) + angleToCube));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, -10), 0.5);
        addSequential(new LaunchCubeCommand(robot.getGatherer(), 1));
        addSequential(new DriveForDistanceCommand(robot.getDrivetrain(), 0, 10), 0.5);
        //        addSequential(new SetDrivetrainAngleCommand(robot.getDrivetrain(),(angleToCube > 0 ? -15 : 15) + angleToCube));
        addParallel(new SetElevatorPositionCommand(robot.getElevator(), 0));
        addSequential(new DriveForDistanceCommand(drivetrain,
                (side == Side.LEFT ? 1 : -1) * ((distToSwitch + switchEdgeToCube) - 7),
                -((WALL_TO_SWITCH + SWITCH_DEPTH + 14) - WALL_TO_PLATFORM_ZONE)));
    }
}
