package org.usfirst.frc.team2910.robot.motion;

public final class Path {
    public static final class Segment {
        private final double length;
        private final double direction;
        public Segment(double length, double direction) {
            this.length = length;
            this.direction = direction;
        }

        public double getPercentageAlongSegment(double distance) {
            return distance / this.length;
        }
    }

    private final double initialDirection;
    private final Segment[] segments;

    public Path(double initialDirection, Segment... segments) {
        this.initialDirection = initialDirection;
        this.segments = segments;
    }

    public double getDistanceAtSegment(int segmentIndex) {
        double totalDistance = 0;

        for (int i = 0; i < segmentIndex; i++) {
            totalDistance += segments[i].length;
        }

        return totalDistance;
    }

    public double getTotalDistance() {
        return getDistanceAtSegment(segments.length);
    }

    public double getDirectionAtSegment(int segmentIndex) {
        double directionChange = initialDirection;

        for (int i = 0; i < segmentIndex; i++) {
            directionChange += segments[i].direction;
        }

        return directionChange;
    }

    public int getSegmentFromDistance(double distance) {
        if (distance < getDistanceAtSegment(1)) return 0;

        for (int i = 1; i < segments.length - 1; i++) {
            double lowerBound = getDistanceAtSegment(i);
            double upperBound = getDistanceAtSegment(i + 1);

            System.out.printf("Lower bound (%d): %.3f%n", i, lowerBound);
            System.out.printf("Upper bound (%d): %.3f%n", i + 1, upperBound);
            if (lowerBound < distance &&
                    distance < upperBound)
                return i;
        }

        return segments.length - 1;
    }

    public double getDirectionAtDistance(double distance) {
        int currentSegmentIndex = getSegmentFromDistance(distance);

        System.out.printf("Current Segment: %d%n", currentSegmentIndex);

        double percentageOfSegment = (distance - getDistanceAtSegment(currentSegmentIndex)) / segments[currentSegmentIndex].length;
        System.out.printf("Segment completion %%: %.3f%n", percentageOfSegment);

        return getDirectionAtSegment(currentSegmentIndex) +
                percentageOfSegment * segments[currentSegmentIndex].direction;
    }
}
