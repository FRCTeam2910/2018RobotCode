package org.usfirst.frc.team2910.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveModule;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class SoftCubePlaceCommand extends Command {
    private static final double PLACE_PATH_LENGTH = 120;

    private final SwerveDriveSubsystem drivetrain;
    private final GathererSubsystem gatherer;

    private final double maxVelocity;
    private final double maxAcceleration;

    private boolean wasFieldOriented = true;
    private double angleCorrection = 0.0;

    private final PIDController angleCorrectionController = new PIDController(0.01, 0, 0, new PIDSource() {
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return drivetrain.getGyroAngle();
        }
    }, output -> angleCorrection = output);

    public SoftCubePlaceCommand(SwerveDriveSubsystem drivetrain, GathererSubsystem gatherer, double maxVelocity, double maxAcceleration) {
        this.drivetrain = drivetrain;
        this.gatherer = gatherer;
        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;

        angleCorrectionController.setInputRange(0, 360);
        angleCorrectionController.setOutputRange(-0.5, 0.5);
        angleCorrectionController.setContinuous(true);

        requires(drivetrain);
        requires(gatherer);
    }

    @Override
    protected void initialize() {
        wasFieldOriented = drivetrain.isFieldOriented();
        drivetrain.setFieldOriented(false);

        for (SwerveDriveModule module : drivetrain.getSwerveModules()) {
            module.setMotionConstraints(maxAcceleration, maxVelocity);
            module.zeroDistance();
            module.setTargetDistance(PLACE_PATH_LENGTH);
        }

        gatherer.outtakeForDistance(PLACE_PATH_LENGTH, maxVelocity, maxAcceleration);

        angleCorrectionController.setSetpoint(drivetrain.getGyroAngle());
        angleCorrectionController.enable();
    }

    @Override
    protected void execute() {
        double[] angles = drivetrain.calculateSwerveModuleAngles(
                -1,
                0,
                angleCorrection
        );

        for (int i = 0; i < angles.length; i++) {
            drivetrain.getSwerveModule(i).setTargetAngle(angles[i]);
        }
    }

    @Override
    protected void end() {
        angleCorrectionController.disable();
        drivetrain.setFieldOriented(wasFieldOriented);

        drivetrain.holonomicDrive(0, 0, 0);
        gatherer.activateGatherer(0, 0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
