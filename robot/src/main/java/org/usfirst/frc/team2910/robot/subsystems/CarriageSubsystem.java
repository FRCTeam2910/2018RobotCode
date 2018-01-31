package org.usfirst.frc.team2910.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.RobotMap;
import org.usfirst.frc.team2910.robot.commands.CarriageIntakeCommand;

public class CarriageSubsystem extends Subsystem {

    private final TalonSRX leftMotor = new TalonSRX(RobotMap.CARRIAGE_LEFT_MOTOR);
    private final TalonSRX rightMotor = new TalonSRX(RobotMap.CARRIAGE_RIGHT_MOTOR);

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new CarriageIntakeCommand(this, Robot.getOI().getSecondaryController()));
    }

    public void setIntakeSpeed(double speed) {
        leftMotor.set(ControlMode.PercentOutput, speed);
        rightMotor.set(ControlMode.PercentOutput, speed);
    }
}
