package org.frcteam2910.motion_profiling;

public class MotionProfileLoader {

    public static String getPathOfProfile(String name) {
        return String.format("/home/lvuser/motion_profiles/%s.motionprofile", name);
    }
}
