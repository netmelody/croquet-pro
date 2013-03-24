package org.netmelody.croquet.model;

public final class Stroke {

    public final Ball ball;
    public final Position roquetPosition;
    public final Strike strike;

    private Stroke(Ball ball, Position roquetPosition, Strike strike) {
        this.ball = ball;
        this.roquetPosition = roquetPosition;
        this.strike = strike;
    }

    public static Stroke standard(Ball ball, Strike strike) {
        return new Stroke(ball, null, strike);
    }

    public static Stroke roquet(Ball ball, Position roquetPosition, Strike strike) {
        return new Stroke(ball, roquetPosition, strike);
    }
}
