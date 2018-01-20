package org.usfirst.frc.team2910.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.commands.MotorTesterCommand;

/**
 * Don't actually use this class
 *
 * TODO: Remove me
 */
@Deprecated
public class MotorTesterSubsystem extends Subsystem {

    private SpeedController leftMotor = new Talon(0);
    private SpeedController rightMotor = new Talon(1);

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MotorTesterCommand(this, Robot.getOI().getSecondaryController()));
    }

    public void setLeftMotor(double speed) {
        leftMotor.set(speed);
    }

    public void setRightMotor(double speed) {
        rightMotor.set(speed);
    }
}
