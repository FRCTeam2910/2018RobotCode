package org.usfirst.frc.team2910.robot.motion;

public class AutonomousPaths {
    private AutonomousPaths() {
    }

    //<editor-fold desc="Start to same scale forward">
    /**
     * Drive from the right starting position to the right scale scoring position.
     */
    public static final Path RIGHT_START_FORWARD_TO_RIGHT_SCALE_FRONT = new Path(
            -3.9499,
            new Segment.Line(140),
            new Segment.Arc(105.14, 40.1621),
            new Segment.Line(39.93)
    );

    /**
     * Drive from the left starting position to the left scale scoring position.
     */
    public static final Path LEFT_START_FORWARD_TO_LEFT_SCALE_FRONT = RIGHT_START_FORWARD_TO_RIGHT_SCALE_FRONT.flip();
    //</editor-fold>

    //<editor-fold desc="Start to opposite scale forward">
    /**
     * Drive from the right starting position to the left scale scoring position.
     */
    public static final Path RIGHT_START_FORWARD_TO_LEFT_SCALE_FRONT = new Path(
            -6.89781,
            new Segment.Line(163.52),
            new Segment.Arc(84.56, 96.8978),
            new Segment.Line(135.12),
            new Segment.Arc(73.30, -120)
    );

    /**
     * Drive from the left starting position to the right scale scoring position.
     */
    public static final Path LEFT_START_FORWARD_TO_RIGHT_SCALE_FRONT = RIGHT_START_FORWARD_TO_LEFT_SCALE_FRONT.flip();
    //</editor-fold>

    //<editor-fold desc="Start to same scale side">
    public static final Path RIGHT_START_TO_RIGHT_SCALE_SIDE = new Path(0); // TODO: Get path

    public static final Path LEFT_START_TO_LEFT_SCALE_SIDE = RIGHT_START_TO_RIGHT_SCALE_SIDE.flip();
    //</editor-fold>

    //<editor-fold desc="Start to opposite scale side">
    public static final Path RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_1 = new Path(0); // TODO: Get path
    public static final Path RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_2 = new Path(0); // TODO: Get path

    public static final Path LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_1 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_1.flip();
    public static final Path LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_2 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_2.flip();
    //</editor-fold>

    //<editor-fold desc="Start to switch front">
    public static final Path CENTER_START_TO_RIGHT_SWITCH = new Path(
            -19.81,
            new Segment.Line(113.06)
    );

    public static final Path CENTER_START_TO_LEFT_SWITCH = new Path(
            55.04,
            new Segment.Arc(133.76, -38.32)
    );
    //</editor-fold>

    //<editor-fold desc="Switch front second cube">
    public static final Path RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_1 = new Path(
            180,
            new Segment.Line(44),
            new Segment.Arc(90.52, -180),
            new Segment.Line(12)
    );
    public static final Path RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_2 = new Path(
            -90,
            new Segment.Line(24.64),
            new Segment.Arc(50.84, 90)
    );

    public static final Path LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_1 = RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_1.flip();
    public static final Path LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_2 = RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_2.flip();
    //</editor-fold>

    //<editor-fold desc="Scale front to scale">
    public static final Path RIGHT_SCALE_FRONT_TO_RIGHT_SCALE = new Path(-164.48,
            new Segment.Line(37.36));

    public static final Path LEFT_SCALE_FRONT_TO_LEFT_SCALE = RIGHT_SCALE_FRONT_TO_RIGHT_SCALE.flip();
    //</editor-fold>

    //<editor-fold desc="Grab 2nd cube from scale">
    public static final Path RIGHT_SCALE_TO_RIGHT_CUBE = new Path(133.75,
            new Segment.Arc(26.64, 46.25),
            new Segment.Line(10));
    public static final Path RIGHT_SCALE_TO_LEFT_CUBE = new Path(0); // TODO: Get path

    public static final Path LEFT_SCALE_TO_RIGHT_CUBE = RIGHT_SCALE_TO_LEFT_CUBE.flip();
    public static final Path LEFT_SCALE_TO_LEFT_CUBE = RIGHT_SCALE_TO_RIGHT_CUBE.flip();
    //</editor-fold>

    //<editor-fold desc="2nd cube to scale">
    public static final Path RIGHT_CUBE_TO_RIGHT_SCALE = new Path(-16.74,
            new Segment.Line(35.34));

    public static final Path LEFT_CUBE_TO_LEFT_SCALE = RIGHT_CUBE_TO_RIGHT_SCALE.flip();
    //</editor-fold>

    //<editor-fold desc="Scale to scale front">
    public static final Path RIGHT_SCALE_TO_RIGHT_SCALE_FRONT = new Path(15.52,
            new Segment.Line(37.36));

    public static final Path LEFT_SCALE_TO_LEFT_SCALE_FRONT = RIGHT_SCALE_TO_RIGHT_SCALE_FRONT.flip();
    //</editor-fold>

    //<editor-fold desc="Scale to switch back">
    public static final Path RIGHT_SCALE_TO_RIGHT_SWITCH_BACK = new Path(133.75,
            new Segment.Arc(26.64, 46.25),
            new Segment.Line(20));
    public static final Path RIGHT_SCALE_TO_LEFT_SWITCH_BACK = new Path(154.11,
            new Segment.Arc(36.19, -69.11),
            new Segment.Line(101.9),
            new Segment.Arc(29.85, 95),
            new Segment.Line(18));

    public static final Path LEFT_SCALE_TO_LEFT_SWITCH_BACK = RIGHT_SCALE_TO_RIGHT_SWITCH_BACK.flip();
    public static final Path LEFT_SCALE_TO_RIGHT_SWITCH_BACK = RIGHT_SCALE_TO_LEFT_SWITCH_BACK.flip();
    //</editor-fold>

    //<editor-fold desc="Start to switch side">
    public static final Path RIGHT_START_TO_RIGHT_SWITCH = new Path(2.42,
            new Segment.Line(151.32));

    public static final Path RIGHT_START_TO_LEFT_SWITCH = new Path(-6,
            new Segment.Line(164.68),
            new Segment.Arc(83.78, 96),
            new Segment.Line(167.42),
            new Segment.Arc(76.79, 110),
            new Segment.Line(15.04));
    public static final Path RIGHT_SWITCH_TO_RIGHT_SWITCH_SIDE = new Path(90,
            new Segment.Line(30.81));

    public static final Path LEFT_START_TO_LEFT_SWITCH = RIGHT_START_TO_RIGHT_SWITCH.flip();
    public static final Path LEFT_START_TO_RIGHT_SWITCH = RIGHT_START_TO_LEFT_SWITCH.flip();
    public static final Path LEFT_SWITCH_TO_LEFT_SWITCH_SIDE = RIGHT_SWITCH_TO_RIGHT_SWITCH_SIDE.flip();
    //</editor-fold>
}