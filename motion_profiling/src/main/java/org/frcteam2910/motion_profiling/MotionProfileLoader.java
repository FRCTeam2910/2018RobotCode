package org.frcteam2910.motion_profiling;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MotionProfileLoader {

    public static String getPathOfProfile(String name) {
        return String.format("/home/lvuser/motion_profiles/%s.motionprofile", name);
    }

    public static List<MotionProfile> loadProfile(String name) throws IOException {
        String path = getPathOfProfile(name);

        List<MotionProfile> profiles = new ArrayList<>();

        try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(new File(path)))) {
            int profileCount = oin.readInt();

            for (int i = 0; i < profileCount; i++) {
                int profileLength = oin.readInt();
                MotionProfile profile = new MotionProfile(profileLength);
                for (int j = 0; j < profileLength; j++)
                    profile.setSegment(j, (MotionSegment) oin.readObject());
                profiles.add(profile);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return profiles;
    }
}
