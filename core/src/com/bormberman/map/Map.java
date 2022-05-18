package com.bormberman.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import static com.bormberman.Bomberman.UNIT_SCALE;

public class Map {
    private final TiledMap tiledMap;
    private final String TAG = Map.class.getSimpleName();
    private final Array<CollisionArea> collisionArea;
    public Map(final TiledMap tiledMap){
        this.tiledMap = tiledMap;
        this.collisionArea = new Array<>();
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

    public Array<CollisionArea> getCollisionArea() {
        return collisionArea;
    }
    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
