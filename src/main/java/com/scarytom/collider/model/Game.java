package com.scarytom.collider.model;

import static com.scarytom.collider.model.Position.at;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.List;


public final class Game {

    public final Pitch pitch = new Pitch();

    public final List<BallInPlay> ballPositions = unmodifiableList(asList(
            new BallInPlay(Ball.BLACK,  at(1.0f, 3.00f)),
            new BallInPlay(Ball.YELLOW, at(1.0f, 3.25f)),
            new BallInPlay(Ball.RED,    at(1.0f, 3.50f)),
            new BallInPlay(Ball.BLUE  , at(1.0f, 3.75f))));

}
