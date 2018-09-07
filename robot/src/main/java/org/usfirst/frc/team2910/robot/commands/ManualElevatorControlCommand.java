package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class ManualElevatorControlCommand extends Command {
    public static final double SNAP_TO_BOTTOM_DISTANCE = 10.0;
    private static final double ALLOWABLE_PID_VELOCITY = 2.5;

    private final ElevatorSubsystem elevator;

    private boolean shouldSetPosition = false;
    private boolean hasSecondPidSet = false;

    public ManualElevatorControlCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;

        requires(elevator);
    }

    @Override
    protected void execute() {
        double primaryInput = Robot.getOI().getPrimaryController().getLeftTriggerValue() -
                Robot.getOI().getPrimaryController().getRightTriggerValue();
        double secondaryInput = Robot.getOI().getSecondaryController().getRightYValue();

        if (elevator.getCurrentHeight() > ElevatorSubsystem.TOP_POSITION) {
            primaryInput = Math.min(0, primaryInput);
            secondaryInput = Math.min(0, secondaryInput);
        }

        double input = 0.0;
        if (Math.abs(primaryInput) > 0.05) {
            input = Math.copySign(primaryInput * primaryInput, primaryInput);
        } else if (Math.abs(secondaryInput) > 0.05) {
            input = Math.copySign(secondaryInput * secondaryInput, secondaryInput);
        }

        if (Math.abs(input) > 0.05) {
            if (input < 0 && elevator.getCurrentHeight() < SNAP_TO_BOTTOM_DISTANCE) {
                elevator.setElevatorPosition(0.0);
            } else {
                elevator.setElevatorSpeed(input);
            }

            shouldSetPosition = true;
        } else if (shouldSetPosition) {
            double velocity = elevator.getCurrentVelocity();

            double distance = (velocity * velocity) / (2 * ElevatorSubsystem.MAX_ABS_ACCELERATION);

            elevator.getMotors()[0].setIntegralAccumulator(0, 0, 0);
            elevator.setElevatorPosition(elevator.getCurrentHeight() + Math.copySign(distance, velocity));
            shouldSetPosition = false;
            hasSecondPidSet = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
