package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class ChangeElevatorModeCommand extends Command {
    private static final double WAIT_TIME = 0.75;
    private final ElevatorSubsystem elevator;
    private final ElevatorSubsystem.Mode targetMode;

    private final Timer timer = new Timer();

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

        timer.reset();
        timer.start();

        elevator.setMode(targetMode);
        elevator.setMode(targetMode);
    }

    @Override
    protected void execute() {
        if (timer.get() % WAIT_TIME > WAIT_TIME / 2)
            elevator.setElevatorSpeed(-0.1);
        else
            elevator.setElevatorSpeed(0.1);
    }

    @Override
    protected boolean isFinished() {
        if (targetMode == ElevatorSubsystem.Mode.REGULAR)
            return true;
        else
            return elevator.isShiftingSwitchActivated();
    }

    @Override
    protected void end() {
        timer.stop();
        if (elevator.getCurrentMode() == ElevatorSubsystem.Mode.CLIMBING)
            elevator.setElevatorSpeed(0);
        else
            elevator.setElevatorPosition(elevator.getCurrentHeight());
    }
}
