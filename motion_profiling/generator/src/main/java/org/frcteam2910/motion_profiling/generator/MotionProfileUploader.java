package org.frcteam2910.motion_profiling.generator;

import org.apache.commons.net.ftp.FTPClient;
import org.frcteam2910.motion_profiling.MotionProfile;
import org.frcteam2910.motion_profiling.MotionProfileLoader;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MotionProfileUploader implements AutoCloseable {

    private FTPClient client = new FTPClient();

    public MotionProfileUploader() {
        try {
            client.connect("roborio-2910-frc.local");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        client.disconnect();
    }

    public void uploadProfile(String name, MotionProfile profile) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(client.storeFileStream(MotionProfileLoader.getPathOfProfile(name)))) {
            out.writeInt(profile.getLength());
            for (int i = 0; i < profile.getLength(); i++)
                out.writeObject(profile.getSegment(i));
        }
    }
}
