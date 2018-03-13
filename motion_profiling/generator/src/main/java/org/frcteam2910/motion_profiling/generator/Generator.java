package org.frcteam2910.motion_profiling.generator;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.SwerveModifier;
import org.frcteam2910.motion_profiling.MotionProfile;
import org.frcteam2910.motion_profiling.MotionProfileLoader;
import org.frcteam2910.motion_profiling.MotionProfileSerializer;
import org.frcteam2910.motion_profiling.MotionSegment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Generator {


    public static final Trajectory.Config CONFIG = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_HIGH, 0.02, 200, 150, 600.0);

    public static void main(String[] args) {
        Waypoint[] point = {
                new Waypoint(0, 0, 0),
                new Waypoint(5 * 12, 5 * 12, 0),
                new Waypoint(10 * 12, 0, Math.toRadians(90)),
                new Waypoint(5 * 12, -5 * 12, Math.toRadians(180)),
                new Waypoint(0, 0, Math.toRadians(-90))
        };


        Trajectory mainTrajectory = Pathfinder.generate(point, CONFIG);

        SwerveModifier modifier = new SwerveModifier(mainTrajectory);
        modifier.modify(14.5 / 12,
                13.5 / 12,
                SwerveModifier.Mode.SWERVE_DEFAULT);

        MotionProfileUploader uploader = new MotionProfileUploader();
        try {
            uploader.uploadProfile("test", new MotionProfile[]{
                    convert(modifier.getFrontLeftTrajectory()),
                    convert(modifier.getFrontRightTrajectory()),
                    convert(modifier.getBackLeftTrajectory()),
                    convert(modifier.getBackRightTrajectory())
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MotionProfile convert(Trajectory trajectory) {
        MotionProfile profile = new MotionProfile(trajectory.length());

        for (int i = 0; i < profile.getLength(); i++) {
            Trajectory.Segment jaciSegment = trajectory.get(i);

            profile.setSegment(i, new MotionSegment(
                    jaciSegment.dt,
                    jaciSegment.x,
                    jaciSegment.y,
                    jaciSegment.position,
                    jaciSegment.velocity,
                    jaciSegment.acceleration,
                    jaciSegment.jerk,
                    jaciSegment.heading
            ));
        }

        return profile;
    }
}
