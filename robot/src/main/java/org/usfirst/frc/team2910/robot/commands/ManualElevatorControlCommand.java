package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class ManualElevatorControlCommand extends Command {

    private final ElevatorSubsystem elevator;

    private boolean shouldSetPosition = false;

    public ManualElevatorControlCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;

        requires(elevator);
    }

    @Override
    protected void execute() {
        double primaryInput = Robot.getOI().getPrimaryController().getRightTriggerValue() -
                Robot.getOI().getPrimaryController().getLeftTriggerValue();
        System.out.println(primaryInput);
        double secondaryInput = Robot.getOI().getSecondaryController().getRightYValue();

        if (elevator.getCurrentHeight() > ElevatorSubsystem.TOP_POSITION) {
            primaryInput = Math.min(0, primaryInput);
            secondaryInput = Math.min(0, secondaryInput);
        }

        if (Math.abs(primaryInput) > 0.05) {
            primaryInput *= Math.abs(primaryInput);
            elevator.setElevatorSpeed(primaryInput);
            shouldSetPosition = true;
        } else if (Math.abs(secondaryInput) > 0.05) {
            secondaryInput *= Math.abs(secondaryInput);
            elevator.setElevatorSpeed(secondaryInput);
            shouldSetPosition = true;
        } else if (shouldSetPosition && elevator.getCurrentMode() == ElevatorSubsystem.Mode.REGULAR) {
            elevator.getMotors()[0].setIntegralAccumulator(0, 0, 0);
            elevator.setElevatorPosition(elevator.getCurrentHeight());
            shouldSetPosition = false;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
