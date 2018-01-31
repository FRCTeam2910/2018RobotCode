package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class ToggleElevatorGearCommand extends Command{

    private final ElevatorSubsystem elevator;

    public ToggleElevatorGearCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;
    }

    @Override
    protected void initialize() {
        elevator.setGear(ElevatorSubsystem.Gear.LOW);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        elevator.setGear(ElevatorSubsystem.Gear.HIGH);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
