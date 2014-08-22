package com.neosam.game.ld30prepare;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Iterator;

/**
 * Created by neosam on 22.08.14.
 */
public class MapController {
    private TiledMap map;

    public MapController(String filename) {
        map = new TmxMapLoader().load(filename);
    }

    public void preparePhysics(World world) {
        final Iterator<MapLayer> layers = map.getLayers().iterator();
        while (layers.hasNext()) {
            final MapLayer layer = layers.next();
            if (layer instanceof TiledMapTileLayer) {
                final int width = ((TiledMapTileLayer) layer).getWidth();
                final int height = ((TiledMapTileLayer) layer).getHeight();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        final TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) layer).getCell(x, y);
                        if (cell == null) {
                            continue;
                        }
                        generateStaticWall(world, x, y);
                    }
                }
            }
        }
    }

    private void generateStaticWall(World world, int x, int y) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        final Body body = world.createBody(bodyDef);
        body.setTransform(x, y, 0);

        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.1f, 0.1f);

        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        final Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void draw(Batch batch) {
        final Iterator<MapLayer> layers = map.getLayers().iterator();
        batch.begin();
        while (layers.hasNext()) {
            final MapLayer layer = layers.next();
            if (layer instanceof TiledMapTileLayer) {
                final int width = ((TiledMapTileLayer) layer).getWidth();
                final int height = ((TiledMapTileLayer) layer).getHeight();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        final TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) layer).getCell(x, y);
                        if (cell == null) {
                            continue;
                        }
                        final TextureRegion textureRegion = cell.getTile().getTextureRegion();
                        batch.draw(textureRegion, x, y, 1, 1);
                    }
                }
            }
        }
        batch.end();
    }

    public Vector2 getTriggerPoint(String name) {
        final MapLayer layer = map.getLayers().get("triggers");
        final MapObject mapObject = layer.getObjects().get(name);
        if (mapObject == null) {
            return null;
        }
        return new Vector2((Float) mapObject.getProperties().get("x") / (Integer) map.getProperties().get("tilewidth"),
                (Float) mapObject.getProperties().get("y") / (Integer) map.getProperties().get("tileheight"));
    }
}
