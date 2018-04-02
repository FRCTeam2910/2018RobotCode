package org.usfirst.frc.team2910.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Drivetrain extends Subsystem {
	private final double width;
	private final double length;
	private double speedMultiplier = 1;

	public Drivetrain(double width, double length) {
		this.width = width;
		this.length = length;
	}

	public final double getWidth() {
		return width;
	}

	public final double getLength() {
		return length;
	}

	public double getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(double speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

	public abstract double getMaxAcceleration();

	public abstract double getMaxVelocity();
}
