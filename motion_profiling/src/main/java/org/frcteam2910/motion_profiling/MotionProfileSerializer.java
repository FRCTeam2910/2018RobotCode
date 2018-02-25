package org.frcteam2910.motion_profiling;

import com.google.gson.Gson;

import java.io.*;

public class MotionProfileSerializer {
    private static Gson gson = new Gson();

    public static void serialize(OutputStream out, MotionProfile[] profiles) throws IOException {
//        ObjectOutputStream oout = new ObjectOutputStream(out);
//
//        oout.writeObject(profile);

        String json = gson.toJson(profiles);
        out.write(json.getBytes());
    }

    public static MotionProfile[] deserialize(InputStream in) {
//        ObjectInputStream oin = new ObjectInputStream(in);
//
//        try {
//            return (MotionProfile) oin.readObject();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return null;
        return gson.fromJson(new InputStreamReader(in), MotionProfile[].class);
    }
}
