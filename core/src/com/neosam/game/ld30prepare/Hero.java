package com.neosam.game.ld30prepare;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by neosam on 21.08.14.
 */
public class Hero extends AnimatedPhysicsActor {
    public Settings settings;

    public Hero(Sharer sharer, TextureAtlas textureAtlas) {
        super(sharer, new Vector2(2, 4), textureAtlas, "hero_", "_");
        activateAnimation("run");
        settings = sharer.getSettings();

        addListener(new HeroKeyboardListener(settings));
    }
}
