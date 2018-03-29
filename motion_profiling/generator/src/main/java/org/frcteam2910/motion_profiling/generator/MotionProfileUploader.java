package org.frcteam2910.motion_profiling.generator;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.frcteam2910.motion_profiling.MotionProfile;
import org.frcteam2910.motion_profiling.MotionProfileLoader;
import org.frcteam2910.motion_profiling.MotionProfileSerializer;
import org.frcteam2910.motion_profiling.MotionSegment;

import java.io.*;

public class MotionProfileUploader implements AutoCloseable {

    private FTPClient client = new FTPClient();

    public MotionProfileUploader() throws IOException {
        FTPClientConfig config = new FTPClientConfig();
        client.configure(config);

        client.connect("roborio-2910-frc.local", 21);
        client.login("anonymous", "");
    }

    @Override
    public void close() throws IOException {
        client.disconnect();
    }

    public void uploadProfile(String name, MotionProfile[] profiles) throws IOException {
        String path = MotionProfileLoader.getPathOfProfile(name);

        client.makeDirectory("/home/lvuser/motion_profiles");
        try (OutputStream stream = client.storeFileStream(path)) {
            MotionProfileSerializer.serialize(stream, profiles);
            stream.flush();
        }
    }
}
