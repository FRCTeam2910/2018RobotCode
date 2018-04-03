package org.usfirst.frc.team2910.robot.commands.autonomous;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2910.robot.motion.Path;
import org.usfirst.frc.team2910.robot.motion.Trajectory;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveModule;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

public class FollowPathCommand extends Command {
	public static final double ALLOWABLE_ERROR = 3;

	private final SwerveDriveSubsystem drivetrain;
	private final Path                 path;
	private final Trajectory           trajectory;

	// TODO: Comp bot P: 0.02, I: 0, D: 0
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

	private double angleCorrection;

	public FollowPathCommand(SwerveDriveSubsystem drivetrain, Path path) {
		this.drivetrain = drivetrain;
		this.path = path;
		this.trajectory = new Trajectory(path, drivetrain.getMaxAcceleration(), drivetrain.getMaxVelocity());

		angleCorrectionController.setInputRange(0, 360);
		angleCorrectionController.setOutputRange(-0.5, 0.5);
		angleCorrectionController.setContinuous(true);

		setTimeout(trajectory.getDuration() + 1);

		requires(drivetrain);
	}

	@Override
	protected void initialize() {
		for (SwerveDriveModule module : drivetrain.getSwerveModules()) {
			module.zeroDistance();
			module.setTargetDistance(path.getLength());
		}

		angleCorrectionController.setSetpoint(drivetrain.getGyroAngle());
		angleCorrectionController.enable();
	}

	@Override
	protected void execute() {
		double currentDistance = 0;
		for (SwerveDriveModule module : drivetrain.getSwerveModules())
			currentDistance += module.getDriveDistance();
		currentDistance /= drivetrain.getSwerveModules().length;

		double pathDirection = path.getDirectionAtDistance(currentDistance);

		SmartDashboard.putNumber("Current distance", currentDistance);
        SmartDashboard.putNumber("Path direction", pathDirection);

		double[] angles = drivetrain.calculateSwerveModuleAngles(
				Math.cos(Math.toRadians(pathDirection)),
				Math.sin(Math.toRadians(pathDirection)),
				0
		);

		for (int i = 0; i < angles.length; i++) {
			drivetrain.getSwerveModule(i).setTargetAngle(angles[i]);
		}
	}

	@Override
	protected void end() {
	    drivetrain.holonomicDrive(0, 0, 0);
		angleCorrectionController.disable();
	}

	@Override
	protected boolean isFinished() {
		double currentDistance = 0;
		for (SwerveDriveModule module : drivetrain.getSwerveModules())
			currentDistance += module.getDriveDistance();
		currentDistance /= drivetrain.getSwerveModules().length;

		return Math.abs(path.getLength() - currentDistance) < ALLOWABLE_ERROR;
	}
}
