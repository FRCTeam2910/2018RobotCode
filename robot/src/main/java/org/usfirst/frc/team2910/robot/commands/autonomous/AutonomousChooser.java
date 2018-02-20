package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.autonomous.stage1.*;
import org.usfirst.frc.team2910.robot.commands.autonomous.stage2.Stage2SameSideSwitchCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.stage2.Stage2ScaleCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.stage2.Stage2SwitchCommand;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

import java.util.ArrayList;
import java.util.List;

public class AutonomousChooser {
    public static final int CHOICE_COUNT = 4;
    private final SendableChooser<StartingPosition> startPosChooser = new SendableChooser<>();
    private List<SendableChooser<AutonomousStageChoice>> priorityChoices = new ArrayList<>();

    public AutonomousChooser() {
        startPosChooser.addDefault("Left", StartingPosition.LEFT);
        startPosChooser.addObject("Center", StartingPosition.CENTER);
        startPosChooser.addObject("Right", StartingPosition.RIGHT);

        SmartDashboard.putData("Start Position", startPosChooser);

        for (int i = 0; i < CHOICE_COUNT; i++) {
            SendableChooser<AutonomousStageChoice> chooser = new SendableChooser<>();

            chooser.addDefault("None", AutonomousStageChoice.NONE);
            chooser.addObject("Auto line", AutonomousStageChoice.AUTOLINE);
            chooser.addObject("Same Side Scale", AutonomousStageChoice.SAME_SIDE_SCALE);
            chooser.addObject("Same Side Switch", AutonomousStageChoice.SAME_SIDE_SWITCH);
            chooser.addObject("Opposite Side Scale", AutonomousStageChoice.OPPOSITE_SIDE_SCALE);
            chooser.addObject("Opposite Side Switch", AutonomousStageChoice.OPPOSITE_SIDE_SWITCH);

            priorityChoices.add(chooser);

            SmartDashboard.putData("Auto Preference " + i, chooser);
        }
    }

    private boolean isChoiceGood(AutonomousStageChoice choice, String fieldConf, StartingPosition startPos) {
        Side switchSide = Side.fromChar(fieldConf.charAt(0)),
                scaleSide = Side.fromChar(fieldConf.charAt(1));
        if (startPos == StartingPosition.CENTER) return true;

        switch (choice) {
            case NONE:
                return true;
            case AUTOLINE:
                return true;
            case SAME_SIDE_SCALE:
                return (startPos == StartingPosition.LEFT && scaleSide == Side.LEFT) ||
                        (startPos == StartingPosition.RIGHT && scaleSide == Side.RIGHT);
            case SAME_SIDE_SWITCH:
                return (startPos == StartingPosition.LEFT && switchSide == Side.LEFT) ||
                        (startPos == StartingPosition.RIGHT && switchSide == Side.RIGHT);
            case OPPOSITE_SIDE_SCALE:
                return (startPos == StartingPosition.RIGHT && scaleSide == Side.LEFT) ||
                        (startPos == StartingPosition.LEFT && scaleSide == Side.RIGHT);
            case OPPOSITE_SIDE_SWITCH:
                return (startPos == StartingPosition.RIGHT && switchSide == Side.LEFT) ||
                        (startPos == StartingPosition.LEFT && switchSide == Side.RIGHT);
            default:
                return false;
        }
    }

    public Command getCommand(Robot robot) {
        GrabCubeFromPlatformZoneCommand.resetAvailableCubes();

        String fieldConfiguration = DriverStation.getInstance().getGameSpecificMessage();
        StartingPosition startPos = startPosChooser.getSelected();

        CommandGroup autoGroup = new CommandGroup();

        robot.getElevator().setEncoderPosition(ElevatorSubsystem.STARTING_ENCODER_TICKS);
        robot.getElevator().setElevatorPosition(robot.getElevator().getCurrentHeight());

        Side lastSide = Side.LEFT;
        int stageNumber = 1;
        for (int i = 0; i < priorityChoices.size(); i++) {
            AutonomousStageChoice choice = priorityChoices.get(i).getSelected();
            if (isChoiceGood(choice, fieldConfiguration, startPos)) {
                System.out.printf("%d: %s\n", stageNumber, choice);
                if (stageNumber == 1) {
                    switch (choice) {
                        case NONE:
                            return autoGroup;
                        case AUTOLINE:
                            autoGroup.addSequential(new AutoLineCommand(robot, startPos));
                            return autoGroup;
                        case SAME_SIDE_SCALE:
                        case OPPOSITE_SIDE_SCALE:
                            autoGroup.addSequential(new Stage1ScaleCommand(robot,
                                    startPos, fieldConfiguration.charAt(1)));
                            lastSide = Side.fromChar(fieldConfiguration.charAt(1));
                            break;
                        case SAME_SIDE_SWITCH:
                        case OPPOSITE_SIDE_SWITCH:
                            if (startPos == StartingPosition.CENTER) {
                                autoGroup.addSequential(new Stage1CenterSwitchCommand(robot,
                                        Side.fromChar(fieldConfiguration.charAt(0))));
                                return autoGroup;
                            }
                            autoGroup.addSequential(new Stage1SwitchCommand(robot,
                                    startPos, fieldConfiguration.charAt(0)));
                            lastSide = Side.fromChar(fieldConfiguration.charAt(0));
                            break;
                    }
                } else {
                    // Grab Cube

                    if (priorityChoices.get(i).getSelected() == AutonomousStageChoice.SAME_SIDE_SWITCH) {
                        autoGroup.addSequential(new Stage2SameSideSwitchCommand(robot, lastSide));
                    }

                    Side targetSide;
                    Side startSide = (startPos == StartingPosition.LEFT ? Side.LEFT : Side.RIGHT);
                    switch (priorityChoices.get(i).getSelected()) {
                        case OPPOSITE_SIDE_SCALE:
                        case OPPOSITE_SIDE_SWITCH:
                            targetSide = startSide.opposite();
                            break;
                        case SAME_SIDE_SCALE:
                        case SAME_SIDE_SWITCH:
                            targetSide = startSide;
                            break;
                        default:
                            continue;
                    }

                    autoGroup.addSequential(new GrabCubeFromPlatformZoneCommand(robot, lastSide, targetSide));

                    switch (priorityChoices.get(i).getSelected()) {
                        case SAME_SIDE_SCALE:
                        case OPPOSITE_SIDE_SCALE:
                            autoGroup.addSequential(new Stage2ScaleCommand(robot, targetSide));
                            break;
                        case SAME_SIDE_SWITCH:
                        case OPPOSITE_SIDE_SWITCH:
                            autoGroup.addSequential(new Stage2SwitchCommand(robot, targetSide));
                            break;
                    }

                    lastSide = targetSide;
                }

                stageNumber++;
            }
        }

        return autoGroup;
    }
}
