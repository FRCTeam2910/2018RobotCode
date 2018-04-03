package org.usfirst.frc.team2910.robot.motion;

public class Path extends Segment {
	private final double    initialDirection;
	private final Segment[] segments;
	private final double    length;

	public Path(double initialDirection, Segment... segments) {
		this.initialDirection = initialDirection;
		this.segments = segments;

		{
			double totalLength = 0;
			for (Segment segment : segments)
				totalLength += segment.getLength();
			this.length = totalLength;
		}
	}

	private double getDistanceAtSegment(int segment) {
		double distance = 0;
		for (int i = 0; i < segment; i++)
			distance += segments[i].getLength();
		return distance;
	}

	private double getDirectionAtSegment(int segment) {
		double direction = initialDirection;
		for (int i = 0; i < segment; i++)
			direction += segments[i].getDirection(1);
		return direction;
	}

	private int getSegmentAtDistance(double distance) {
		if (distance < getDistanceAtSegment(1)) return 0;

		for (int i = 0; i < segments.length - 1; i++) {
			double lowerBound = getDistanceAtSegment(i);
			double upperBound = getDistanceAtSegment(i + 1);

			if (lowerBound < distance && distance < upperBound)
				return i;
		}

		return segments.length - 1;
	}

	@Override
	public double getLength() {
		return length;
	}

	@Override
	public double getDirection(double percentage) {
		return getDirectionAtDistance(percentage * getLength());
	}

	@Override
	public double getDirectionAtDistance(double distance) {
		int currentSegment = getSegmentAtDistance(distance);

		double percentageOfSegment = (distance - getDistanceAtSegment(currentSegment)) / segments[currentSegment].getLength();

		return getDirectionAtSegment(currentSegment) + segments[currentSegment].getDirection(percentageOfSegment);
	}

	@Override
	public Path flip() {
		Segment[] flippedSegments = new Segment[segments.length];
		for (int i = 0; i < segments.length; i++)
			flippedSegments[i] = segments[i].flip();
		return new Path(-initialDirection, flippedSegments);
	}
}
