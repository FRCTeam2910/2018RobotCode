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
            -5.2938,
            new Segment.Line(159.81),
            new Segment.Arc(102.93, 98.2938),
            new Segment.Line(118.31),
            new Segment.Arc(75.14, -123)
    );

    /**
     * Drive from the left starting position to the right scale scoring position.
     */
    public static final Path LEFT_START_FORWARD_TO_RIGHT_SCALE_FRONT = RIGHT_START_FORWARD_TO_LEFT_SCALE_FRONT.flip();
    //</editor-fold>

    //<editor-fold desc="Start to same scale side">
    public static final Path RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_1 = new Path(2.5,
            new Segment.Line(308.48));
    public static final Path RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_2 = new Path(90,
            new Segment.Line(23.19));
    public static final Path RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_3 = new Path(-90,
            new Segment.Line(49.95));

    public static final Path LEFT_START_TO_LEFT_SCALE_SIDE_STEP_1 = RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_1.flip();
    public static final Path LEFT_START_TO_LEFT_SCALE_SIDE_STEP_2 = RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_2.flip();
    public static final Path LEFT_START_TO_LEFT_SCALE_SIDE_STEP_3 = RIGHT_START_TO_RIGHT_SCALE_SIDE_STEP_3.flip();
    //</editor-fold>

    //<editor-fold desc="Start to opposite scale side">
    public static final Path RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_1 = new Path(0,
            new Segment.Line(11));
    public static final Path RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_2 = new Path(91,
            new Segment.Line(167.83),
            new Segment.Arc(111.26, -85),
            new Segment.Line(234.25));
    public static final Path RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_3 = new Path(-90,
            new Segment.Line(42.95));
    public static final Path RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_4 = new Path(90,
            new Segment.Line(50.95));

    public static final Path LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_1 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_1.flip();
    public static final Path LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_2 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_2.flip();
    public static final Path LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_3 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_3.flip();
    public static final Path LEFT_START_TO_RIGHT_SCALE_SIDE_STEP_4 = RIGHT_START_TO_LEFT_SCALE_SIDE_STEP_4.flip();
    //</editor-fold>

    //<editor-fold desc="Start to switch front">
    public static final Path CENTER_START_TO_RIGHT_SWITCH = new Path(
            -43.36,
            new Segment.Arc(106.23, 43.36),
            new Segment.Line(18)
    );

    public static final Path CENTER_START_TO_LEFT_SWITCH = new Path(
            74.94,
            new Segment.Arc(135.96, -74.94),
            new Segment.Line(14)
    );
    //</editor-fold>

    //<editor-fold desc="Switch front second cube">
    public static final Path RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_1 = new Path(
            180,
            new Segment.Line(41),
            new Segment.Arc(90.52, -180),
            new Segment.Line(35)
    );
    public static final Path RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_2 = new Path(
            -107.35,
            new Segment.Line(26.37),
            new Segment.Arc(46.84, 107.35),
            new Segment.Line(20)
    );
    public static final Path RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_3 = new Path(
            -100,
            new Segment.Line(33.33),
            new Segment.Arc(47.12, 90),
            new Segment.Line(43.11)
    );

    public static final Path LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_1 = RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_1.flip();
    public static final Path LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_2 = RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_2.flip();
    public static final Path LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_3 = RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_3.flip();
    //</editor-fold>

    //<editor-fold desc="Scale front to scale">
    public static final Path RIGHT_SCALE_FRONT_TO_RIGHT_SCALE = new Path(-164.48,
            new Segment.Line(37.36));

    public static final Path LEFT_SCALE_FRONT_TO_LEFT_SCALE = RIGHT_SCALE_FRONT_TO_RIGHT_SCALE.flip();
    //</editor-fold>

    //<editor-fold desc="Grab 2nd cube from scale">
    public static final Path RIGHT_SCALE_TO_RIGHT_CUBE = new Path(180,
            new Segment.Line(42));
    public static final Path RIGHT_SCALE_TO_LEFT_SCALE = new Path(132.37,
            new Segment.Arc(22.19, -42.37),
            new Segment.Line(126.46)); // TODO: Get path

    public static final Path LEFT_SCALE_TO_RIGHT_SCALE = RIGHT_SCALE_TO_LEFT_SCALE.flip();
    public static final Path LEFT_SCALE_TO_LEFT_CUBE = RIGHT_SCALE_TO_RIGHT_CUBE.flip();
    //</editor-fold>

    //<editor-fold desc="2nd cube to scale">
    public static final Path RIGHT_CUBE_TO_RIGHT_SCALE = new Path(-33.69,
            new Segment.Line(43.27));
    public static final Path RIGHT_SCALE_SECOND_CUBE_STEP_3 = new Path(
            9.48,
            new Segment.Line(35.32)
    );

    public static final Path LEFT_CUBE_TO_LEFT_SCALE = RIGHT_CUBE_TO_RIGHT_SCALE.flip();
    //</editor-fold>

    //<editor-fold desc="Scale to scale front">
    public static final Path RIGHT_SCALE_TO_RIGHT_SCALE_FRONT = new Path(15.52,
            new Segment.Line(44.36));

    public static final Path LEFT_SCALE_TO_LEFT_SCALE_FRONT = RIGHT_SCALE_TO_RIGHT_SCALE_FRONT.flip();
    //</editor-fold>

    //<editor-fold desc="Scale to switch back">
    public static final Path RIGHT_SCALE_TO_RIGHT_SWITCH_BACK = new Path(133.75,
            new Segment.Arc(26.64, 46.25),
            new Segment.Line(20));
    public static final Path RIGHT_SCALE_TO_LEFT_SWITCH_BACK = new Path(142.65,
            new Segment.Arc(30.19, -57.65),
            new Segment.Line(113.05),
            new Segment.Arc(29.85, 95),
            new Segment.Line(28.38));

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