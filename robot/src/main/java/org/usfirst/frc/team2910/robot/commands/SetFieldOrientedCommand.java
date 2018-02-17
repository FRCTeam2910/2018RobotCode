package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.HolonomicDrivetrain;

public final class SetFieldOrientedCommand extends Command {

    private final HolonomicDrivetrain drivetrain;

    public SetFieldOrientedCommand(HolonomicDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override
    protected void execute() {
        drivetrain.setFieldOriented(true);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
