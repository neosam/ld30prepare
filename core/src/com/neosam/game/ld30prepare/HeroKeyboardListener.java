package com.neosam.game.ld30prepare;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by neosam on 22.08.14.
 */
public class HeroKeyboardListener extends InputListener {
    private Settings settings;

    public HeroKeyboardListener(Settings settings) {
        this.settings = settings;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        final AnimatedPhysicsActor actor = (AnimatedPhysicsActor) event.getListenerActor();

        if (keycode == settings.walkLeftKey) {
            actor.startRun(AnimatedPhysicsActorDirection.left);
        } else if (keycode == settings.walkRightKey) {
            actor.startRun(AnimatedPhysicsActorDirection.right);
        } else if (keycode == settings.jumpKey) {
            actor.jump();
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        final AnimatedPhysicsActor actor = (AnimatedPhysicsActor) event.getListenerActor();

        if (keycode == settings.walkLeftKey) {
            actor.stopRun();
        } else if (keycode == settings.walkRightKey) {
            actor.stopRun();
        } else {
            return false;
        }
        return true;
    }
}
