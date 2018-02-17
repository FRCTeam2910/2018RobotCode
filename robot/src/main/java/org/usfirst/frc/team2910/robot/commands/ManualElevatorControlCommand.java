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
        double input = Robot.getOI().getSecondaryController().getRightYValue();

        if (Math.abs(input) > 0.05) {
            input *= Math.abs(input);
            elevator.setElevatorSpeed(input);
            shouldSetPosition = true;
        } else if (shouldSetPosition) {
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
