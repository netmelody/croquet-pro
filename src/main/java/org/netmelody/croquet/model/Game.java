package org.netmelody.croquet.model;

import static org.netmelody.croquet.model.Position.at;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.List;


public final class Game {

    public final Pitch pitch = new Pitch();

    public final List<BallInPlay> ballPositions = unmodifiableList(asList(
            new BallInPlay(Ball.BLACK,  at(10.0f, 30.0f)),
            new BallInPlay(Ball.YELLOW, at(10.0f, 32.5f)),
            new BallInPlay(Ball.RED,    at(10.0f, 35.0f)),
            new BallInPlay(Ball.BLUE  , at(10.0f, 37.5f))));

}
