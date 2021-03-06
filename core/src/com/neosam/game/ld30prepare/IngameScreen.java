package com.neosam.game.ld30prepare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by neosam on 21.08.14.
 */
public class IngameScreen implements Screen, Sharer {
    final private static int DEFAULT_WIDTH = 30;
    final private static int DEFAULT_HEIGHT = 20;

    private Stage stage;
    private World world;
    private Hero hero;
    private OrthographicCamera camera;
    private TextureAtlas heroTextureAtlas;

    private Box2DDebugRenderer debugRenderer;
    private MapController map;

    private Texture background;
    private float backgroundDelay = 0.9f;
    final AssetManager assetManager = new AssetManager();



    private Settings settings;

    public IngameScreen() {
        final Viewport viewport = new ExtendViewport(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        camera = (OrthographicCamera) viewport.getCamera();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        settings = new Settings();

        assetManager.load("background.png", Texture.class);
        assetManager.load("hero.txt", TextureAtlas.class);
        assetManager.finishLoading();
        background = assetManager.get("background.png", Texture.class);
        setupPhysics();
        setupMap();
        setupAnimations();
        setupActors();
    }

    private void setupMap() {
        map = new MapController("map.tmx");
        map.preparePhysics(world);
    }

    private void setupAnimations() {
        heroTextureAtlas = assetManager.get("hero.txt", TextureAtlas.class);
    }

    private void setupActors() {
        hero = new Hero(this, heroTextureAtlas);
        final Vector2 spawnPoint = map.getTriggerPoint("player_spawn");
        hero.getBody().setTransform(spawnPoint, 0);
        stage.addActor(hero);
        stage.setKeyboardFocus(hero);
    }

    private void setupPhysics() {
        world = new World(new Vector2(0, -100), true);
        debugRenderer = new Box2DDebugRenderer();
}

    @Override
    public void render(float delta) {
        final Batch batch = stage.getSpriteBatch();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, backgroundDelay * camera.position.x - 15, backgroundDelay * camera.position.y - 10, 40, 40);
        batch.end();

        world.step(delta, 2, 6);
        cameraUpdate();
        stage.act(delta);
        map.draw(batch);
        stage.draw();
        debugRenderer.render(world, stage.getViewport().getCamera().combined);
    }

    private void cameraUpdate() {
        camera.position.set(hero.getX(), hero.getY(), 0);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        if (stage.getViewport() instanceof ExtendViewport) {
            final ExtendViewport viewport = (ExtendViewport) stage.getViewport();
            viewport.setMaxWorldWidth(width);
            viewport.setMaxWorldHeight(height);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public World getWorld() {
        return world;
    }

    public Settings getSettings() {
        return settings;
    }
}
