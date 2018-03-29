package org.usfirst.frc.team2910.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frcteam2910.motion_profiling.MotionProfile;
import org.frcteam2910.motion_profiling.MotionProfileLoader;
import org.frcteam2910.motion_profiling.MotionProfileSerializer;
import org.usfirst.frc.team2910.robot.commands.ResetMotorsCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.AutonomousChooser;
import org.usfirst.frc.team2910.robot.commands.autonomous.FollowMotionProfileCommand;
import org.usfirst.frc.team2910.robot.commands.autonomous.FollowMotionProfileOnboardCommand;
import org.usfirst.frc.team2910.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team2910.robot.subsystems.GathererSubsystem;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static final boolean DEBUG = true;

	private static OI mOI; 
	private static SwerveDriveSubsystem swerveDriveSubsystem;
	private static ElevatorSubsystem elevatorSubsystem;
	private static GathererSubsystem gathererSubsystem;

	private final AutonomousChooser autoChooser = new AutonomousChooser();
	private Command autoCommand;

	public static OI getOI() {
		return mOI;
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		mOI = new OI(this);

//		gathererSubsystem = new GathererSubsystem();
		swerveDriveSubsystem = new SwerveDriveSubsystem();
//		elevatorSubsystem = new ElevatorSubsystem();

		mOI.registerControls();
		NetworkTableInstance instance = NetworkTableInstance.getDefault();
		instance.getTable("limelight").getEntry("ledMode").setNumber(1.0);

		SmartDashboard.putData("Reset Motors", new ResetMotorsCommand(swerveDriveSubsystem));
}

    @Override
    public void robotPeriodic() {
        for (int i = 0; i < 4; i++) {
            SmartDashboard.putNumber("Module Angle " + i, Math.round(swerveDriveSubsystem.getSwerveModule(i).getCurrentAngle() * 100) / 100.0);
            SmartDashboard.putNumber("Module Pos " + i, (swerveDriveSubsystem.getSwerveModule(i).getDriveDistance()));
            SmartDashboard.putNumber("Module Raw Angle " + i, swerveDriveSubsystem.getSwerveModule(i).getAngleMotor().getSelectedSensorPosition(0));
            SmartDashboard.putNumber("Module Drive Speed " + i, swerveDriveSubsystem.getSwerveModule(i).getDriveMotor().getMotorOutputPercent());
            SmartDashboard.putNumber("Module Current Ticks " + i, swerveDriveSubsystem.getSwerveModule(i).getDriveMotor().getSelectedSensorPosition(0));
        	SmartDashboard.putNumber("Module Drive % " + i, swerveDriveSubsystem.getSwerveModule(i).getDriveMotor().getMotorOutputPercent());
        	SmartDashboard.putNumber("Module Speed " + i, swerveDriveSubsystem.getSwerveModule(i).getCurrentSpeed());
        }

//		SmartDashboard.putNumber("Elevator encoder", elevatorSubsystem.getEncoderValue());
//		SmartDashboard.putNumber("Elevator height", elevatorSubsystem.getCurrentHeight());
//		SmartDashboard.putNumber("Elevator target height", elevatorSubsystem.getTargetHeight());
//		SmartDashboard.putNumber("Elevator speed", elevatorSubsystem.getMotors()[0].getSelectedSensorVelocity(0));

		SmartDashboard.putNumber("Drivetrain Angle", swerveDriveSubsystem.getGyroAngle());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		for (int i = 0; i < 4; i++) {
			swerveDriveSubsystem.getSwerveModule(i).robotDisabledInit();
		}
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		try {
			System.out.println("Loading profile");
			List<MotionProfile> pro = MotionProfileLoader.loadProfile("left_same_switch_turn");
			System.out.println("Profile length: " + pro.size());
			autoCommand = new FollowMotionProfileOnboardCommand(swerveDriveSubsystem, pro);
			autoCommand.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		autoCommand = autoChooser.getCommand(this);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autoCommand != null) autoCommand.cancel();

		for (int i = 0; i < 4; i++)
			swerveDriveSubsystem.getSwerveModule(i).zeroDistance();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() { }

	public SwerveDriveSubsystem getDrivetrain() {
		return swerveDriveSubsystem;
	}

	public ElevatorSubsystem getElevator() {
		return elevatorSubsystem;
	}

	public GathererSubsystem getGatherer() {
		return gathererSubsystem;
	}
}
