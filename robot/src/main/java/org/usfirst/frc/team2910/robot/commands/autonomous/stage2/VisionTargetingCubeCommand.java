package org.usfirst.frc.team2910.robot.commands.autonomous.stage2;

import org.usfirst.frc.team2910.robot.Robot;
import org.usfirst.frc.team2910.robot.subsystems.SwerveDriveSubsystem;
import org.usfirst.frc.team2910.robot.util.Side;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionTargetingCubeCommand extends CommandGroup{
	
	private final Robot robot;
	private final PIDController angleErrorController;
	private Side side;
	private boolean finish = false;
	private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	private NetworkTableEntry tx = table.getEntry("tx");
	private NetworkTableEntry ty = table.getEntry("ty");
	private NetworkTableEntry tv = table.getEntry("tv");
	private double PIDForwardValue;
	private double PIDStrafeValue;
	private double rotationFactor;
	
	private PIDController forwardController = new PIDController(0.03, 0.0, 0.0, new PIDSource() {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return ty.getDouble(0);
		}
	
	}, output -> {	
		PIDForwardValue = output;
	});
	
	private PIDController strafeController = new PIDController(0.025, 0.0, 0.0, new PIDSource() {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return tx.getDouble(0);
		}
		
	}, output -> {
		PIDStrafeValue = -output;
		SmartDashboard.putNumber("PID Strafe Value", PIDStrafeValue);
	});

	public VisionTargetingCubeCommand(Robot robot, Side side) {
        this.robot = robot;
        this.side = side;
        
        strafeController.setInputRange(-27, 27);
        strafeController.setOutputRange(-1, 1);
        strafeController.setAbsoluteTolerance(0.5);
        forwardController.setInputRange(-20.5, 20.5);
        forwardController.setOutputRange(-1, 1);
        forwardController.setAbsoluteTolerance(0.5);
        
        angleErrorController = new PIDController(0.01, 0, 0, new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) { }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kDisplacement;
            }

            @Override
            public double pidGet() {
                return robot.getDrivetrain().getGyroAngle();
            }
        }, output -> {
            rotationFactor = output;
        });
        
        angleErrorController.setInputRange(0, 360);
        angleErrorController.setOutputRange(-0.5, 0.5);
        angleErrorController.setContinuous(true);
	}
	
	protected void initialize() {
		strafeController.enable();
		forwardController.enable();
		angleErrorController.enable();
		
		angleErrorController.setSetpoint(robot.getDrivetrain().getGyroAngle());
	}
	
	
	protected void execute() {
		SmartDashboard.putNumber("Rotation Factor", rotationFactor);
		if(tv.getDouble(0) == 0){	//If there is no target
			if(side == side.RIGHT) {
				robot.getDrivetrain().holonomicDrive(0, -.4, rotationFactor);
			} else {
				robot.getDrivetrain().holonomicDrive(0, .4, rotationFactor);
			}
		} else if(tx.getDouble(0) != 0 || ty.getDouble(0) != 0) {	//Else check that we are not already at the tar
				robot.getDrivetrain().holonomicDrive(PIDForwardValue, PIDStrafeValue, rotationFactor);
		}	else {
			finish = true;
			isFinished();
		}
	}
	
	protected boolean isFinished() {
		return finish;
	}
	
	protected void end() {
		strafeController.disable();
		forwardController.disable();
		angleErrorController.disable();
	}
}
