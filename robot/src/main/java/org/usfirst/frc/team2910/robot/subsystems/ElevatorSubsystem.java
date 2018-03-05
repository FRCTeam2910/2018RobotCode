package org.usfirst.frc.team2910.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2910.robot.RobotMap;
import org.usfirst.frc.team2910.robot.commands.ManualElevatorControlCommand;

public class ElevatorSubsystem extends Subsystem {
    public enum Gear {
        HIGH,
        LOW
    }

    public enum Mode {
        Regular,
        Climbing
    }

    public static final int STARTING_ENCODER_TICKS = 6527;

    public static final double TOP_POSITION = 79.25;
    public static final double SCORE_SCALE_POSITION = 6 * 12;
    public static final double SCORE_SWITCH_POISITON = 3 * 12;
    public static final double GROUND_POSITION = 0;

    private static final double INCH_PER_ENCODER_TICK = 150 / 26009.6;
    private static final double ENCODER_TICKS_PER_INCH = 1 / INCH_PER_ENCODER_TICK;

    private final TalonSRX[] motors = {
            new TalonSRX(RobotMap.ELEVATOR_MOTORS[0]),
            new TalonSRX(RobotMap.ELEVATOR_MOTORS[1])
    };

    private final Solenoid shiftingSolenoid = new Solenoid(RobotMap.ELEVATOR_SHIFTER);

    private final Solenoid lockingSolenoid = new Solenoid(RobotMap.ELEVATOR_LOCKER);

    private double targetHeight = 0;
    private Mode currentMode = Mode.Regular;

    public ElevatorSubsystem() {
        motors[0].configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        motors[0].config_kP(0, 4, 0);
        motors[0].config_kI(0, 0.01, 0);
        motors[0].config_kD(0, 0, 0);
        motors[0].config_IntegralZone(0, 500, 0);

        motors[0].configAllowableClosedloopError(0, (int) ((1 / 8) * ENCODER_TICKS_PER_INCH), 0);

        motors[0].configForwardSoftLimitEnable(false, 0);
        motors[0].configNominalOutputForward(0.05, 0);
        motors[0].configNominalOutputReverse(-0.05, 0);

        motors[0].configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
//        motors[0].configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);

        unlock();
        setGear(Gear.HIGH);

        motors[0].setSensorPhase(true);
        motors[0].setInverted(true);
        motors[1].setInverted(true);

        for (int i = 1; i < motors.length; i++) {
            motors[i].follow(motors[0]);
            motors[i].configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen, motors[0].getDeviceID(), 0);
        }
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ManualElevatorControlCommand(this));
    }

    public void setMode(Mode mode) {
        switch (currentMode) {
            case Regular:
                setGear(Gear.HIGH);
                break;
            case Climbing:
                setGear(Gear.LOW);
                break;
        }

        this.currentMode = mode;
    }

    public void setGear(Gear gear) {
        System.out.printf("Shifting to %s%n", gear);
        if (gear == Gear.HIGH) {
            shiftingSolenoid.set(false);
        } else {
            shiftingSolenoid.set(true);
        }
    }

    public void setElevatorPosition(double height) {
        if (isLocked()) return;

        height = Math.min(height, TOP_POSITION);
        targetHeight = height;
        double encoderTicks = height * ENCODER_TICKS_PER_INCH;

        motors[0].set(ControlMode.Position, encoderTicks);
    }

    public void setElevatorSpeed(double speed) {
        if (isLocked()) return;

        motors[0].set(ControlMode.PercentOutput, speed);
    }

    public boolean getLimitSwitchState() {
        return motors[0].getSensorCollection().isRevLimitSwitchClosed();
    }

    public void zeroElevatorEncoder() {
        motors[0].setSelectedSensorPosition(0, 0, 0);
    }

    public void lock() {
        System.out.println("LOCKED");
        lockingSolenoid.set(false);
        setElevatorSpeed(0);
    }

    public void unlock() {
        System.out.println("UNLOCKED");
        lockingSolenoid.set(true);
    }

    public boolean isLocked() {
        return !lockingSolenoid.get();
    }

    public double getEncoderValue() {
        return motors[0].getSelectedSensorPosition(0);
    }

    public double getCurrentHeight() {
        double encPos = getEncoderValue();

        return encPos * INCH_PER_ENCODER_TICK;
    }

    public double getTargetHeight() {
        return targetHeight;
    }

    public TalonSRX[] getMotors() {
        return motors;
    }

    public void setEncoderPosition(int position) {
        motors[0].setSelectedSensorPosition(position, 0, 0);
    }
}
