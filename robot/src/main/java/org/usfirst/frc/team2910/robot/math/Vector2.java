package org.usfirst.frc.team2910.robot.math;

public class Vector2 {
    public double x, y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public double getMagnitude() {
        return Math.hypot(x, y);
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public Vector2 add(Vector2 vector) {
        x += vector.x;
        y += vector.y;

        return this;
    }

    public Vector2 multiply(double scalar) {
        x *= scalar;
        y *= scalar;

        return this;
    }
}
