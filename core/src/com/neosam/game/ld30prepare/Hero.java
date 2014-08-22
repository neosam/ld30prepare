package com.neosam.game.ld30prepare;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by neosam on 21.08.14.
 */
public class Hero extends AnimatedPhysicsActor {
    public Hero(PhysicsSharer physicsSharer, TextureAtlas textureAtlas) {
        super(physicsSharer, new Vector2(2, 4), textureAtlas, "hero_", "_");
        activateAnimation("run");
    }
}
