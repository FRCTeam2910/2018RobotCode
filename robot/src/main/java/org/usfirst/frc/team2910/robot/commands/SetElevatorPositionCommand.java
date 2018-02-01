package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class SetElevatorPositionCommand extends Command {

    private final ElevatorSubsystem elevator;
    private final double percentage;

    public SetElevatorPositionCommand(ElevatorSubsystem elevator, double percentage) {
        this.elevator = elevator;
        this.percentage = percentage;
    }

    @Override
    protected void initialize() {
        elevator.setElevatorPosition(percentage);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
