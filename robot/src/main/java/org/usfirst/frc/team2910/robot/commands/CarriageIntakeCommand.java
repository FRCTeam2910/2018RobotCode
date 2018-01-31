package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.input.IGamepad;
import org.usfirst.frc.team2910.robot.subsystems.CarriageSubsystem;

public class CarriageIntakeCommand extends Command {

    private final CarriageSubsystem subsystem;
    private final IGamepad gamepad;

    public CarriageIntakeCommand(CarriageSubsystem subsystem, IGamepad gamepad) {
        this.subsystem = subsystem;
        this.gamepad = gamepad;

        requires(subsystem);
    }

    @Override
    protected void execute() {
        subsystem.setIntakeSpeed(gamepad.getLeftYValue());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        end();
    }

    protected void end() {
        subsystem.setIntakeSpeed(0.0);
    }

}
