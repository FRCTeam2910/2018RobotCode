package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class WaitForElevatorPositionCommand extends Command {
    private final ElevatorSubsystem elevator;
    private final double targetHeight;

    public WaitForElevatorPositionCommand(ElevatorSubsystem elevator, double targetHeight) {
        this.elevator = elevator;
        this.targetHeight = targetHeight;
    }

    @Override
    protected void initialize() {
        System.out.println("[INFO]: Waiting for elevator position");
    }

    @Override
    protected boolean isFinished() {
        if (Robot.PRACTICE_BOT)
            return true;

        return Math.abs(targetHeight - elevator.getCurrentHeight()) < 3;
    }

    @Override
    protected void end() {
        System.out.println("[INFO]: Finished waiting for elevator");
    }
}
