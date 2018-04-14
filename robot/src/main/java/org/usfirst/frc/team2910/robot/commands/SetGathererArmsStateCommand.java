package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;

public class SetGathererArmsStateCommand extends Command {
    private final GathererSubsystem gatherer;
    private final GathererSubsystem.Position position;

    public SetGathererArmsStateCommand(GathererSubsystem gatherer, GathererSubsystem.Position position) {
        this.gatherer = gatherer;
        this.position = position;
    }

    @Override
    protected void initialize() {
        gatherer.setLeftArm(position);
        gatherer.setRightArm(position);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
