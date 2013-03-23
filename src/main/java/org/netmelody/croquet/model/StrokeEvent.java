package org.netmelody.croquet.model;

public final class StrokeEvent {

    public final Ball ball;
    public final StrokeEventType type;

    private StrokeEvent(Ball ball, StrokeEventType type) {
        this.ball = ball;
        this.type = type;
    }

}
