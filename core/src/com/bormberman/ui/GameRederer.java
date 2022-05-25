package com.bormberman.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bormberman.Bomberman;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.B2DComponent;
import com.bormberman.ecs.components.GameObjectComponent;
import com.bormberman.map.Map;
import com.bormberman.map.MapListener;

import static com.bormberman.Bomberman.UNIT_SCALE;

import java.util.EnumMap;

public class GameRederer implements Disposable, MapListener{
    public static final String TAG = GameRederer.class.getSimpleName();


    private final OrthographicCamera gameCamera;
    private final FitViewport viewport;
    private final SpriteBatch spriteBatch;
    private final AssetManager assetManager;
    private final EnumMap<AnimationType, Animation<Sprite>> animationCache;

    private final ImmutableArray<Entity> animatedEntities;
    private final ImmutableArray<Entity> gameObjectEntity;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private IntMap<Animation<Sprite>> mapAnimation;
    private final Array<TiledMapTileLayer> tileMapLayers;

    private final GLProfiler profiler;
    private final Box2DDebugRenderer box2dDebugRenderer;
    private final World world;

    public GameRederer(Bomberman context){
        assetManager = context.getAssetManager();
        viewport = context.getScreenViewport();
        gameCamera = context.getOrthographicCamera();
        spriteBatch = context.getSpriteBatch();
        animationCache = new EnumMap<>(AnimationType.class);

        gameObjectEntity = context.getEscEngine().getEntitiesFor(Family.all(GameObjectComponent.class, B2DComponent.class, AnimationComponent.class).get());
        animatedEntities =   context.getEscEngine().getEntitiesFor(Family.all(AnimationComponent.class,B2DComponent.class).exclude(GameObjectComponent.class).get());
        context.getMapManager().addMapListener(this);

        tileMapLayers = new Array<>();

        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, spriteBatch);

        profiler = new GLProfiler(Gdx.graphics);
        profiler.disable();
        if (profiler.isEnabled()) {
            box2dDebugRenderer = new Box2DDebugRenderer();
            world = context.getWorld();
        }else{
            box2dDebugRenderer = null;
            world = null;
        }

    }
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply(true);

        mapRenderer.setView(gameCamera);
        spriteBatch.begin();
        if (mapRenderer.getMap() != null) {
            AnimatedTiledMapTile.updateAnimationBaseTime();
            for (TiledMapTileLayer layer : tileMapLayers) {
                mapRenderer.renderTileLayer(layer);
            }
        }
        for (final Entity entity : gameObjectEntity) {
            renderGameObjects(entity,delta);
        }
        for (final Entity entity : animatedEntities) {
            renderEntities(entity,delta);
        }
        spriteBatch.end();
        if (profiler.isEnabled()) {
            Gdx.app.debug(TAG, "Vinculos "+ profiler.getTextureBindings());
            Gdx.app.debug(TAG, "LLamadas de dibujo "+ profiler.getDrawCalls());
            profiler.reset();

            box2dDebugRenderer.render(world, gameCamera.combined);
        }
    }
    private void renderGameObjects(Entity entity, float delta) {
        final B2DComponent b2dComponent = ESCEngine.B2_COMPONENT_MAPPER.get(entity);
        final AnimationComponent animationComponent = ESCEngine.A_COMPONENT_MAPPER.get(entity);
        final GameObjectComponent gameObjectComponent = ESCEngine.GO_COMPONENT_MAPPER.get(entity);

        if (gameObjectComponent.animationIndex != -1) {
            final Animation<Sprite> animation = mapAnimation.get(gameObjectComponent.animationIndex);
            final Sprite frame = animation.getKeyFrame(animationComponent.aniTime);
            frame.setBounds(b2dComponent.renderPosition.x-b2dComponent.width *0.5f, b2dComponent.renderPosition.y - b2dComponent.heigth*0.5f, animationComponent.width, animationComponent.heigth);
            frame.setOriginCenter();
            frame.setRotation(b2dComponent.body.getAngle() * MathUtils.radDeg);
            frame.draw(spriteBatch);
        }
    }
    private void renderEntities(Entity entity, float delta) {
        final B2DComponent b2dComponent = ESCEngine.B2_COMPONENT_MAPPER.get(entity);
        final AnimationComponent aComponent = ESCEngine.A_COMPONENT_MAPPER.get(entity);
        if (aComponent.aniType != null) {
            final Animation<Sprite> animation = getAnimation(aComponent.aniType);
            final Sprite frame = animation.getKeyFrame(aComponent.aniTime);
            b2dComponent.renderPosition.lerp(b2dComponent.body.getPosition(), delta);
            frame.setBounds(b2dComponent.renderPosition.x-b2dComponent.width,b2dComponent.renderPosition.y-b2dComponent.heigth, aComponent.width, aComponent.heigth);
            frame.draw(spriteBatch);
        }
    }
    private Animation<Sprite> getAnimation(AnimationType aniType) {
        Animation<Sprite> animation =animationCache.get(aniType);
        if (animation==null) {
            Gdx.app.debug(TAG, "Creando una nueva animacion de tipo: "+aniType);   
            final AtlasRegion region = assetManager.get(aniType.getAtlasPath(),TextureAtlas.class).findRegion(aniType.getAtalsKey());
            TextureRegion[][]textureRegions;
            if (aniType.equals(AnimationType.BOM_IDLE) || aniType.equals(AnimationType.BOM_TIMEOUT) || aniType.equals(AnimationType.GAME_OBJECT_DEST) || aniType.equals(AnimationType.ENEMY_DIE)) {
                textureRegions= region.split(16, 16);
            }else if (aniType.equals(AnimationType.FIRE)){
                textureRegions= region.split(48, 48);
            }else{
                textureRegions= region.split(16, 24);
            }
            animation = new Animation<Sprite>(aniType.getFrameTime(), getKeyFrame(textureRegions[aniType.getRowIndex()]));
            if (aniType.equals(AnimationType.FIRE) || aniType.equals(AnimationType.GAME_OBJECT_DEST) || aniType.equals(AnimationType.ENEMY_DIE) || aniType.equals(AnimationType.BOMBERMAN_DIE)) {
                animation.setPlayMode(Animation.PlayMode.NORMAL);
            }else{

                 animation.setPlayMode(Animation.PlayMode.LOOP);
            }
            animationCache.put(aniType, animation);
        }
        return animation;
    }
    private Sprite[] getKeyFrame(TextureRegion[] textureRegions) {
        final Sprite[] keyFrame = new Sprite[textureRegions.length];
        int i = 0;
        for (TextureRegion region : textureRegions) {
            final Sprite sprite = new Sprite(region);
            sprite.setOriginCenter();
            keyFrame[i++]=sprite;
        }
        return keyFrame;
    }
    @Override
    public void dispose() {
        if (box2dDebugRenderer!=null) {
            box2dDebugRenderer.dispose();
        }
        mapRenderer.dispose();
    }
    @Override
    public void mapChange(Map map) {
        mapRenderer.setMap(map.getTiledMap());
        map.getTiledMap().getLayers().getByType(TiledMapTileLayer.class,tileMapLayers);
        mapAnimation = map.getMapAnimation();
    }
    
}
