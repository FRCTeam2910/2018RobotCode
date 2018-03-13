package org.frcteam2910.motion_profiling;

import java.io.Serializable;

public final class MotionSegment implements Serializable {
    private static final long serialVersionUID = 5953190419778457903L;

    public double deltaTime;
    public double x;
    public double y;
    public double position;
    public double velocity;
    public double acceleration;
    public double jerk;
    public double heading;

    public MotionSegment(double deltaTime, double x, double y, double position, double velocity, double acceleration, double jerk, double heading) {
        this.deltaTime = deltaTime;
        this.x = x;
        this.y = y;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.jerk = jerk;
        this.heading = heading;
    }

    public MotionSegment(MotionSegment segment) {
        this(segment.deltaTime, segment.x, segment.y, segment.position, segment.velocity, segment.acceleration, segment.jerk, segment.heading);
    }

    @Override
    public String toString() {
        return String.format("(dt:% 3f x:% 3f y:% 3f p:% 3f v:% 3f a:% 3f j:% 3f h:% 3f)",
                deltaTime, x, y, position, velocity, acceleration, jerk, heading);
    }
}
