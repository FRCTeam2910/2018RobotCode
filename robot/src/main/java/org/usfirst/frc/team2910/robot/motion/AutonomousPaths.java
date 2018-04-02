package org.usfirst.frc.team2910.robot.motion;

public class AutonomousPaths {
	private AutonomousPaths() {}

	//<editor-fold desc="Start to same scale forward">
	/**
	 * Drive from the right starting position to the right scale scoring position.
	 */
	public static final Path RIGHT_START_TO_RIGHT_SCALE_FORWARD = new Path(
			3.9499,
			new Segment.Line(140),
			new Segment.Arc(75.21, 28.7266),
			new Segment.Line(63.85)
	);

	/**
	 * Drive from the left starting position to the left scale scoring position.
	 */
	public static final Path LEFT_START_TO_LEFT_SCALE_FORWARD = RIGHT_START_TO_RIGHT_SCALE_FORWARD.flip();
	//</editor-fold>

	//<editor-fold desc="Start to opposite scale forward">
	/**
	 * Drive from the right starting position to the left scale scoring position.
	 */
	public static final Path RIGHT_START_TO_LEFT_SCALE_FORWARD = new Path(
			3.9644,
			new Segment.Line(171.84),
			new Segment.Arc(84.62, 96.9644),
			new Segment.Line(122.84),
			new Segment.Arc(75.14, 123)
	);

	/**
	 * Drive from the left starting position to the right scale scoring position.
	 */
	public static final Path LEFT_START_TO_RIGHT_SCALE_FORWARD = RIGHT_START_TO_LEFT_SCALE_FORWARD.flip();
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

	//<editor-fold desc="Scale forward to same cube">
	public static final Path RIGHT_SCALE_FORWARD_TO_SAME_CUBE_STEP_1 = new Path(
			164.05,
			new Segment.Line(35)
	);
	public static final Path RIGHT_SCALE_FORWARD_TO_SAME_CUBE_STEP_2 = new Path(
			83.53,
			new Segment.Arc(36.44, 83.53),
			new Segment.Line(10)
	);

	public static final Path LEFT_SCALE_FORWARD_TO_SAME_CUBE_STEP_1 = RIGHT_SCALE_FORWARD_TO_SAME_CUBE_STEP_1.flip();
	public static final Path LEFT_SCALE_FORWARD_TO_SAME_CUBE_STEP_2 = RIGHT_SCALE_FORWARD_TO_SAME_CUBE_STEP_2.flip();
	//</editor-fold>

	//<editor-fold desc="Scale side to same cube">
	public static final Path RIGHT_SCALE_SIDE_TO_SAME_CUBE_STEP_1 = new Path(0); // TODO: Get path
	public static final Path RIGHT_SCALE_SIDE_TO_SAME_CUBE_STEP_2 = new Path(0); // TODO: Get path

	public static final Path LEFT_SCALE_SIDE_TO_SAME_CUBE_STEP_1 = RIGHT_SCALE_SIDE_TO_SAME_CUBE_STEP_1.flip();
	public static final Path LEFT_SCALE_SIDE_TO_SAME_CUBE_STEP_2 = RIGHT_SCALE_SIDE_TO_SAME_CUBE_STEP_2.flip();
	//</editor-fold>

	//<editor-fold desc="Cube to scale forward">
	public static final Path RIGHT_CUBE_TO_RIGHT_SCALE_FORWARD_STEP_1 = new Path(
			212.48,
			new Segment.Line(41.3)
	);
	public static final Path RIGHT_CUBE_TO_RIGHT_SCALE_FORWARD_STEP_2 = new Path(
			15.95,
			new Segment.Line(36.4)
	);

	public static final Path LEFT_CUBE_TO_LEFT_SCALE_FORWARD_STEP_1 = RIGHT_CUBE_TO_RIGHT_SCALE_FORWARD_STEP_1.flip();
	public static final Path LEFT_CUBE_TO_LEFT_SCALE_FORWARD_STEP_2 = RIGHT_CUBE_TO_RIGHT_SCALE_FORWARD_STEP_2.flip();
	//</editor-fold>

	//<editor-fold desc="Start to switch front">
	public static final Path CENTER_START_TO_RIGHT_SWITCH = new Path(
			19.81,
			new Segment.Line(113.06)
	);

	public static final Path CENTER_START_TO_LEFT_SWITCH = new Path(
			55.04,
			new Segment.Arc(133.76, 38.32)
	);
	//</editor-fold>

	//<editor-fold desc="Switch front second cube">
	public static final Path RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_1 = new Path(
			180,
			new Segment.Line(44),
			new Segment.Arc(90.52, 180),
			new Segment.Line(12)
	);
	public static final Path RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_2 = new Path(
			90,
			new Segment.Line(24.64),
			new Segment.Arc(50.84, 90)
	);

	public static final Path LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_1 = RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_1.flip();
	public static final Path LEFT_SWITCH_FRONT_SECOND_CUBE_STEP_2 = RIGHT_SWITCH_FRONT_SECOND_CUBE_STEP_2.flip();
	//</editor-fold>
}