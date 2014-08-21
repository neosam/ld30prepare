package com.neosam.game.ld30prepare;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by neosam on 21.08.14.
 */
abstract public class PhysicsActor extends Actor {
    private World world;
    private IngameScreen ingameScreen;
    private Body body;
    private Vector2 size;

    public PhysicsActor(PhysicsSharer physicsSharer, Vector2 size) {
        world = physicsSharer.getWorld();
        this.size = size;

        generateBody(size);
    }

    private void generateBody(Vector2 size) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        final PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(size.x / 2, size.y / 2);

        final FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = bodyShape;

        final Fixture bodyFixture = body.createFixture(bodyFixtureDef);
        bodyShape.dispose();

        setOrigin(size.x / 2, size.y / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(body.getPosition().x, body.getPosition().y);
        setSize(size.x, size.y);
    }
}
