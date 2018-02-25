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
            Trajectory.Config.SAMPLES_HIGH, 0.04, 1.7, 2.0, 60.0);

    public static void main(String[] args) {
        Waypoint[] point = {
                new Waypoint(-4, -1, Pathfinder.d2r(-45)),
                new Waypoint(-2, -2, 0),
                new Waypoint(0, 0, 0)
        };

        Trajectory mainTrajectory = Pathfinder.generate(point, CONFIG);

        SwerveModifier modifier = new SwerveModifier(mainTrajectory);
        modifier.modify(14.5 / 12 * 3.28084,
                13.5  / 12 * 3.28084,
                SwerveModifier.Mode.SWERVE_DEFAULT);

        MotionProfileUploader uploader = new MotionProfileUploader();
        try {
            uploader.uploadProfile("test", new MotionProfile[] {
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
