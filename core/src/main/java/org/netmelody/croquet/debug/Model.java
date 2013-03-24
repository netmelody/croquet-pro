package org.netmelody.croquet.debug;

import java.awt.Color;

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

public final class Model {

    private final World world = new World(new Vec2(0.0f, 0.0f), true);

    public Model() {
        world.setContactListener(new CustomContactListener());
        final FixtureDef ballDef = ballDefinition();
        createBall(ballDef, Color.BLUE,    5.0f, 25.0f,  -5.0f, -24.0f, world);
        createBall(ballDef, Color.RED,   -20.0f,  5.0f,  30.0f,   5.0f, world);
        createBall(ballDef, Color.BLACK,  20.0f,  5.0f, -30.0f,   5.0f, world);
        createBall(ballDef, Color.YELLOW, 20.0f, 25.0f, -30.0f, -25.0f, world);
        
        final FixtureDef hoopDef = hoopDefinition();
        createHoop(hoopDef, 3.0f, 0.0f, 1.1f, 0.0f, world);
    }

    private static FixtureDef ballDefinition() {
        final CircleShape shape = new CircleShape();
        shape.m_radius = 1f;
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1f;
        fd.restitution = 0.66f;
        return fd;
    }

    private static void createBall(FixtureDef fd, Color colour, float x, float y, float vx, float vy, World world) {
        final BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(x, y);
        bd.linearDamping = 0.8f;
        bd.fixedRotation = true;
        bd.linearVelocity.set(vx, vy);
        
        final Body body = world.createBody(bd);
        body.setUserData(colour);
        body.createFixture(fd);
    }

    private FixtureDef hoopDefinition() {
        final CircleShape shape = new CircleShape();
        shape.m_radius = 0.05f;
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        return fd;
    }

    private static void createHoop(FixtureDef fd, float x, float y, float x1, float y1, World world) {
        final BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        bd.position.set(x - x1, y - y1);
        world.createBody(bd).createFixture(fd);
        bd.position.set(x + x1, y + y1);
        world.createBody(bd).createFixture(fd);
    }

    public World pose() {
        return world;
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

