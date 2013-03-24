package org.netmelody.croquet.model;

public final class Strike {
    public final float compassDirection;
    public final float speed;

    public Strike(float compassDirectionRadians, float speed) {
        this.compassDirection = compassDirectionRadians;
        this.speed = speed;
    }

}
