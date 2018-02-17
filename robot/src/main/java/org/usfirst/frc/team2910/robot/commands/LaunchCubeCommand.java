package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;

public class LaunchCubeCommand extends Command {

    private final GathererSubsystem gatherer;
    private final double runTime;
    private final Timer timer = new Timer();

    public LaunchCubeCommand(GathererSubsystem gatherer, double runTime) {
        this.gatherer = gatherer;
        this.runTime = runTime;

        requires(gatherer);
    }

    @Override
    protected void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    protected void execute() {
        gatherer.activateGatherer(1, 0);
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
