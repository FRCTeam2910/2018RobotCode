package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WaitCommand extends Command {
    private final double time;
    private final Timer timer = new Timer();

    public WaitCommand(double time) {
        this.time = time;
    }

    @Override
    protected void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    protected boolean isFinished() {
        return timer.hasPeriodPassed(time);
    }

    @Override
    protected void end() {
        timer.stop();
        timer.reset();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
