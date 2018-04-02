package org.usfirst.frc.team2910.robot.motion;

public class Trajectory {
	private final Path   path;
	private final double maxAcceleration, maxVelocity;

	public Trajectory(Path path, double maxAcceleration, double maxVelocity) {
		this.path = path;
		this.maxAcceleration = maxAcceleration;
		this.maxVelocity = maxVelocity;
	}

	private boolean isTrapezoidal() {
		return (maxVelocity * maxAcceleration / maxAcceleration) > path.getLength();
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
}
