package org.netmelody.croquet.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import java.util.ArrayList;

import org.junit.Test;

public final class GameTest {

    private final Team team1 = new Team("1");
    private final Team team2 = new Team("2");

    private final Game game = new Game(team1, team2);

    @Test public void
    teamOneStartsFirst() {
        assertThat(game.currentTeam(), sameInstance(team1));
    }

    @Test public void
    teamTwoGetsATurnNext() {
        Game game2 = game.applyStroke(Ball.BLACK, new ArrayList<StrokeEvent<?>>(), game.ballPositions);
        assertThat(game2.currentTeam(), sameInstance(team2));
    }
}
