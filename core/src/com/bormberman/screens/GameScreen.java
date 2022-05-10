package com.bormberman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bormberman.Bomberman;
import com.bormberman.map.CollisionArea;
import com.bormberman.map.Map;

import static com.bormberman.Bomberman.UNIT_SCALE;
public class GameScreen extends AbstractScreen<Table> {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private final Body player;
    private Map map;

	public static final short BIT_PLAYER = 1<<0;
	public static final short BIT_GROUND = 1<<1;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final AssetManager assetManager;
    private final OrthographicCamera orthographicCamera;
    public GameScreen(final Bomberman contextBomberman){
        super(contextBomberman);

        mapRenderer = new OrthogonalTiledMapRenderer(null,UNIT_SCALE,context.getSpriteBatch());
        this.assetManager = context.getAssetManager();
        this.orthographicCamera = context.getOrthographicCamera();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();


        final TiledMap tiledMap = assetManager.get("map/stage1_1.tmx",TiledMap.class);
        mapRenderer.setMap(tiledMap);
        map = new Map(tiledMap);
        map.parceCollisionLayer();
        Vector2 positon = map.parcePlayerStartLayer();
        
        bodyDef.position.set(positon);
        bodyDef.gravityScale = 1;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND; 
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f,0.5f,player.getLocalCenter(), 0);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();

        spawnCollisionAreas();
    };
    private void resetBodieAndFixture() {
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale=1;
        bodyDef.type = BodyType.StaticBody;
        bodyDef.fixedRotation = false;

        fixtureDef.density = 0 ;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0; 
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = 0x0001;
        fixtureDef.filter.maskBits = -1;
        fixtureDef.shape = null;
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
        ScreenUtils.clear(0,0,0,1);
        final float speedX;
        final float speedY;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speedX = -3;
        }else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speedX = 3;
        }else{
            speedX=0 ;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speedY = -3;
        }else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedY = 3;
        }else{
            speedY=0 ;
        }

        player.applyLinearImpulse(
            (speedX - player.getLinearVelocity().x) *player.getMass(),
            (speedY - player.getLinearVelocity().y) * player.getMass(),
            player.getWorldCenter().x, player.getWorldCenter().y,true
        );
        if(Gdx.input.isKeyPressed(Input.Keys.G)) {
            context.setScreen(ScreenType.LOADING);
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

}
