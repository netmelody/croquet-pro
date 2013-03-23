package org.netmelody.croquet.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;

import org.junit.Test;
import org.netmelody.croquet.model.Ball;
import org.netmelody.croquet.model.StrokeEvent;
import org.netmelody.croquet.model.Team;
import org.netmelody.croquet.model.Turn;

public final class RulesTest {

    private final Team team = new Team("1");
    private final Turn turn = new Turn(team);

    private final Rules rules = new Rules();

    @Test public void
    eventlessStrokeFinishesTurn() {
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, new ArrayList<StrokeEvent>());

        assertThat(updatedTurn.finished(), is(true));
    }

}
