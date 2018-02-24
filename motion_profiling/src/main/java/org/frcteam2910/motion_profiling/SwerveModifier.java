package org.frcteam2910.motion_profiling;

public class SwerveModifier {
    private MotionProfile source, frontLeft, frontRight, backLeft, backRight;
    private final double wheelbaseWidth, wheelbaseDepth;

    public SwerveModifier(MotionProfile source, double wheelbaseWidth, double wheelbaseDepth) {
        this.source = source;
        this.wheelbaseWidth = wheelbaseWidth;
        this.wheelbaseDepth = wheelbaseDepth;

        frontLeft = new MotionProfile(source.getLength());
        frontRight = new MotionProfile(source.getLength());
        backLeft = new MotionProfile(source.getLength());
        backRight = new MotionProfile(source.getLength());

        for (int i = 0; i < source.getLength(); i++) {
            frontLeft.setSegment(i, calculateFrontLeft(i));
            frontRight.setSegment(i, calculateFrontRight(i));
            backLeft.setSegment(i, calculateBackLeft(i));
            backRight.setSegment(i, calculateBackRight(i));
        }
    }

    private MotionSegment calculateFrontLeft(int i) {
        MotionSegment src = source.getSegment(i);
        MotionSegment dst = new MotionSegment(src);

        dst.x = src.x - wheelbaseWidth / 2;
        dst.y = src.y + wheelbaseDepth / 2;

        return dst;
    }

    private MotionSegment calculateFrontRight(int i) {
        MotionSegment src = source.getSegment(i);
        MotionSegment dst = new MotionSegment(src);

        dst.x = src.x + wheelbaseWidth / 2;
        dst.y = src.y + wheelbaseDepth / 2;

        return dst;
    }

    private MotionSegment calculateBackLeft(int i) {
        MotionSegment src = source.getSegment(i);
        MotionSegment dst = new MotionSegment(src);

        dst.x = src.x - wheelbaseWidth / 2;
        dst.y = src.y - wheelbaseDepth / 2;

        return dst;
    }

    private MotionSegment calculateBackRight(int i) {
        MotionSegment src = source.getSegment(i);
        MotionSegment dst = new MotionSegment(src);

        dst.x = src.x + wheelbaseWidth / 2;
        dst.y = src.y - wheelbaseDepth / 2;

        return dst;
    }

    public MotionProfile getFrontLeft() {
        return frontLeft;
    }

    public MotionProfile getFrontRight() {
        return frontRight;
    }

    public MotionProfile getBackLeft() {
        return backLeft;
    }

    public MotionProfile getBackRight() {
        return backRight;
    }

    public MotionProfile getSource() {
        return source;
    }
}
