package org.frcteam2910.motion_profiling.generator;

import jaci.pathfinder.Waypoint;

public class Point {
    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Waypoint toWaypoint(double angle) {
        return new Waypoint(x, y, angle);
    }
}
