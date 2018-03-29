package org.frcteam2910.motion_profiling.generator;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.SwerveModifier;
import org.frcteam2910.motion_profiling.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.frcteam2910.motion_profiling.Field.*;

public class Generator {
    public static final double ROBOT_LENGTH = 19;
    public static final double ROBOT_DEPTH = 20;

    public static final Trajectory.Config CONFIG = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_HIGH, 0.02, 200, 150, 600.0);

    public static final Point START_LEFT = new Point(ROBOT_DEPTH / 2, WIDTH - 30 - ROBOT_LENGTH / 2);
    public static final Point START_RIGHT = new Point(ROBOT_DEPTH / 2, 30 + ROBOT_LENGTH / 2);
    public static final Point START_CENTER = new Point(ROBOT_DEPTH / 2, WIDTH / 2);
    
    public static final Point SWITCH_LEFT_CENTER = new Point(WALL_TO_SWITCH, WIDTH / 2 + SWITCH_LENGTH / 2 + 12);
    public static final Point SWITCH_RIGHT_CENTER = new Point(WALL_TO_SWITCH, WIDTH / 2 - SWITCH_LENGTH / 2 - 12);
    
    public static final Point SWITCH_RIGHT_STAGE_1 = new Point(WALL_TO_SWITCH + SWITCH_DEPTH / 2, WIDTH / 2 - SWITCH_LENGTH / 2 + ROBOT_DEPTH / 2);
    public static final Point SWITCH_LEFT_STAGE_1 = new Point(WALL_TO_SWITCH + SWITCH_DEPTH / 2, WIDTH / 2 + SWITCH_LENGTH / 2 + ROBOT_DEPTH / 2);

    public static final Point SCALE_RIGHT = new Point(WALL_TO_SCALE - ROBOT_DEPTH / 2, WIDTH / 2 - SCALE_LENGTH / 2);
    public static final Point SCALE_LEFT = new Point(WALL_TO_SCALE - ROBOT_DEPTH / 2, WIDTH / 2 + SCALE_LENGTH / 2);
    
    public static final Point SWITCH_LEFT_STAGE_2 = new Point(SWITCH_LEFT_CENTER.x, SWITCH_LEFT_CENTER.y + SWITCH_DEPTH);
    public static final Point SWITCH_RIGHT_STAGE_2 = new Point(SWITCH_RIGHT_CENTER.x, SWITCH_RIGHT_CENTER.y + SWITCH_DEPTH);

    public static final Point RIGHT_TURN_POINT = new Point(WALL_TO_PLATFORM_ZONE, SWITCH_RIGHT_STAGE_2.y);
    public static final Point LEFT_TURN_POINT = new Point(WALL_TO_PLATFORM_ZONE, SWITCH_LEFT_STAGE_2.y);

    public static final Point RIGHT_SWITCH_TURN = new Point(SWITCH_RIGHT_STAGE_1.x, SWITCH_RIGHT_STAGE_1.y - 12);
    public static final Point LEFT_SWITCH_TURN = new Point(SWITCH_LEFT_STAGE_1.x, SWITCH_LEFT_STAGE_1.y + 12);
    
    private static final Waypoint[] RIGHT_SAME_SCALE = {
            new Waypoint(START_RIGHT.x, START_RIGHT.y, 0),
            new Waypoint(SCALE_RIGHT.x, SCALE_RIGHT.y, 0)
    };

    private static final Waypoint[] LEFT_SAME_SCALE = {
            new Waypoint(START_LEFT.x, START_LEFT.y, 0),
            new Waypoint(SCALE_LEFT.x, SCALE_LEFT.y, 0)
    };
    
    private static final Waypoint[] RIGHT_SAME_SWITCH_TURN = {
            new Waypoint(START_RIGHT.x, START_RIGHT.y, 0),
            new Waypoint(RIGHT_SWITCH_TURN.x, RIGHT_SWITCH_TURN.y, 0)
    };
    
    private static final Waypoint[] RIGHT_SWITCH_TURN_RIGHT_SWITCH = {
            new Waypoint(RIGHT_SWITCH_TURN.x, RIGHT_SWITCH_TURN.y, Math.toRadians(90)),
            new Waypoint(SWITCH_RIGHT_STAGE_1.x, SWITCH_RIGHT_STAGE_1.y, Math.toRadians(90))
    };

    private static final Waypoint[] LEFT_SAME_SWITCH_TURN = {
            new Waypoint(START_LEFT.x, START_LEFT.y, 0),
            new Waypoint(LEFT_SWITCH_TURN.x, LEFT_SWITCH_TURN.y, 0)
    };

    private static final Waypoint[] LEFT_SWITCH_TURN_LEFT_SWITCH = {
            new Waypoint(LEFT_SWITCH_TURN.x, LEFT_SWITCH_TURN.y, Math.toRadians(-90)),
            new Waypoint(SWITCH_LEFT_STAGE_1.x, SWITCH_LEFT_STAGE_1.y, Math.toRadians(-90))
    };
    
    private static final Waypoint[] CENTER_RIGHT_SWITCH = {
            new Waypoint(START_CENTER.x, START_CENTER.y, 0),
            new Waypoint(SWITCH_RIGHT_CENTER.x, SWITCH_RIGHT_CENTER.y, 0)
    };
    
    private static final Waypoint[] CENTER_LEFT_SWITCH = {
            new Waypoint(START_CENTER.x, START_CENTER.y, 0),
            new Waypoint(SWITCH_LEFT_CENTER.x, SWITCH_LEFT_CENTER.y, 0)
    };
    
    private static final Waypoint[] RIGHT_SCALE_RIGHT_TURN = {
            new Waypoint(SCALE_RIGHT.x, SCALE_RIGHT.y, Math.toRadians(180)),
            new Waypoint(RIGHT_TURN_POINT.x, RIGHT_TURN_POINT.y, Math.toRadians(180))
    };

    private static final Waypoint[] LEFT_SCALE_LEFT_TURN = {
            new Waypoint(SCALE_LEFT.x, SCALE_LEFT.y, Math.toRadians(180)),
            new Waypoint(LEFT_TURN_POINT.x, LEFT_TURN_POINT.y, Math.toRadians(180))
    };
    
    private static final Waypoint[] RIGHT_SWITCH_RIGHT_TURN = {
            new Waypoint(SWITCH_LEFT_STAGE_1.x, SWITCH_LEFT_STAGE_1.y, Math.toRadians(-90)),
            new Waypoint(RIGHT_TURN_POINT.x, RIGHT_TURN_POINT.y, Math.toRadians(90))
    };

    private static final Waypoint[] LEFT_SWITCH_LEFT_TURN = {
            new Waypoint(SWITCH_LEFT_STAGE_1.x, SWITCH_LEFT_STAGE_1.y, Math.toRadians(90)),
            new Waypoint(LEFT_TURN_POINT.x, LEFT_TURN_POINT.y, Math.toRadians(-90))
    };

    private static final Waypoint[] RIGHT_OPPOSITE_TURN = {
            new Waypoint(START_RIGHT.x, START_RIGHT.y, 0),
            new Waypoint(RIGHT_TURN_POINT.x, RIGHT_TURN_POINT.y, Math.toRadians(90)),
            new Waypoint(LEFT_TURN_POINT.x, LEFT_TURN_POINT.y, 0),
            new Waypoint(SCALE_LEFT.x, SCALE_LEFT.y, 0)
    };

    private static final Waypoint[] LEFT_OPPOSITE_TURN = {
            new Waypoint(START_LEFT.x, START_LEFT.y, 0),
            new Waypoint(LEFT_TURN_POINT.x, LEFT_TURN_POINT.y, Math.toRadians(-90)),
            new Waypoint(RIGHT_TURN_POINT.x, RIGHT_TURN_POINT.y, 0),
            new Waypoint(SCALE_RIGHT.x, SCALE_RIGHT.y, 0)
    };

    private static final Waypoint[] RIGHT_OPPOSITE_SWITCH_TURN = {
            new Waypoint(START_RIGHT.x, START_RIGHT.y, 0),
            new Waypoint(RIGHT_TURN_POINT.x, RIGHT_TURN_POINT.y, Math.toRadians(90)),
            new Waypoint(LEFT_TURN_POINT.x, LEFT_TURN_POINT.y, Math.toRadians(90)),
            new Waypoint(LEFT_SWITCH_TURN.x, LEFT_SWITCH_TURN.y, Math.toRadians(-90))
    };

    private static final Waypoint[] LEFT_OPPOSITE_SWITCH_TURN = {
            new Waypoint(START_LEFT.x, START_LEFT.y, 0),
            new Waypoint(LEFT_TURN_POINT.x, LEFT_TURN_POINT.y, Math.toRadians(-90)),
            new Waypoint(RIGHT_TURN_POINT.x, RIGHT_TURN_POINT.y, Math.toRadians(-90)),
            new Waypoint(RIGHT_SWITCH_TURN.x, RIGHT_SWITCH_TURN.y, Math.toRadians(90))
    };

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(1);

        pool.submit(() -> upload("right_same_scale", RIGHT_SAME_SCALE));
        pool.submit(() -> upload("left_same_scale", LEFT_SAME_SCALE));
        pool.submit(() -> upload("center_right_switch", CENTER_RIGHT_SWITCH));
        pool.submit(() -> upload("center_left_switch", CENTER_LEFT_SWITCH));
        pool.submit(() -> upload("right_scale_right_turn", RIGHT_SCALE_RIGHT_TURN));
        pool.submit(() -> upload("left_scale_left_turn", LEFT_SCALE_LEFT_TURN));
        pool.submit(() -> upload("right_same_switch_turn", RIGHT_SAME_SWITCH_TURN));
        pool.submit(() -> upload("left_same_switch_turn", LEFT_SAME_SWITCH_TURN));
        pool.submit(() -> upload("right_switch_turn_right_switch", RIGHT_SWITCH_TURN_RIGHT_SWITCH));
        pool.submit(() -> upload("left_switch_turn_left_switch", LEFT_SWITCH_TURN_LEFT_SWITCH));
        pool.submit(() -> upload("right_switch_right_turn", RIGHT_SWITCH_RIGHT_TURN));
        pool.submit(() -> upload("left_switch_left_turn", LEFT_SWITCH_LEFT_TURN));
        pool.submit(() -> upload("right_opposite_scale", RIGHT_OPPOSITE_TURN));
        pool.submit(() -> upload("left_opposite_scale", LEFT_OPPOSITE_TURN));
        pool.submit(() -> upload("right_opposite_switch_turn", RIGHT_OPPOSITE_SWITCH_TURN));
        pool.submit(() -> upload("left_opposite_switch_turn", LEFT_OPPOSITE_SWITCH_TURN));

        pool.shutdown();
    }

    private static void upload(String name, Waypoint[] waypoints) {
        System.out.printf("Generating path for '%s'%n", name);
        for (int i = 0; i < waypoints.length; i++)
            System.out.printf("\t(%.3f, %.3f)%n", waypoints[i].x, waypoints[i].y);
        Trajectory mainTrajectory = Pathfinder.generate(waypoints, CONFIG);

        SwerveModifier modifier = new SwerveModifier(mainTrajectory);
        modifier.modify(14.5 / 12,
                13.5 / 12,
                SwerveModifier.Mode.SWERVE_DEFAULT);

        System.out.printf("Uploading '%s' to robot.%n", name);
        try (MotionProfileUploader uploader = new MotionProfileUploader()) {
            uploader.uploadProfile(name, new MotionProfile[]{
                    convert(modifier.getFrontLeftTrajectory()),
                    convert(modifier.getFrontRightTrajectory()),
                    convert(modifier.getBackLeftTrajectory()),
                    convert(modifier.getBackRightTrajectory())
            });
            System.out.printf("Finished uploading '%s' to robot.%n", name);
        } catch (IOException e) {
            System.err.printf("Failed to upload '%s' to robot: %s%n", name, e.getMessage());
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
