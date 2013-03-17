package com.scarytom.collider.model;

public final class Hoop {

    public final Position position;
    public final Position leg1Position;
    public final Position leg2Position;

    public final float legRadius = 0.0079375f;
    public final float width = 0.098425f + 2 * legRadius;

    public Hoop(Position position) {
        this.position = position;
        this.leg1Position = Position.at(position.x, position.y - width / 2.0f);
        this.leg2Position = Position.at(position.x, position.y + width / 2.0f);
    }

}
