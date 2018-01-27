package org.usfirst.frc.team2910.robot.util;

public enum Side {
    LEFT,
    RIGHT;

    public static Side fromChar(char c) {
        switch (c) {
            case 'L':
                return Side.LEFT;
            case 'R':
                return Side.RIGHT;
        }

        return null;
    }

    public Side opposite() {
        return values()[(ordinal() + 1) % 2];
    }
}
