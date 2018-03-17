package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class ChangeElevatorModeCommand extends Command {
    private final ElevatorSubsystem elevator;
    private final ElevatorSubsystem.Mode targetMode;

    public ChangeElevatorModeCommand(ElevatorSubsystem elevator, ElevatorSubsystem.Mode targetMode) {
        this.elevator = elevator;
        this.targetMode = targetMode;

        setInterruptible(false);
        setTimeout(10);

        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.setElevatorSpeed(-0.1);

        elevator.setMode(targetMode);
        elevator.setMode(targetMode);
    }

    @Override
    protected boolean isFinished() {
        if (targetMode == ElevatorSubsystem.Mode.CLIMBING)
            return true;
        else
            return elevator.isShiftingSwitchActivated();
    }

    @Override
    protected void end() {
        elevator.setElevatorPosition(elevator.getCurrentHeight());
    }
}
