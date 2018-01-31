package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class SetElevatorPositionCommand extends Command {

    private final ElevatorSubsystem elevator;
    private final double height;

    public SetElevatorPositionCommand(ElevatorSubsystem elevator, double height) {
        this.elevator = elevator;
        this.height = height;
    }

    @Override
    protected void initialize() {
        elevator.setElevatorPosition(height);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
