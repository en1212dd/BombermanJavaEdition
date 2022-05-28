package com.bormberman.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

import static com.bormberman.Bomberman.UNIT_SCALE;



public class Map {
    private final TiledMap tiledMap;
    private final String TAG = Map.class.getSimpleName();
    private final Array<CollisionArea> collisionArea;
    private final Array<GameObject> gameObjects;
    private final IntMap<Animation<Sprite>> mapAnimation;
    public Map(final TiledMap tiledMap){
        this.tiledMap = tiledMap;
        this.collisionArea = new Array<>();

        gameObjects = new Array<>();
        mapAnimation = new IntMap<>();
    }
    public void parceCollisionLayer() {
        final MapLayer collisionLayer = tiledMap.getLayers().get("collision");
        if (collisionLayer==null) {
            Gdx.app.debug(TAG, "No se ha encontrado la capa de coliciones");
            return;
        }
        final MapObjects mapObjects = collisionLayer.getObjects();
        for (final MapObject mapObject : mapObjects) {
            if (mapObject instanceof RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                final Rectangle rectangle = rectangleMapObject.getRectangle();
                final float[] rectVertices =  new float[10];
                //left-bot
                rectVertices[0]= 0;
                rectVertices[1]= 0;
                //left-top
                rectVertices[2]= 0;
                rectVertices[3]= rectangle.height;
                //rigth-top
                rectVertices[4]= rectangle.width;
                rectVertices[5]= rectangle.height;
                //rigth-bottom
                rectVertices[6]= rectangle.width;
                rectVertices[7]= 0;
                //left-bottom
                rectVertices[8]=0;
                rectVertices[9]=0;

                collisionArea.add(new CollisionArea(rectangle.x, rectangle.y, rectVertices));
            }else if (mapObject instanceof PolylineMapObject) {
                final PolylineMapObject polylineMapObject = (PolylineMapObject) mapObject;
                final Polyline polyline = polylineMapObject.getPolyline();
                collisionArea.add(new CollisionArea(polyline.getX(), polyline.getY(), polyline.getVertices()));
            }else{
                Gdx.app.debug(TAG, "El objeto "+mapObject.toString()+ " no esta soportado por la capa de colicion");
            }
            
        }
    }
    public Vector2 parcePlayerStartLayer() {
        MapLayer playerStartPosition = tiledMap.getLayers().get("positionStartPlayer");
        if (playerStartPosition == null) {
            Gdx.app.debug(TAG, "No se ha encontrado la posicion del jugador");
        }
        MapObjects mapObjects = playerStartPosition.getObjects();
        Vector2 position = new Vector2();
        for ( MapObject mObject : mapObjects) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) mObject;
            Rectangle rectangleStartPosition = rectangleMapObject.getRectangle();
            position.set(rectangleStartPosition.x * UNIT_SCALE, rectangleStartPosition.y * UNIT_SCALE);
        }
        return position;
    }
    
    public Array<Vector2> parceEnemysPositions() {
        MapLayer enemyLayer = tiledMap.getLayers().get("enemysPositions");
        if (enemyLayer == null) {
            Gdx.app.debug(TAG, "No se ha encontrado la posicion de los enenmigos");
        }
        Array<Vector2> positions =  new Array<>();
        MapObjects mapObjects = enemyLayer.getObjects();
        for (MapObject mapObject : mapObjects) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
            Rectangle rectanglePosition = rectangleMapObject.getRectangle();
            positions.add(new Vector2(rectanglePosition.x * UNIT_SCALE, rectanglePosition.y * UNIT_SCALE));
        }
        return positions;
    }
    public void parceObstalcesLayer(String layer) {
        final MapLayer gameObLayer = tiledMap.getLayers().get(layer); 
        if (gameObLayer == null) {
            Gdx.app.debug(TAG, "No se ha encontrado la capa de objetos");
        }
        final MapObjects objects = gameObLayer.getObjects();
        for (MapObject mapObject : objects) {
            if (!(mapObject instanceof TiledMapTileMapObject)) {
                Gdx.app.debug(TAG, "El Objeto "+mapObject + " no esta soportado");
                continue;
            }
            final TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) mapObject;
            final MapProperties mapProperties = tileMapObject.getProperties();
            final MapProperties properties = tileMapObject.getTile().getProperties();
            final GameObjectType gameObjectType;
            if (mapProperties.containsKey("TYPE")) {
                gameObjectType = GameObjectType.valueOf(mapProperties.get("TYPE", String.class));
            }else if (properties.containsKey("TYPE")) {
                gameObjectType = GameObjectType.valueOf(properties.get("TYPE", String.class));
            }else{
                Gdx.app.debug(TAG, "No hay un tipo de objeto para "+ mapProperties.get("id",Integer.class));
                continue;
            }

            final int animationIndex = tileMapObject.getTile().getId();
            if (!createAnimation(animationIndex,tileMapObject.getTile())) {
                Gdx.app.debug(TAG, "No se ha podido crear la animacion para el tile" + mapProperties.get("id", Integer.class));
                continue;
            }
            final float width = mapProperties.get("width",Float.class)*UNIT_SCALE;
            final float heigth = mapProperties.get("height",Float.class)*UNIT_SCALE;
            gameObjects.add(new GameObject(gameObjectType, new Vector2(tileMapObject.getX()*UNIT_SCALE,tileMapObject.getY()*UNIT_SCALE), width, heigth, tileMapObject.getRotation(), animationIndex));
        }
    }

    private boolean createAnimation(int animationIndex, TiledMapTile tile) {
        Animation<Sprite> animation = mapAnimation.get(animationIndex);
        if (animation == null) {
            Gdx.app.debug(TAG, "Creando una nueva animacion para el tile "+ tile.getId());
            if (tile instanceof AnimatedTiledMapTile) {
                final AnimatedTiledMapTile aniTile = (AnimatedTiledMapTile) tile;
                final Sprite[] keyFrames = new Sprite[aniTile.getFrameTiles().length];
                int i = 0;
                for (final StaticTiledMapTile sprite : aniTile.getFrameTiles()) {
                    keyFrames[i++] = new Sprite(sprite.getTextureRegion());
                }
                animation = new Animation<>(aniTile.getAnimationIntervals()[0]*0.001f, keyFrames);
                animation.setPlayMode(PlayMode.LOOP);
                mapAnimation.put(animationIndex, animation);
            }else if (tile instanceof StaticTiledMapTile) {
                animation = new Animation<>(0, new Sprite(tile.getTextureRegion()));
                mapAnimation.put(animationIndex, animation);
            }else{
                Gdx.app.debug(TAG, "El tile de tipo "+ tile + " no esta soprtado ");
                return false;
            }
        }
        return true;
    }
    public Array<CollisionArea> getCollisionArea() {
        return collisionArea;
    }
    public TiledMap getTiledMap() {
        return tiledMap;
    }
    public Array<GameObject> getGameObjects() {
        return gameObjects;
    }
    public IntMap<Animation<Sprite>> getMapAnimation() {
        return mapAnimation;
    }
}
