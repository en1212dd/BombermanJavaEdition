package com.bormberman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bormberman.Bomberman;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

	public static final short BIT_CIRCLE = 1<<0;
	public static final short BIT_BOX = 1<<1;
	public static final short BIT_GROUND = 1<<2;
    public static final float UNIT_SCALE=1/16f;
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

        bodyDef.position.set(4.5f, 15);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_CIRCLE;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_BOX;
        CircleShape cShape = new CircleShape();
        cShape.setRadius(0.5f);
        fixtureDef.shape= cShape;
        body.createFixture(fixtureDef);
        cShape.dispose();

        bodyDef.position.set(5.2f, 6);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_BOX;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_CIRCLE;
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();

        bodyDef.position.set(4.5f, 2);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyType.StaticBody;
        body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.4f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits =-1;
        pShape = new PolygonShape();
        pShape.setAsBox(4, 0.5f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();
    };

    @Override
    public void show() {
        mapRenderer.setMap(assetManager.get("map/stage1_1.tmx",TiledMap.class));
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        if(Gdx.input.isKeyPressed(Input.Keys.G)) {
            context.setScreen(ScreenType.LOADING);
        }
        viewport.apply(true);
        mapRenderer.setView(orthographicCamera);
        mapRenderer.render();
        box2dDebugRenderer.render(world, viewport.getCamera().combined);
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }

}
