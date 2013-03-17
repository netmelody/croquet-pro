package com.scarytom.collider.model;

import static com.scarytom.collider.model.Position.at;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.List;


public final class Game {

    public final Pitch pitch = new Pitch();

    public final List<BallInPlay> ballPositions = unmodifiableList(asList(
            new BallInPlay(Ball.BLACK,  at(1.0f, 2.0f)),
            new BallInPlay(Ball.YELLOW, at(3.0f, 2.0f)),
            new BallInPlay(Ball.RED,    at(5.0f, 2.0f)),
            new BallInPlay(Ball.BLUE  , at(7.0f, 2.0f))));

}
