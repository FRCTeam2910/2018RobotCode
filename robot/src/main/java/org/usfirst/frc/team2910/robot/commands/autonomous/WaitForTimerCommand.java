package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WaitForTimerCommand extends Command {
    private final Timer timer;
    private final double time;

    public WaitForTimerCommand(Timer timer, double time) {
        this.timer = timer;
        this.time = time;
    }

    @Override
    protected boolean isFinished() {
        return timer.hasPeriodPassed(time);
    }
}
