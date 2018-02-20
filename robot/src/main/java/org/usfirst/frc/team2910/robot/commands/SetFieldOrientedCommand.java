package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.HolonomicDrivetrain;

public final class SetFieldOrientedCommand extends Command {

    private final HolonomicDrivetrain drivetrain;
    private final boolean isFieldOriented;

    @Deprecated
    public SetFieldOrientedCommand(HolonomicDrivetrain drivetrain) {
        this(drivetrain,true);
    }

    public SetFieldOrientedCommand(HolonomicDrivetrain drivetrain, boolean isFieldOriented) {
        this.drivetrain = drivetrain;
        this.isFieldOriented = isFieldOriented;
    }

    @Override
    protected void execute() {
        drivetrain.setFieldOriented(isFieldOriented);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
