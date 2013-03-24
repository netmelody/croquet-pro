package org.netmelody.croquet.model;

public final class StrokeEvent<T> {

    public final Ball ball;
    public final T counterparty;
    public final StrokeEventType type;

    private StrokeEvent(Ball ball, T counterparty, StrokeEventType type) {
        this.ball = ball;
        this.counterparty = counterparty;
        this.type = type;
    }

    public static StrokeEvent<Ball> collision(Ball ball1, Ball ball2) {
        return new StrokeEvent<Ball>(ball1, ball2, StrokeEventType.COLLISION);
    }

    public static StrokeEvent<Hoop> runHoop(Ball ball, Hoop hoop) {
        return new StrokeEvent<Hoop>(ball, hoop, StrokeEventType.RUN_HOOP);
    }

    public static StrokeEvent<Peg> collision(Ball ball, Peg peg) {
        return new StrokeEvent<Peg>(ball, peg, StrokeEventType.HIT_PEG);
    }

    public static StrokeEvent<Void> outOfBounds(Ball ball) {
        return new StrokeEvent<Void>(ball, null, StrokeEventType.OUT_OF_BOUNDS);
    }

}
