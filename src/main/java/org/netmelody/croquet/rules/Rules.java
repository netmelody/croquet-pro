package org.netmelody.croquet.rules;

import java.util.List;

import org.netmelody.croquet.model.Ball;
import org.netmelody.croquet.model.StrokeEvent;
import org.netmelody.croquet.model.Turn;

public final class Rules {

    public Turn adjudicateStroke(Turn currentTurn, Ball ball, List<StrokeEvent<?>> events) {
        return currentTurn.finish();
    }

}
