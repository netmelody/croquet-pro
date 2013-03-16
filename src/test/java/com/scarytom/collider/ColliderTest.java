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
        
        double[] objectAfterOneSecond = positionAfter(1.0, objectAtStart)[0];
        
        assertThat(positionOf(objectAfterOneSecond), is(new double[]{3.0, 4.5}));
        assertThat(velocityOf(objectAfterOneSecond), is(new double[]{1.0, 0.5}));
    }

    @Test public void
    modelsPositionDuringSimpleMovement() {
        double[] objectAtStart = object(2.0, 4.0, 1.0, 0.5);
        
        double[] objectAfterOneHundredthOfASecond = positionAfter(0.01, objectAtStart)[0];
        
        assertThat(positionOf(objectAfterOneHundredthOfASecond), is(new double[]{2.01, 4.005}));
        assertThat(velocityOf(objectAfterOneHundredthOfASecond), is(new double[]{1.0, 0.5}));
    }

    @Test public void
    modelsPositionOfTwoObjectsInMotion() {
        double[] object1AtStart = object(2.0, 4.0, 1.0, 0.5);
        double[] object2AtStart = object(6.0, 2.0, -3.0, 2.5);
        
        double[][] objectsAfterHalfASecond = positionAfter(0.5, object1AtStart, object2AtStart);
        
        assertThat(positionOf(objectsAfterHalfASecond[0]), is(new double[]{2.5, 4.25}));
        assertThat(positionOf(objectsAfterHalfASecond[1]), is(new double[]{4.5, 3.25}));
    }

    private static double[] object(double x, double y, double vx, double vy) {
        return new double[] {x, y, vx, vy};
    }

    private static double[][] positionAfter(double time, double[]... objects) {
        double[][] result = new double[objects.length][];
        for (int i = 0; i < objects.length; i++) {
            double[] object = objects[i];
            result[i] = object(object[X] + object[VX] * time,
                        object[Y] + object[VY] * time,
                        object[VX],
                        object[VY]);
        }
        return result;
    }

    private static double[] positionOf(double[] object) {
        return new double[]{object[X], object[Y]};
    }

    private static double[] velocityOf(double[] object) {
        return new double[]{object[VX], object[VY]};
    }
}
