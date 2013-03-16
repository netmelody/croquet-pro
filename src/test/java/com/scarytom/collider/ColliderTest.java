package com.scarytom.collider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public final class ColliderTest {

    @Test public void
    modelsSimpleMovement() {
        double[] startPos = position(2.0, 4.0);
        double[] velocity = new double[] {1.0, 0.5};
        
        double[] positionAfterOneSecond = position(startPos[0] + velocity[0], startPos[1] + velocity[1]);
        assertThat(positionAfterOneSecond, is(position(3.0, 4.5)));
    }

    private static double[] position(double x, double y) {
        return new double[] {x, y};
    }
}
