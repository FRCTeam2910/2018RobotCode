package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.Utilities;
import org.usfirst.frc.team2910.robot.input.IGamepad;
import org.usfirst.frc.team2910.robot.subsystems.MotorTesterSubsystem;

@Deprecated
public class MotorTesterCommand extends Command {

    private final MotorTesterSubsystem subsystem;
    private final IGamepad gamepad;

    public MotorTesterCommand(MotorTesterSubsystem subsystem, IGamepad gamepad) {
        this.subsystem = subsystem;
        this.gamepad = gamepad;

        requires(subsystem);
    }

    @Override
    protected void execute() {
        subsystem.setLeftMotor(Utilities.deadband(gamepad.getLeftYValue()));
        subsystem.setRightMotor(Utilities.deadband(gamepad.getRightYValue()));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        subsystem.setLeftMotor(0);
        subsystem.setRightMotor(0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
