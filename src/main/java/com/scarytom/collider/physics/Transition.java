package com.scarytom.collider.physics;

import java.util.List;

import com.scarytom.collider.model.BallInPlay;

public final class Transition {

    private List<List<BallInPlay>> footage;
    public final float timeStep;

    public Transition(float timeStep, List<List<BallInPlay>> positions) {
        this.timeStep = timeStep;
        this.footage = positions;
    }

    public List<BallInPlay> finalPositions() {
        return footage.get(footage.size() - 1);
    }

    public List<List<BallInPlay>> footage() {
        return footage;
    }
}
