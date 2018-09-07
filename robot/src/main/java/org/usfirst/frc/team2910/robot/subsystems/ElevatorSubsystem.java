package org.usfirst.frc.team2910.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.RobotMap;
import org.usfirst.frc.team2910.robot.commands.ManualElevatorControlCommand;

import static org.usfirst.frc.team2910.robot.RobotMap.ELEVATOR_MASTER_MOTOR;
import static org.usfirst.frc.team2910.robot.RobotMap.ELEVATOR_SLAVE_MOTOR;

public class ElevatorSubsystem extends Subsystem {

    public enum Gear {
        HIGH,
        LOW
    }

    public enum Mode {
        REGULAR,
        CLIMBING
    }
    public static final int STARTING_ENCODER_TICKS = 6527;

    public static final double TOP_POSITION = 80.00;
    public static final double SCORE_SCALE_POSITION = 6 * 12;
    public static final double SCORE_SWITCH_POISITON = 3 * 12;
    public static final double CLIMB_POSITION = 71;
    public static final double GROUND_POSITION = 0;

    /**
     * Max acceleration of the elevator in in*s^-2
     */
    public static final double MAX_ABS_ACCELERATION = 950.0;

    private static final double INCH_PER_ENCODER_TICK = 150 / 26009.6;
    private static final double ENCODER_TICKS_PER_INCH = 1 / INCH_PER_ENCODER_TICK;

    private final TalonSRX[] motors = {
            new TalonSRX(ELEVATOR_MASTER_MOTOR),
            new TalonSRX(ELEVATOR_SLAVE_MOTOR)
    };

    private final Solenoid shiftingSolenoid = new Solenoid(RobotMap.ELEVATOR_SHIFTER);

    private final Solenoid lockingSolenoid = new Solenoid(RobotMap.ELEVATOR_LOCKER);

    private double targetHeight = 0;

    private Mode currentMode = Mode.REGULAR;
    public ElevatorSubsystem() {
        motors[0].configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        motors[0].config_kP(0, 2.5, 0);
        motors[0].config_kI(0, 0.001, 0);
        motors[0].config_kD(0, 150.0, 0);
        motors[0].config_IntegralZone(0, (int) (3.0 * ENCODER_TICKS_PER_INCH), 0);

        motors[0].configAllowableClosedloopError(0, (int) ((1 / 8) * ENCODER_TICKS_PER_INCH), 0);

        motors[0].configForwardSoftLimitEnable(false, 0);
        motors[0].configNominalOutputForward(0.05, 0);
        motors[0].configNominalOutputReverse(-0.05, 0);

        motors[0].configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
//        motors[0].configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);

        for (int i = 0; i < motors.length; i++) {
            motors[i].configContinuousCurrentLimit(50, 0);
            motors[i].configPeakCurrentDuration(100, 0);
            motors[i].configPeakCurrentLimit(50, 0);
        }

        motors[0].setSensorPhase(true);
        motors[0].setInverted(true);
        motors[1].setInverted(true);

        for (int i = 1; i < motors.length; i++) {
            motors[i].follow(motors[0]);
            motors[i].configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen, motors[0].getDeviceID(), 0);
        }

        unlock();
        setMode(Mode.REGULAR);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ManualElevatorControlCommand(this));
    }

    public void setMode(Mode mode) {
        switch (currentMode) {
            case REGULAR:
                setGear(Gear.HIGH);
                motors[0].enableCurrentLimit(true);
                motors[1].enableCurrentLimit(true);
                break;
            case CLIMBING:
                setGear(Gear.LOW);
                motors[0].enableCurrentLimit(false);
                motors[1].enableCurrentLimit(false);
                break;
        }

        this.currentMode = mode;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setGear(Gear gear) {
        if (gear == Gear.LOW) {
            shiftingSolenoid.set(true);
        } else {
            shiftingSolenoid.set(false);
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

        if (getCurrentHeight() > TOP_POSITION)
            speed = Math.max(speed, 0);

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

    public double getEncoderVelocity() {
        return motors[0].getSelectedSensorVelocity(0);
    }

    public double getCurrentHeight() {
        if (Robot.PRACTICE_BOT)
            return targetHeight;

        double encPos = getEncoderValue();

        return encPos * INCH_PER_ENCODER_TICK;
    }

    public double getCurrentVelocity() {
        double encVel = getEncoderVelocity();

        return encVel * INCH_PER_ENCODER_TICK * (1000.0 / 100.0);
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
