package org.frcteam2910.motion_profiling;

public class TankModifier {
    private final MotionProfile source, left, right;
    private final double halfWheelbaseWidth;

    public TankModifier(MotionProfile source, double wheelbaseWidth) {
        this.source = source;
        this.left = new MotionProfile(source.getLength());
        this.right = new MotionProfile(source.getLength());
        this.halfWheelbaseWidth = wheelbaseWidth / 2;

        for (int i = 0; i < source.getLength(); i++) {
            left.setSegment(i, modifyLeft(i));
            right.setSegment(i, modifyRight(i));
        }
    }

    private MotionSegment modifyLeft(int i) {
        MotionSegment src = source.getSegment(i);
        MotionSegment dst = new MotionSegment(src);

        double cosAngle = Math.cos(src.heading);
        double sinAngle = Math.cos(src.heading);

        dst.x = src.x - (halfWheelbaseWidth * sinAngle);
        dst.y = src.y + (halfWheelbaseWidth * cosAngle);

        if (i > 0) {
            MotionSegment last = left.getSegment(i - 1);

            double distance = Math.hypot(dst.x - last.x, dst.y - last.y);

            dst.position = last.position + distance;
            dst.velocity = distance / src.deltaTime;
            dst.acceleration = (dst.velocity - last.velocity) / src.deltaTime;
            dst.jerk = (dst.acceleration - last.acceleration) / src.deltaTime;
        }

        return dst;
    }

    private MotionSegment modifyRight(int i) {
        MotionSegment src = source.getSegment(i);
        MotionSegment dst = new MotionSegment(src);

        double cosAngle = Math.cos(src.heading);
        double sinAngle = Math.cos(src.heading);

        dst.x = src.x + (halfWheelbaseWidth * sinAngle);
        dst.y = src.y - (halfWheelbaseWidth * cosAngle);

        if (i > 0) {
            MotionSegment last = right.getSegment(i - 1);

            double distance = Math.hypot(dst.x - last.x, dst.y - last.y);

            dst.position = last.position + distance;
            dst.velocity = distance / src.deltaTime;
            dst.acceleration = (dst.velocity - last.velocity) / src.deltaTime;
            dst.jerk = (dst.acceleration - last.acceleration) / src.deltaTime;
        }

        return dst;
    }
}
