package com.scarytom.collider.model;

public final class Strike {
    public final double compassDirection;
    public final double speed;

    public Strike(double compassDirectionRadians, double speed) {
        this.compassDirection = compassDirectionRadians;
        this.speed = speed;
    }

}
