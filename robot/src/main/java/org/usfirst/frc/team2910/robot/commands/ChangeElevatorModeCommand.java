package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class ChangeElevatorModeCommand extends Command {

    private static final double SHIFT_WAIT_TIME = 1.0;

    private final Timer timer = new Timer();
    private final ElevatorSubsystem elevator;
    private final ElevatorSubsystem.Mode targetMode;

    private boolean waitingForZeroVel = true;

    public ChangeElevatorModeCommand(ElevatorSubsystem elevator, ElevatorSubsystem.Mode targetMode) {
        this.elevator = elevator;
        this.targetMode = targetMode;

        setInterruptible(false);

        requires(elevator);
    }

    @Override
    protected void initialize() {
        if (targetMode == ElevatorSubsystem.Mode.Climbing) {
            elevator.setMode(targetMode);
            elevator.setMode(targetMode);
        } else
            elevator.setElevatorPosition(elevator.getCurrentHeight());
    }

    @Override
    protected void execute() {
        if (targetMode == ElevatorSubsystem.Mode.Climbing) return;

        if (Math.abs(elevator.getMotors()[0].getSelectedSensorVelocity(0)) < 20 &&
                Math.abs(elevator.getTargetHeight() - elevator.getCurrentHeight()) < (1.0 / 8.0) && waitingForZeroVel) {
            elevator.setElevatorSpeed(-0.1);
            elevator.setMode(targetMode);
            elevator.setMode(targetMode);
            timer.reset();
            timer.start();

            System.out.println("RUNNING TIMER");

            waitingForZeroVel = false;
        }
    }

    @Override
    protected boolean isFinished() {
        if (targetMode == ElevatorSubsystem.Mode.Climbing)
            return true;
        else
            return timer.hasPeriodPassed((targetMode == ElevatorSubsystem.Mode.Regular ? 2 : 1) * SHIFT_WAIT_TIME);
    }

    @Override
    protected void end() {
        timer.stop();
        timer.reset();
        elevator.setElevatorPosition(elevator.getCurrentHeight());

        waitingForZeroVel = true;
    }
}
