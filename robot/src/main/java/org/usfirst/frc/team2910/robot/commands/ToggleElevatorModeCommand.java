package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class ToggleElevatorModeCommand extends Command {

    private final ElevatorSubsystem elevator;

    private ElevatorSubsystem.Gear mode = ElevatorSubsystem.Gear.LOW;

    public ToggleElevatorModeCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;
    }

    @Override
    protected void execute() {
        elevator.setElevatorSpeed(0.0);
        elevator.setGear(mode);
        if (mode == ElevatorSubsystem.Gear.LOW)
            mode = ElevatorSubsystem.Gear.HIGH;
        else
            mode = ElevatorSubsystem.Gear.LOW;
        elevator.setElevatorPosition(elevator.getTargetHeight());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
