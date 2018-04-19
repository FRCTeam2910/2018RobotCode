package org.usfirst.frc.team2910.robot.motion;

public class Trajectory {
	private final Path   path;
	private final double maxAcceleration, maxVelocity;

	public Trajectory(Path path, double maxAcceleration, double maxVelocity) {
		this.path = path;
		this.maxAcceleration = maxAcceleration * 12;
		this.maxVelocity = maxVelocity * 12;
	}

	public boolean isTrapezoidal() {
		return ((maxVelocity * maxVelocity) / maxAcceleration) < path.getLength();
	}

	/**
	 * Get the amount of time it takes to complete the path.
	 *
	 * @return The amount of time to complete the path.
	 */
	public double getDuration() {
		if (isTrapezoidal()) {
			// Use calculation to calculate duration of trapezoidal path
			return (maxVelocity / maxAcceleration) + (path.getLength() / maxVelocity);
		} else {
			// Use calculation to calculate duration of triangular path
			return Math.sqrt(path.getLength() / maxAcceleration);
		}
	}

	public double getMaxAcceleration() {
		return maxAcceleration / 12;
	}

	public double getMaxVelocity() {
		return maxVelocity / 12;
	}
}
