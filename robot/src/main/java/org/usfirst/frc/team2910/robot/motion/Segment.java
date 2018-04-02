package org.usfirst.frc.team2910.robot.motion;

public abstract class Segment {
	public abstract double getDirection(double percentage);

	public double getDirectionAtDistance(double distance) {
		assert distance > 0 : distance < getLength();
		return getDirection(distance / getLength());
	}

	public abstract double getLength();

	protected abstract Segment flip();

	public static final class Arc extends Segment {
		private final double length;
		private final double direction;

		public Arc(double length, double direction) {
			this.length = length;
			this.direction = direction;
		}

		@Override
		public double getDirection(double percentage) {
			return percentage * direction;
		}

		@Override
		public double getLength() {
			return length;
		}

		@Override
		protected Segment flip() {
			return new Arc(length, -direction);
		}
	}

	public static final class Line extends Segment {
		private final double length;

		public Line(double length) {
			this.length = length;
		}

		@Override
		public double getDirection(double percentage) {
			return 0;
		}

		@Override
		public double getLength() {
			return length;
		}

		@Override
		protected Segment flip() {
			return new Line(length);
		}
	}
}
