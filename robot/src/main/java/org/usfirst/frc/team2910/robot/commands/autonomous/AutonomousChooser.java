package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.autonomous.stage1.Stage1ScaleCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.stage1.Stage1SwitchCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.stage1.StartingPosition;
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

        SmartDashboard.putData("StartingPosition", startPosChooser);

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
        String fieldConfiguration = DriverStation.getInstance().getGameSpecificMessage();
        StartingPosition startPos = startPosChooser.getSelected();

        CommandGroup autoGroup = new CommandGroup();

        int stageNumber = 1;
        for (int i = 0; i < priorityChoices.size(); i++) {
            AutonomousStageChoice choice = priorityChoices.get(i).getSelected();
            if (isChoiceGood(choice, fieldConfiguration, startPos)) {
                if (stageNumber == 1) {
                	System.out.println(priorityChoices.get(i).getSelected());
                    switch (priorityChoices.get(i).getSelected()) {
                        case NONE:
                            return autoGroup;
                        case AUTOLINE:
                            // TODO: Auto line
                            return autoGroup;
                        case SAME_SIDE_SCALE:
                        case OPPOSITE_SIDE_SCALE:
                            autoGroup.addSequential(new Stage1ScaleCommand(robot,
                                    startPos, fieldConfiguration.charAt(1)));
                            break;
                        case SAME_SIDE_SWITCH:
                        case OPPOSITE_SIDE_SWITCH:
                            autoGroup.addSequential(new Stage1SwitchCommand(robot,
                                    startPos, fieldConfiguration.charAt(0)));
                            break;
                    }
                } else {
                    // TODO: Second stage
                }

                stageNumber++;
            }
        }

        return autoGroup;
    }
}
