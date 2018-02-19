package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class SetElevatorPositionCommand extends Command {

    private final ElevatorSubsystem elevator;
    private final double height;
    private final double waitTime;
    private final Timer waitTimer = new Timer();

    public SetElevatorPositionCommand(ElevatorSubsystem elevator, double height) {
        this(elevator, height, 0);
    }

    public SetElevatorPositionCommand(ElevatorSubsystem elevator, double height, double waitTime) {
        this.elevator = elevator;
        this.height = height;
        this.waitTime = waitTime;

        requires(elevator);
    }

    @Override
    protected void initialize() {
        waitTimer.reset();
        waitTimer.start();
    }

    @Override
    protected void execute() {
        if (waitTimer.hasPeriodPassed(waitTime)) {
            elevator.setElevatorPosition(height);
        }
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(height - elevator.getCurrentHeight()) < (1.0/8.0);
    }

    @Override
    protected void end() {
        waitTimer.stop();
        waitTimer.reset();
    }

    @Override
    protected void interrupted() {
        System.out.println("INTERRUPTED");
        end();
    }
}
