package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class CalibrateElevatorEncoderCommand extends Command {

    private static final double ELEVATOR_SPEED = -0.3;

    private final ElevatorSubsystem elevator;

    public CalibrateElevatorEncoderCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;
        requires(elevator);
    }

    @Override
    protected boolean isFinished() {
        return elevator.getLimitSwitchState();
    }

    @Override
    protected void initialize() {
        System.out.println("Start calibrate");
        elevator.setElevatorSpeed(ELEVATOR_SPEED);
    }

    @Override
    protected void execute() {
        elevator.setElevatorSpeed(ELEVATOR_SPEED);
    }

    @Override
    protected void end() {
        elevator.setElevatorSpeed(0.0);
        elevator.setElevatorPosition(0);
        elevator.zeroElevatorEncoder();
        System.out.println("Done calibrate");
    }

    @Override
    protected void interrupted() {
        elevator.setElevatorSpeed(0.0);
    }
}
