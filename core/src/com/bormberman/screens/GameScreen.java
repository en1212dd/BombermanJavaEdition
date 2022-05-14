package com.bormberman.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bormberman.Bomberman;
import com.bormberman.input.GameKeys;
import com.bormberman.input.InputManager;
import com.bormberman.map.CollisionArea;
import com.bormberman.map.Map;
import com.bormberman.ui.GameUi;

import static com.bormberman.Bomberman.UNIT_SCALE;

public class GameScreen extends AbstractScreen<Table> {
    private Map map;

    public static final short BIT_PLAYER = 1 << 0;
    public static final short BIT_GROUND = 1 << 1;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final AssetManager assetManager;
    private final OrthographicCamera orthographicCamera;

    int speedX;
    int speedY;
    boolean directionChange;

    public GameScreen(final Bomberman contextBomberman) {
        super(contextBomberman);

        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, context.getSpriteBatch());
        this.assetManager = context.getAssetManager();
        this.orthographicCamera = context.getOrthographicCamera();

        final TiledMap tiledMap = assetManager.get("map/stage1_1.tmx", TiledMap.class);
        mapRenderer.setMap(tiledMap);
        map = new Map(tiledMap);
        map.parceCollisionLayer();
        context.getEscEngine().createPlayer(map.parcePlayerStartLayer(),0.45f, 0.45f);
        spawnCollisionAreas();
    };


    private void spawnCollisionAreas() {
        BodyDef bodyDef= new BodyDef();
        FixtureDef fixtureDef  = new FixtureDef();
        for (final CollisionArea collisionArea : map.getCollisionArea()) {

            bodyDef.position.set(collisionArea.getX(), collisionArea.getY());
            bodyDef.fixedRotation = true;
            final Body body = world.createBody(bodyDef);
            body.setUserData("GROUND");

            fixtureDef.filter.categoryBits = BIT_GROUND;
            fixtureDef.filter.maskBits = -1;
            final ChainShape chainShape = new ChainShape();
            chainShape.createChain(collisionArea.getVertices());
            fixtureDef.shape = chainShape;
            body.createFixture(fixtureDef);
            chainShape.dispose();
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply(true);
        mapRenderer.setView(orthographicCamera);
        mapRenderer.render();
        box2dDebugRenderer.render(world, viewport.getCamera().combined);
    }

    @Override
    protected Table getScreenUI(Bomberman context) {
        return new GameUi(context);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {
    }

}
