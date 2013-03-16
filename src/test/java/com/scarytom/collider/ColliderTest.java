package com.scarytom.collider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public final class ColliderTest {

    @Test public void
    modelsSimpleMovement() {
        double[] objectAtStart = object(2.0, 4.0, 1.0, 0.5);
        
        double[] objectAfterOneSecond = object(objectAtStart[0] + objectAtStart[2],
                                               objectAtStart[1] + objectAtStart[3],
                                               objectAtStart[2],
                                               objectAtStart[3]);
        
        assertThat(positionOf(objectAfterOneSecond), is(new double[]{3.0, 4.5}));
        assertThat(velocityOf(objectAfterOneSecond), is(new double[]{1.0, 0.5}));
    }

    @Test public void
    modelsPositionDuringSimpleMovement() {
        double[] objectAtStart = object(2.0, 4.0, 1.0, 0.5);
        
        double[] objectAfterOneSecond = object(objectAtStart[0] + objectAtStart[2] / 100,
                objectAtStart[1] + objectAtStart[3] / 100,
                objectAtStart[2],
                objectAtStart[3]);
        
        assertThat(positionOf(objectAfterOneSecond), is(new double[]{2.01, 4.005}));
        assertThat(velocityOf(objectAfterOneSecond), is(new double[]{1.0, 0.5}));
    }

    private static double[] object(double x, double y, double vx, double vy) {
        return new double[] {x, y, vx, vy};
    }

    private static double[] positionOf(double[] object) {
        return new double[]{object[0], object[1]};
    }

    private static double[] velocityOf(double[] object) {
        return new double[]{object[2], object[3]};
    }
}
