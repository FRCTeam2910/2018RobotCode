package org.frcteam2910.motion_profiling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MotionProfile implements Serializable {
    private static final long serialVersionUID = -4970557251825378042L;

    private MotionSegment[] segments;

    public MotionProfile(int length) {
        this(new MotionSegment[length]);
    }

    public MotionProfile(MotionSegment[] segments) {
        this.segments = segments;
    }

    public MotionSegment[] getSegments() {
        return segments;
    }

    public MotionSegment getSegment(int i) {
        return segments[i];
    }

    public void setSegment(int i, MotionSegment segment) {
        segments[i] = segment;
    }

    public int getLength() {
        return segments.length;
    }
}
