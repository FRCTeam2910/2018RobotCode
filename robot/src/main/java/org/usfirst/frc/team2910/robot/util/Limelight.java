package org.usfirst.frc.team2910.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    private final NetworkTable limelightTable;

    private final NetworkTableEntry hasValidTargetsEntry;
    private final NetworkTableEntry horizOffsetEntry;
    private final NetworkTableEntry vertOffsetEntry;
    private final NetworkTableEntry targetAreaEntry;
    private final NetworkTableEntry skewEntry;
    private final NetworkTableEntry latencyEntry;

    private final NetworkTableEntry ledModeEntry;
    private final NetworkTableEntry camModeEntry;
    private final NetworkTableEntry pipelineEntry;
    private final NetworkTableEntry streamEntry;
    private final NetworkTableEntry snapshotEntry;

    public Limelight() {
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

        hasValidTargetsEntry = limelightTable.getEntry("tv");
        horizOffsetEntry = limelightTable.getEntry("tx");
        vertOffsetEntry = limelightTable.getEntry("ty");
        targetAreaEntry = limelightTable.getEntry("ta");
        skewEntry = limelightTable.getEntry("ts");
        latencyEntry = limelightTable.getEntry("tl");

        ledModeEntry = limelightTable.getEntry("ledMode");
        camModeEntry = limelightTable.getEntry("camMode");
        pipelineEntry = limelightTable.getEntry("pipeline");
        streamEntry = limelightTable.getEntry("stream");
        snapshotEntry = limelightTable.getEntry("snapshot");
    }

    public boolean hasTargets() {
        return hasValidTargetsEntry.getBoolean(false);
    }

    public double getHorizontalOffset() {
        return horizOffsetEntry.getDouble(0);
    }

    public double getVerticalOffset() {
        return vertOffsetEntry.getDouble(0);
    }

    public double getArea() {
        return targetAreaEntry.getDouble(0);
    }

    public double getSkew() {
        return skewEntry.getDouble(0);
    }

    public double getLatency() {
        // 11 is added to account for image capture latency
        return latencyEntry.getDouble(0) + 11;
    }

    public void setCameraMode(CameraMode mode) {
        camModeEntry.setNumber(mode.getValue());
    }

    public void setLedMode(LedMode mode) {
        ledModeEntry.setNumber(mode.getValue());
    }

    public void setPipeline(int pipeline) {
        pipelineEntry.setNumber(pipeline);
    }

    public void setStreamingMode(StreamingMode mode) {
        streamEntry.setNumber(mode.getValue());
    }

    public void enableSnapshots() {
        snapshotEntry.setNumber(1);
    }

    public void disableSnapshots() {
        snapshotEntry.setNumber(0);
    }

    public enum CameraMode {
        VISON(0),
        DRIVER(1);

        private final int value;

        CameraMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum LedMode {
        ON(0),
        OFF(1),
        BLINK(2);

        private final int value;


        LedMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum StreamingMode {
        STANDARD(0),
        PIP_MAIN(1),
        PIP_SECONDARY(2);

        private final int value;

        StreamingMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
