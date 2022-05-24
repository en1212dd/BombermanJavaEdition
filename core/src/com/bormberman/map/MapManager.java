package com.bormberman.map;

import java.util.EnumMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.bormberman.Bomberman;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.GroundComponent;

import static com.bormberman.Bomberman.BODY_DEF;
import static com.bormberman.Bomberman.FIXTURE_DEF;
import static com.bormberman.Bomberman.BIT_GROUND;
import static com.bormberman.Bomberman.resetBodieAndFixture;

public class MapManager {
    public static final String TAG = MapManager.class.getSimpleName();

    private final World world;
    private final Array<Body> bodies;

    private final AssetManager assetManager;
    private final ESCEngine engine;

    private MapType currentMapType;
    private Map currentMap;
    private final EnumMap<MapType, Map> mapCache; 
    private final Array<MapListener> listeners;
    private Array<Entity> gameObjectsToRemove;

    public MapManager(Bomberman context){
        currentMap=null;
        currentMapType = null;
        world = context.getWorld();
        assetManager = context.getAssetManager();
        engine = context.getEscEngine();
        bodies = new Array<>();
        mapCache = new EnumMap<>(MapType.class);
        listeners = new Array<>();
        gameObjectsToRemove = new Array<>();
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
            destroyGameObjects();
        }

        Gdx.app.debug(TAG, "Cambiando al mapa: "+type);
        currentMap = mapCache.get(type);
        if (currentMap == null) {
            Gdx.app.debug(TAG, "Creando un mapa nuevo de tipo "+ type);
            final TiledMap tiledMap = assetManager.get(type.getFilePath(),TiledMap.class);
            currentMap = new Map(tiledMap);
            currentMap.parceCollisionLayer();
            currentMap.parceObstalcesLayer();
            mapCache.put(type, currentMap);
        }

        spawnCollisionAreas();
        spawnGameObjects();
        
        for (final MapListener listener : listeners) {
            listener.mapChange(currentMap);
        }
    }

    private void spawnGameObjects() {
        for (final GameObject gameObject : currentMap.getGameObjects()) {
            engine.createGameObjects(gameObject);
        }
    }
    private void destroyGameObjects() {
        for (final Entity entity : engine.getEntities()) {
            if (ESCEngine.GO_COMPONENT_MAPPER.get(entity)!= null) {
                gameObjectsToRemove.add(entity);
            }
        }
        for (final Entity entity : gameObjectsToRemove) {
            engine.removeEntity(entity);
        }
    }
    private void destroyCollisionAreas(){
        for (Body body : bodies) {
            if ( ((Entity)body.getUserData()).getComponent(GroundComponent.class) != null) {
                world.destroyBody(body);
            }
        }
    }
    private void spawnCollisionAreas() {
        resetBodieAndFixture();
        for (final CollisionArea collisionArea : currentMap.getCollisionArea()) {
            final Entity graundEntity = engine.createEntity();
            final GroundComponent groundComponent = engine.createComponent(GroundComponent.class);
            groundComponent.name = "GROUND";
            graundEntity.add(groundComponent);
            BODY_DEF.position.set(collisionArea.getX(), collisionArea.getY());
            BODY_DEF.fixedRotation = true;
            final Body body = world.createBody(BODY_DEF);
            body.setUserData(graundEntity);

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
