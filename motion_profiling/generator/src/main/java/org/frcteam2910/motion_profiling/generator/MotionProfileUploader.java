package org.frcteam2910.motion_profiling.generator;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.frcteam2910.motion_profiling.MotionProfile;
import org.frcteam2910.motion_profiling.MotionProfileLoader;
import org.frcteam2910.motion_profiling.MotionProfileSerializer;
import org.frcteam2910.motion_profiling.MotionSegment;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MotionProfileUploader implements AutoCloseable {

    private FTPClient client = new FTPClient();

    public MotionProfileUploader() {
        FTPClientConfig config = new FTPClientConfig();
        client.configure(config);

        try {
            client.connect("roborio-2910-frc.local", 21);
            client.login("anonymous", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        client.disconnect();
    }

    public void uploadProfile(String name, MotionProfile[] profile) throws IOException {
        String path = MotionProfileLoader.getPathOfProfile(name);

        client.makeDirectory("/home/lvuser/motion_profiles");
        try (OutputStream stream = client.storeFileStream(path)) {
            MotionProfileSerializer.serialize(stream, profile);
            stream.flush();
        }
    }
}
