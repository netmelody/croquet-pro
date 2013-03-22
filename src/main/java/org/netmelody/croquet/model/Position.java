package com.scarytom.collider.model;

public final class Position {
    public final float x;
    public final float y;

    private Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Position at(float x, float y) {
        return new Position(x, y);
    }
}
