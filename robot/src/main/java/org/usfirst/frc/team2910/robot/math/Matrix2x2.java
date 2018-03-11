package org.usfirst.frc.team2910.robot.math;

public class Matrix2x2 {
    private double[][] rows = new double[2][2];

    public Matrix2x2() {
    }

    public Matrix2x2(double[][] rows) {
        assert rows.length == 2;
        for (double[] row : rows) assert row.length == 2;
        this.rows = rows;
    }

    public Vector2 multiply(Vector2 vector) {
        return new Vector2(vector.x * rows[0][0] + vector.y * rows[0][1],
                vector.x * rows[1][0] + vector.y * rows[1][1]);
    }
}
