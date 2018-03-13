package org.frcteam2910.motion_profiling;

// TODO: Rewrite because I need to
public class MotionProfileFollower {
    private double kp, ki, kd, kv, ka;

    private double lastError;
    private double heading;

    private int segment;
    private MotionProfile profile;

    public MotionProfileFollower(MotionProfile profile) {
        this.profile = profile;
    }

    public void setPIDVA(double kp, double ki, double kd, double kv, double ka) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kv = kv;
        this.ka = ka;
    }

    public void reset() {
        lastError = 0;
        segment = 0;
    }

    public double calculate(double distanceCovered) {
        if (segment < profile.getLength()) {
            MotionSegment seg = profile.getSegment(segment);
            double error = seg.position - distanceCovered;
            double calculatedValue =
                    kp * error +
                            kd * ((error - lastError) / seg.deltaTime) +
                            (kv * seg.velocity + ka * seg.acceleration);
            lastError = error;
            heading = seg.heading;
            segment++;

            System.out.printf("p: % 3f v: % 3f a: % 3f%n", seg.position, seg.velocity, seg.acceleration);

            return calculatedValue;
        } else {
            return 0;
        }
    }

    public double getHeading() {
        return heading;
    }

    public MotionSegment getSegment() {
        return profile.getSegment(segment);
    }

    public boolean isFinished() {
        return segment >= profile.getLength();
    }
}
