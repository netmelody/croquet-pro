package org.netmelody.croquet.rules;

import static org.netmelody.croquet.model.StrokeEventType.COLLISION;
import static org.netmelody.croquet.model.StrokeEventType.RUN_HOOP;

import java.util.List;

import org.netmelody.croquet.model.Ball;
import org.netmelody.croquet.model.Hoop;
import org.netmelody.croquet.model.StrokeEvent;
import org.netmelody.croquet.model.Turn;

public final class Rules {

    public Turn adjudicateStroke(Turn currentTurn, Ball ball, Hoop nextHoop, List<StrokeEvent<?>> events) {
        for (StrokeEvent<?> event : events) {
            if (event.type == RUN_HOOP && ball == event.ball && nextHoop == event.counterparty) {
                return currentTurn.continuation();
            }
            
            if (event.type == COLLISION && (ball == event.ball || ball == event.counterparty)) {
                return currentTurn.roquet((ball == event.ball) ? (Ball)event.counterparty : event.ball);
            }
        }
        
        return (currentTurn.roquetBall == null) ? currentTurn.finish() : currentTurn.continuation();
    }

}
