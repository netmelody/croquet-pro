package com.scarytom.collider.model;

public final class Stroke {

    public final Ball ball;
    public final Position roquetPosition;
    public final Strike swing;

    private Stroke(Ball ball, Position roquetPosition, Strike swing) {
        this.ball = ball;
        this.roquetPosition = roquetPosition;
        this.swing = swing;
    }

    public static Stroke standard(Ball ball, Strike swing) {
        return new Stroke(ball, null, swing);
    }

    public static Stroke roquet(Ball ball, Position roquetPosition, Strike swing) {
        return new Stroke(ball, roquetPosition, swing);
    }
}
