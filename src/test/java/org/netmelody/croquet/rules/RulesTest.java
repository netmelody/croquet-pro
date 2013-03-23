package org.netmelody.croquet.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.netmelody.croquet.model.StrokeEvent.runHoop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.netmelody.croquet.model.Ball;
import org.netmelody.croquet.model.Hoop;
import org.netmelody.croquet.model.Position;
import org.netmelody.croquet.model.StrokeEvent;
import org.netmelody.croquet.model.Team;
import org.netmelody.croquet.model.Turn;

public final class RulesTest {

    private final Team team = new Team("1");
    private final Turn turn = new Turn(team);

    private final Rules rules = new Rules();

    @Test public void
    eventlessStrokeFinishesTurn() {
        final Hoop hoop = new Hoop(Position.at(1, 2));
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, hoop, events());

        assertThat(updatedTurn.finished(), is(true));
    }

    @Test public void
    runningTargetHoopWithThePlayedBallGarnersAnExtraStroke() {
        final Hoop hoop = new Hoop(Position.at(1, 2));
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, hoop, events(runHoop(Ball.BLACK, hoop)));
        
        assertThat(updatedTurn.finished(), is(false));
    }

    @Test public void
    runningTargetHoopWithABallOtherThanThatPlayedGarnersNoExtraStroke() {
        final Hoop hoop = new Hoop(Position.at(1, 2));
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, hoop, events(runHoop(Ball.BLUE, hoop)));
        
        assertThat(updatedTurn.finished(), is(true));
    }

    @Test public void
    runningNonTargetHoopWithThePlayedBallGarnersAnExtraStroke() {
        final Hoop target = new Hoop(Position.at(1, 2));
        final Hoop other = new Hoop(Position.at(1, 2));
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, target, events(runHoop(Ball.BLACK, other)));
        
        assertThat(updatedTurn.finished(), is(true));
    }


    private static List<StrokeEvent<?>> events(StrokeEvent<?>... events) {
        return new ArrayList<StrokeEvent<?>>(Arrays.asList(events));
    }
}
