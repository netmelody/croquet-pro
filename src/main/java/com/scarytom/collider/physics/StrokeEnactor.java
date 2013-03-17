package com.scarytom.collider.physics;

import static com.scarytom.collider.model.Position.at;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import com.scarytom.collider.model.Ball;
import com.scarytom.collider.model.BallInPlay;
import com.scarytom.collider.model.Hoop;
import com.scarytom.collider.model.Pitch;
import com.scarytom.collider.model.Stroke;

public final class StrokeEnactor {

    private static final float TIME_STEP = 0.01f;
    private static final int VELOCITY_ITERATIONS_PER_STEP = 3;
    private static final int POSITION_ITERATIONS_PER_STEP = 8;

    private static final float REST_VELOCITY_THRESHOLD = 0.01f;

    private static final FixtureDef BALL_DEFINITON;
    static {
        final CircleShape shape = new CircleShape();
        shape.m_radius = 1f;
        BALL_DEFINITON = new FixtureDef();
        BALL_DEFINITON.shape = shape;
        BALL_DEFINITON.density = 1.0f;
        BALL_DEFINITON.restitution = 0.66f;
    }
    private static final FixtureDef HOOP_DEFINITON;
    static {
        final CircleShape shape = new CircleShape();
        shape.m_radius = 0.05f;
        HOOP_DEFINITON = new FixtureDef();
        HOOP_DEFINITON.shape = shape;
    }
    private static final FixtureDef PEG_DEFINITON;
    static {
        final CircleShape shape = new CircleShape();
        shape.m_radius = 0.1f;
        PEG_DEFINITON = new FixtureDef();
        PEG_DEFINITON.shape = shape;
    }

    private final World world = new World(new Vec2(0.0f, 0.0f), true);

    public StrokeEnactor(Pitch pitch) {
        world.setContactListener(new CustomContactListener());
        
        for (Hoop hoop : pitch.hoops) {
            createHoop(hoop.position.x, hoop.position.y, 1.1f, 0.0f);
        }
    }

    public Transition makeStroke(List<BallInPlay> balls, Stroke stroke) {
        clearBalls();
        for (BallInPlay ballInPlay : balls) {
            if (stroke.ball.equals(ballInPlay.ball)) {
                createBall(ballInPlay.ball, ballInPlay.position.x, ballInPlay.position.y, -0.0f, 25f);
            }
            else {
                createStillBall(ballInPlay.ball, ballInPlay.position.x, ballInPlay.position.y);
            }
        }
        return simulate();
    }

    private void createStillBall(Ball colour, float x, float y) {
        createBall(colour, x, y, 0.0f, 0.0f);
    }

    private void createBall(Ball colour, float x, float y, float vx, float vy) {
        final BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(x, y);
        bd.linearDamping = 0.8f;
        bd.fixedRotation = true;
        bd.linearVelocity.set(vx, vy);
        
        final Body body = world.createBody(bd);
        body.setUserData(colour);
        body.createFixture(BALL_DEFINITON);
    }

    private void createHoop(float x, float y, float x1, float y1) {
        final BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        bd.position.set(x - x1, y - y1);
        world.createBody(bd).createFixture(HOOP_DEFINITON);
        bd.position.set(x + x1, y + y1);
        world.createBody(bd).createFixture(HOOP_DEFINITON);
    }

    private Transition simulate() {
        List<List<BallInPlay>> ballPositions = new ArrayList<List<BallInPlay>>();
        do {
            world.step(TIME_STEP, VELOCITY_ITERATIONS_PER_STEP, POSITION_ITERATIONS_PER_STEP);
            ballPositions.add(currentBallPositions());
        } while (!isFinished());
        return new Transition(ballPositions);
    }

    private List<BallInPlay> currentBallPositions() {
        final List<BallInPlay> result = new ArrayList<BallInPlay>();
        
        Body body = world.getBodyList();
        while (body != null) {
            if (body.getUserData() instanceof Ball) {
                result.add(new BallInPlay((Ball)body.getUserData(), at(body.getWorldCenter().x,  body.getWorldCenter().y)));
            }
            body = body.getNext();
        }
        return result;
    }

    private boolean isFinished() {
        Body body = world.getBodyList();
        while (body != null) {
            if (body.m_linearVelocity.x > REST_VELOCITY_THRESHOLD || body.m_linearVelocity.y > REST_VELOCITY_THRESHOLD) {
                return false;
            }
            body = body.getNext();
        }
        return true;
    }

    private void clearBalls() {
        Body body = world.getBodyList();
        while (body != null) {
            if (body.getUserData() instanceof Ball) {
                world.destroyBody(body);
            }
            body = body.getNext();
        }
    }

    private static final class CustomContactListener implements ContactListener {
        public void beginContact(Contact contact) { }
        public void endContact(Contact contact) { }
        public void postSolve(Contact contact, ContactImpulse impulse) { }
        public void preSolve(Contact contact, Manifold oldManifold) {
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();
            System.out.println(fixtureA.getBody().getUserData() + " hit " + fixtureB.getBody().getUserData());
        }
    }
}

