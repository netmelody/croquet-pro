package com.scarytom.collider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public final class ColliderTest {

    private static final int X  = 0;
    private static final int Y  = 1;
    private static final int VX = 2;
    private static final int VY = 3;

    @Test public void
    modelsSimpleMovement() {
        double[] objectAtStart = object(2.0, 4.0, 1.0, 0.5);
        
        double[] objectAfterOneSecond = object(objectAtStart[X] + objectAtStart[VX],
                                               objectAtStart[Y] + objectAtStart[VY],
                                               objectAtStart[VX],
                                               objectAtStart[VY]);
        
        assertThat(positionOf(objectAfterOneSecond), is(new double[]{3.0, 4.5}));
        assertThat(velocityOf(objectAfterOneSecond), is(new double[]{1.0, 0.5}));
    }

    @Test public void
    modelsPositionDuringSimpleMovement() {
        double[] objectAtStart = object(2.0, 4.0, 1.0, 0.5);
        
        double[] objectAfterOneSecond = object(objectAtStart[X] + objectAtStart[VX] / 100,
                                               objectAtStart[Y] + objectAtStart[VY] / 100,
                                               objectAtStart[VX],
                                               objectAtStart[VY]);
        
        assertThat(positionOf(objectAfterOneSecond), is(new double[]{2.01, 4.005}));
        assertThat(velocityOf(objectAfterOneSecond), is(new double[]{1.0, 0.5}));
    }

    private static double[] object(double x, double y, double vx, double vy) {
        return new double[] {x, y, vx, vy};
    }

    private static double[] positionOf(double[] object) {
        return new double[]{object[X], object[Y]};
    }

    private static double[] velocityOf(double[] object) {
        return new double[]{object[VX], object[VY]};
    }
}
