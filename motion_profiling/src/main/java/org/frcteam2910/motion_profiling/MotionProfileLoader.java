package org.frcteam2910.motion_profiling;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MotionProfileLoader {

    public static String getPathOfProfile(String name) {
        return String.format("/home/lvuser/motion_profiles/%s.motionprofile", name);
    }

    public static List<MotionProfile> loadProfile(String name) throws IOException {
        try (InputStream in = new FileInputStream(new File(getPathOfProfile(name)))) {
            return Arrays.asList(MotionProfileSerializer.deserialize(in));
        }
    }
}
