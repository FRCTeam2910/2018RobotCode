package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;

public class LaunchCubeCommand extends Command {

    private final GathererSubsystem gatherer;
    private final double runTime;
    private final double speed;
    private final Timer timer = new Timer();

    public LaunchCubeCommand(GathererSubsystem gatherer, double runTime) {
        this(gatherer, runTime, 1);
    }

    public LaunchCubeCommand(GathererSubsystem gatherer, double runTime, double speed) {
        this.gatherer = gatherer;
        this.runTime = runTime;
        this.speed = speed;

        requires(gatherer);
    }

    @Override
    protected void initialize() {
        System.out.println("[INFO]: Launching cube");
        timer.reset();
        timer.start();
    }

    @Override
    protected void execute() {
        gatherer.activateGatherer(speed, 0);
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
