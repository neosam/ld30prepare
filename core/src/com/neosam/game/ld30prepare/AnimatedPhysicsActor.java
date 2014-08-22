package com.neosam.game.ld30prepare;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by neosam on 21.08.14.
 */
public class AnimatedPhysicsActor extends PhysicsActor {
    private Animation currentAnimation;
    private TextureAtlas textureAtlas;
    private TextureRegion currentFrame;
    private AnimatedPhysicsActorDirection direction = AnimatedPhysicsActorDirection.left;
    private float currentTime = 0;
    private Map<String, Animation> animationMap = new HashMap<String, Animation>();

    private String atlasPrefix = "";
    private String atlasSuffix = "";
    private Vector2 jumpImpulse = new Vector2(0, 50);
    private Vector2 center;
    private float maxSpeed = 30;
    private boolean running = false;
    private Vector2 runLeftImpulse = new Vector2(-2f, 0);
    private Vector2 runRightImpulse = new Vector2(2f, 0);

    public AnimatedPhysicsActor(PhysicsSharer physicsSharer, Vector2 size, TextureAtlas textureAtlas, String atlasPrefix, String atlasSuffix) {
        super(physicsSharer, size);
        this.atlasSuffix =atlasSuffix;
        this.atlasPrefix = atlasPrefix;
        this.textureAtlas = textureAtlas;
        center = new Vector2(size.x / 2, size.y / 2);
        setupDefaultAnimations();

    }

    public AnimatedPhysicsActor(PhysicsSharer physicsSharer, Vector2 size, TextureAtlas textureAtlas) {
        super(physicsSharer, size);
        this.textureAtlas = textureAtlas;
        setupDefaultAnimations();
    }

    private void setupDefaultAnimations() {
        setupAnimation(0.1f, atlasPrefix + "idle" + atlasSuffix, "idle");
        setupAnimation(0.05f, atlasPrefix + "run" + atlasSuffix, "run");
        activateAnimation("idle");
    }

    public void addAnimation(String name, Animation animation) {
        animationMap.put(name, animation);
    }

    public void activateAnimation(String name) {
        if (animationMap.containsKey(name)) {
            currentAnimation = animationMap.get(name);
            currentTime = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (currentFrame != null) {
            if (direction == AnimatedPhysicsActorDirection.right) {
                    final float x = getX();
                    final float y = getY();
                    final float width = getWidth();
                    final float height = getHeight();
                    final float originX = getOriginX();
                    final float originY = getOriginY();
                    batch.draw(currentFrame, x - originX, y - originY,
                            height * currentFrame.getRegionWidth() / currentFrame.getRegionHeight(), height);
            } else if (direction == AnimatedPhysicsActorDirection.left) {
                    final float x = getX();
                    final float y = getY();
                    final float width = getWidth();
                    final float height = getHeight();
                    final float originX = getOriginX();
                    final float originY = getOriginY();
                    batch.draw(currentFrame, x + originX, y - originY,
                            -height * currentFrame.getRegionWidth() / currentFrame.getRegionHeight(), height);
            }

        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        final float currentSpeed = getBody().getLinearVelocity().x;
        final float currentUpperSpeed = getBody().getLinearVelocity().y;

        currentTime += delta;
        if (currentAnimation != null) {
            currentFrame = currentAnimation.getKeyFrame(currentTime);
        }
        if (running == true) {
            if (direction == AnimatedPhysicsActorDirection.left) {
                getBody().applyLinearImpulse(runLeftImpulse, center, true);
            } else if (direction == AnimatedPhysicsActorDirection.right) {
                getBody().applyLinearImpulse(runRightImpulse, center, true);
            }
        } else {
            getBody().setLinearVelocity(0, currentUpperSpeed);
        }

        if (currentSpeed > maxSpeed) {
            getBody().setLinearVelocity(maxSpeed, currentUpperSpeed);
        } else if (currentSpeed < -maxSpeed) {
            getBody().setLinearVelocity(-maxSpeed, currentUpperSpeed);
        }
    }

    public void setupAnimation(float delay, String atlasName, String animationName) {
        final Array<Sprite> sprites = textureAtlas.createSprites(atlasName);
        final Animation animation = new Animation(delay, sprites);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        addAnimation(animationName, animation);
    }

    public void startRun(AnimatedPhysicsActorDirection direction) {
        setDirection(direction);
        activateAnimation("run");
        running = true;
    }

    public void stopRun() {
        activateAnimation("idle");
        running = false;
    }

    public void jump() {
        getBody().applyLinearImpulse(jumpImpulse, center, true);
    }

    public String getAtlasPrefix() {
        return atlasPrefix;
    }

    public void setAtlasPrefix(String atlasPrefix) {
        this.atlasPrefix = atlasPrefix;
    }

    public String getAtlasSuffix() {
        return atlasSuffix;
    }

    public void setAtlasSuffix(String atlasSuffix) {
        this.atlasSuffix = atlasSuffix;
    }

    public AnimatedPhysicsActorDirection getDirection() {
        return direction;
    }

    public void setDirection(AnimatedPhysicsActorDirection direction) {
        this.direction = direction;
    }
}
