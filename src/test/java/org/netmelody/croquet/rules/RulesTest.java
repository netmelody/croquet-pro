package org.netmelody.croquet.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.netmelody.croquet.model.StrokeEvent.collision;
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
    private final Hoop targetHoop = new Hoop(Position.at(1, 2));

    private final Rules rules = new Rules();

    @Test public void
    eventlessStrokeFinishesTurn() {
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, targetHoop, events());

        assertThat(updatedTurn.finished, is(true));
    }

    @Test public void
    runningTargetHoopWithThePlayedBallGarnersAnExtraStroke() {
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, targetHoop, events(runHoop(Ball.BLACK, targetHoop)));
        
        assertThat(updatedTurn.finished, is(false));
    }

    @Test public void
    runningTargetHoopWithABallOtherThanThatPlayedGarnersNoExtraStroke() {
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, targetHoop, events(runHoop(Ball.BLUE, targetHoop)));
        
        assertThat(updatedTurn.finished, is(true));
    }

    @Test public void
    runningNonTargetHoopWithThePlayedBallGarnersAnExtraStroke() {
        final Hoop other = new Hoop(Position.at(1, 2));
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, targetHoop, events(runHoop(Ball.BLACK, other)));
        
        assertThat(updatedTurn.finished, is(true));
    }

    @Test public void
    roquetingEarnsAnExtraStroke() {
        final Turn updatedTurn = rules.adjudicateStroke(turn, Ball.BLACK, targetHoop, events(collision(Ball.BLUE, Ball.BLACK)));
        
        assertThat(updatedTurn.roquetBall, is(Ball.BLUE));
        assertThat(updatedTurn.finished, is(false));
    }

    private static List<StrokeEvent<?>> events(StrokeEvent<?>... events) {
        return new ArrayList<StrokeEvent<?>>(Arrays.asList(events));
    }
}
