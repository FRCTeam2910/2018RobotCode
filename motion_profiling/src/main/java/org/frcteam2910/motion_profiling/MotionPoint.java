package org.frcteam2910.motion_profiling;

public final class MotionPoint {
    public final double x, y, heading;

    public MotionPoint(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public MotionPoint(MotionPoint point) {
        this(point.x, point.y, point.heading);
    }
}
