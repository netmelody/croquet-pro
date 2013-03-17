package com.scarytom.collider.physics;

import java.util.List;

import com.scarytom.collider.model.BallInPlay;

public final class Transition {

    private List<List<BallInPlay>> footage;

    public Transition(List<List<BallInPlay>> positions) {
        this.footage = positions;
    }

    public List<BallInPlay> finalPositions() {
        return footage.get(footage.size());
    }

}
