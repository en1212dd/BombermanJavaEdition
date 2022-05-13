package com.bormberman.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bormberman.Bomberman;
import com.bormberman.input.GameKeys;
import com.bormberman.input.InputManager;
import com.bormberman.map.CollisionArea;
import com.bormberman.map.Map;

import static com.bormberman.Bomberman.UNIT_SCALE;

public class GameScreen extends AbstractScreen<Table> {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private Body player;
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

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        this.player = null;

        final TiledMap tiledMap = assetManager.get("map/stage1_1.tmx", TiledMap.class);
        mapRenderer.setMap(tiledMap);
        map = new Map(tiledMap);
        map.parceCollisionLayer();

        spawnPlayer();
        spawnCollisionAreas();
    };

    private void resetBodieAndFixture() {
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyType.StaticBody;
        bodyDef.fixedRotation = false;

        fixtureDef.density = 0;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = 0x0001;
        fixtureDef.filter.maskBits = -1;
        fixtureDef.shape = null;
    }

    private void spawnPlayer() {
        resetBodieAndFixture();
        bodyDef.position.set(map.parcePlayerStartLayer());
        bodyDef.type = BodyType.DynamicBody;
        this.player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.45f, 0.45f, player.getLocalCenter(), 0);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();
    }

    private void spawnCollisionAreas() {
        for (final CollisionArea collisionArea : map.getCollisionArea()) {
            resetBodieAndFixture();

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
        if (directionChange) {
            player.applyLinearImpulse(
                    (speedX * 3 - player.getLinearVelocity().x) * player.getMass(),
                    (speedY * 3 - player.getLinearVelocity().y) * player.getMass(),
                    player.getWorldCenter().x, player.getWorldCenter().y, true);
        }

        viewport.apply(true);
        mapRenderer.setView(orthographicCamera);
        mapRenderer.render();
        box2dDebugRenderer.render(world, viewport.getCamera().combined);
    }

    @Override
    protected Table getScreenUI(Bomberman context) {
        return new Table();
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
        switch (key) {
            case UP:
                directionChange = true;
                speedY = 1;
                break;
            case DOWN:
                directionChange = true;
                speedY = -1;
                break;
            case LEFT:
                directionChange = true;
                speedX = -1;
                break;
            case RIGTH:
                directionChange = true;
                speedX = 1;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {
        switch (key) {
            case UP:
                directionChange = true;
                speedY = manager.isKeyPressed(GameKeys.DOWN) ? -1 : 0;
                break;
            case DOWN:
                directionChange = true;
                speedY = manager.isKeyPressed(GameKeys.UP) ? 1 : 0;
                break;
            case LEFT:
                directionChange = true;
                speedX = manager.isKeyPressed(GameKeys.RIGTH) ? 1 : 0;
                break;
            case RIGTH:
                directionChange = true;
                speedX = manager.isKeyPressed(GameKeys.LEFT) ? -1 : 0;
                break;
            default:
                break;
        }
    }

}
