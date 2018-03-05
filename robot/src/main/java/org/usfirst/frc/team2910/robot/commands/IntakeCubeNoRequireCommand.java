package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;

public class IntakeCubeNoRequireCommand extends Command {

    private final GathererSubsystem gatherer;
    private final double out, rot;
    private final double runTime;
    private final Timer timer = new Timer();

    public IntakeCubeNoRequireCommand(GathererSubsystem gatherer, double runTime) {
        this(gatherer, -1, 0, runTime);
    }

    public IntakeCubeNoRequireCommand(GathererSubsystem gatherer, double out, double rot, double runTime) {
        this.gatherer = gatherer;
        this.out = out;
        this.rot = rot;
        this.runTime = runTime;
    }

    @Override
    protected void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    protected void execute() {
        gatherer.activateGatherer(out, rot);
    }

    @Override
    protected boolean isFinished() {
        return timer.hasPeriodPassed(runTime);
    }

    @Override
    protected void end() {
        timer.stop();
        timer.reset();
        gatherer.activateGatherer(0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
