package com.bormberman.map;

import java.util.EnumMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.bormberman.Bomberman;

import static com.bormberman.Bomberman.BODY_DEF;
import static com.bormberman.Bomberman.FIXTURE_DEF;
import static com.bormberman.Bomberman.BIT_GROUND;
import static com.bormberman.Bomberman.resetBodieAndFixture;

public class MapManager {
    public static final String TAG = MapManager.class.getSimpleName();

    private final World world;
    private final Array<Body> bodies;

    private final AssetManager assetManager;

    private MapType currentMapType;
    private Map currentMap;
    private final EnumMap<MapType, Map> mapCache; 
    private final Array<MapListener> listeners;

    public MapManager(Bomberman context){
        currentMap=null;
        currentMapType = null;
        world = context.getWorld();
        assetManager = context.getAssetManager();
        bodies = new Array<>();
        mapCache = new EnumMap<>(MapType.class);
        listeners = new Array<>();
    }
    public void addMapListener(MapListener listener) {
        listeners.add(listener);
    }
    public void setMap(MapType type) {
        if (currentMapType == type) {
            return;
        }
        if (currentMap!= null) {
            world.getBodies(bodies);
            destroyCollisionAreas();
        }

        Gdx.app.debug(TAG, "Cambiando al mapa: "+type);
        currentMap = mapCache.get(type);
        if (currentMap == null) {
            Gdx.app.debug(TAG, "Creando un mapa nuevo de tipo "+ type);
            final TiledMap tiledMap = assetManager.get(type.getFilePath(),TiledMap.class);
            currentMap = new Map(tiledMap);
            currentMap.parceCollisionLayer();
            mapCache.put(type, currentMap);
        }

        spawnCollisionAreas();
        
        for (final MapListener listener : listeners) {
            listener.mapChange(currentMap);
        }
    }

    private void destroyCollisionAreas(){
        for (Body body : bodies) {
            if ("GROUND".equals(body.getUserData())) {
                world.destroyBody(body);
            }
        }
    }
    private void spawnCollisionAreas() {
        resetBodieAndFixture();
        for (final CollisionArea collisionArea : currentMap.getCollisionArea()) {

            BODY_DEF.position.set(collisionArea.getX(), collisionArea.getY());
            BODY_DEF.fixedRotation = true;
            final Body body = world.createBody(BODY_DEF);
            body.setUserData("GROUND");

            FIXTURE_DEF.filter.categoryBits = BIT_GROUND;
            FIXTURE_DEF.filter.maskBits = -1;
            final ChainShape chainShape = new ChainShape();
            chainShape.createChain(collisionArea.getVertices());
            FIXTURE_DEF.shape = chainShape;
            body.createFixture(FIXTURE_DEF);
            chainShape.dispose();
        }
    }
    public Map getCurrentMap() {
        return currentMap;
    }
        
}
