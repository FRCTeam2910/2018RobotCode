package org.usfirst.frc.team2910.robot.commands;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;

public class DisableLimitSwitchCommand extends Command {

    private final ElevatorSubsystem elevator;

    public DisableLimitSwitchCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;

        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.getMotors()[0].configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
