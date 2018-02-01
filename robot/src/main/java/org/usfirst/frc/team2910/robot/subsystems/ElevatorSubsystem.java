package org.usfirst.frc.team2910.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2910.robot.RobotMap;

public class ElevatorSubsystem extends Subsystem {
    public enum Gear {
        HIGH,
        LOW
    }

    public static final double SCORE_SCALE_POSITION = 1;
    public static final double SCORE_SWITCH_POISITON = 0.25;
    public static final double GROUND_POSITION = 0;

    public static final double MAX_ENCODER_VALUE = 4096;
    public static final double MIN_ENCODER_VALUE = 0;

    private final TalonSRX[] motors = {
            new TalonSRX(RobotMap.ELEVATOR_MOTORS[0]),
            new TalonSRX(RobotMap.ELEVATOR_MOTORS[1])
    };

    private final DoubleSolenoid shiftingSolenoid = new DoubleSolenoid(RobotMap.ELEVATOR_SHIFTER[0],
            RobotMap.ELEVATOR_SHIFTER[1]);

    public ElevatorSubsystem() {
        motors[0].configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        motors[0].config_kP(0, 0, 0);
        motors[0].config_kI(0, 0, 0);
        motors[0].config_kD(0, 0, 0);
        motors[0].set(ControlMode.Position, 0);

        for (int i = 1; i < motors.length; i++)
            motors[i].follow(motors[0]);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void setGear(Gear gear) {
        if (gear == Gear.HIGH)
            shiftingSolenoid.set(DoubleSolenoid.Value.kForward);
        else
            shiftingSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setElevatorPosition(double percentage) {
        double encoderTicks = percentage * (MAX_ENCODER_VALUE - MIN_ENCODER_VALUE) + MIN_ENCODER_VALUE;

        motors[0].set(ControlMode.Position, encoderTicks);
    }
}
