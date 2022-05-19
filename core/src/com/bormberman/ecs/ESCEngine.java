package com.bormberman.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.bormberman.Bomberman;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.B2DComponent;
import com.bormberman.ecs.components.BomComponent;
import com.bormberman.ecs.components.EnemyComponent;
import com.bormberman.ecs.components.GameObjectComponent;
import com.bormberman.ecs.components.PlayerComponent;
import com.bormberman.ecs.systems.AnimationMoveEnemySystem;
import com.bormberman.ecs.systems.AnimationSystem;
import com.bormberman.ecs.systems.EnemyMovementSystem;
import com.bormberman.ecs.systems.PlayerAnimationSystem;
import com.bormberman.ecs.systems.PlayerAttackSystem;
import com.bormberman.ecs.systems.PlayerMovementSystem;
import com.bormberman.map.GameObject;
import com.bormberman.ui.AnimationType;

import static com.bormberman.Bomberman.BIT_GROUND;
import static com.bormberman.Bomberman.BIT_PLAYER;
import static com.bormberman.Bomberman.BODY_DEF;
import static com.bormberman.Bomberman.FIXTURE_DEF;
import static com.bormberman.Bomberman.UNIT_SCALE;
import static com.bormberman.Bomberman.resetBodieAndFixture;

public class ESCEngine extends PooledEngine{
    public static final ComponentMapper<PlayerComponent> P_COMPONENT_MAPPER = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> B2_COMPONENT_MAPPER = ComponentMapper.getFor(B2DComponent.class);
    public static final ComponentMapper<AnimationComponent> A_COMPONENT_MAPPER = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<EnemyComponent> E_COMPONENT_MAPPER = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<GameObjectComponent> GO_COMPONENT_MAPPER = ComponentMapper.getFor(GameObjectComponent.class);
    public static final ComponentMapper<BomComponent> B_COMPONENT_MAPPER = ComponentMapper.getFor(BomComponent.class);
    private final World world;
    public ESCEngine(Bomberman context) {
        super();
        world = context.getWorld();

        this.addSystem(new PlayerMovementSystem(context));
        this.addSystem(new AnimationSystem(context));
        this.addSystem(new PlayerAnimationSystem(context));
        this.addSystem(new EnemyMovementSystem(context));
        this.addSystem(new AnimationMoveEnemySystem(context));
        this.addSystem(new PlayerAttackSystem(context, this));
    }
    public void createPlayer(final Vector2 startSpawnLocation, final float width,final float heigth) {
        final Entity player = this.createEntity();
        //Add Components
        final PlayerComponent playerComponent =this.createComponent(PlayerComponent.class);
        playerComponent.timeToRecharge = 3;
        playerComponent.speed.set(3, 3);
        player.add(playerComponent);
        //box2d component
        resetBodieAndFixture();
        final B2DComponent b2dComponent = this.createComponent(B2DComponent.class);
        BODY_DEF.position.set(startSpawnLocation.x,startSpawnLocation.y);
        BODY_DEF.fixedRotation = true;
        BODY_DEF.type = BodyType.DynamicBody;
        b2dComponent.body = world.createBody(BODY_DEF);
        b2dComponent.body.setUserData("PLAYER");
        b2dComponent.width = width;
        b2dComponent.heigth = heigth;
        b2dComponent.renderPosition.set(b2dComponent.body.getPosition());

        FIXTURE_DEF.filter.categoryBits = BIT_PLAYER;
        FIXTURE_DEF.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(width ,heigth ,b2dComponent.body.getLocalCenter(), 0);
        FIXTURE_DEF.shape = pShape;
        b2dComponent.body.createFixture(FIXTURE_DEF);
        pShape.dispose();

        player.add(b2dComponent);
        //animation
        final AnimationComponent animationComponent= this.createComponent(AnimationComponent.class);
        animationComponent.aniType = AnimationType.BOMBERMAN_DOWN;
        animationComponent.width = 16 *UNIT_SCALE;
        animationComponent.heigth = 16 *UNIT_SCALE;
        player.add(animationComponent);

        this.addEntity(player);
    }
    public void createEnemy(final Vector2 position, final float width, final float heigth,final int lives, final Vector2 velocity) {
        final Entity enemy = this.createEntity();
        // add Components
        final EnemyComponent enemyComponent = this.createComponent(EnemyComponent.class);
        enemyComponent.lives = lives;
        enemyComponent.velocity.set(velocity);
        enemy.add(enemyComponent);
        //b2dComponent
        resetBodieAndFixture();
        final B2DComponent b2dComponent = this.createComponent(B2DComponent.class);
        BODY_DEF.position.set(position.x,position.y);
        BODY_DEF.fixedRotation = true;
        BODY_DEF.type = BodyType.DynamicBody;
        b2dComponent.body = world.createBody(BODY_DEF);
        b2dComponent.body.setUserData("ENEMY");
        b2dComponent.width = width;
        b2dComponent.heigth = heigth;
        b2dComponent.renderPosition.set(b2dComponent.body.getPosition());

        FIXTURE_DEF.filter.categoryBits = BIT_PLAYER;
        FIXTURE_DEF.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(width ,heigth ,b2dComponent.body.getLocalCenter(), 0);
        FIXTURE_DEF.shape = pShape;
        b2dComponent.body.createFixture(FIXTURE_DEF);
        pShape.dispose();

        enemy.add(b2dComponent);
        //animation
        final AnimationComponent animationComponent= this.createComponent(AnimationComponent.class);
        animationComponent.aniType = AnimationType.ENEMY_DOWN;
        animationComponent.width = 16 *UNIT_SCALE;
        animationComponent.heigth = 16 *UNIT_SCALE;

        enemy.add(animationComponent);

        this.addEntity(enemy);
    }
    public void createGameObjects(final GameObject gameObject){
        final Entity gameObEntity = this.createEntity();
        //game object component
        final GameObjectComponent gameObjectComponent = this.createComponent(GameObjectComponent.class);
        gameObjectComponent.animationIndex = gameObject.getAnimationIndex();
        gameObjectComponent.type = gameObject.getType();
        gameObEntity.add(gameObjectComponent);
        //Animtion
        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        animationComponent.aniType = null;
        animationComponent.width = gameObject.getWidth();
        animationComponent.heigth = gameObject.getHeight();
        gameObEntity.add(animationComponent);
        //B2dcomponent
        resetBodieAndFixture();
        final float halfW = gameObject.getWidth() * 0.5f;
        final float halfH = gameObject.getHeight() * 0.5f;
        final B2DComponent b2dComponent = this.createComponent(B2DComponent.class);
        BODY_DEF.position.set(gameObject.getPosition().x + halfW, gameObject.getPosition().y + halfH);
        BODY_DEF.fixedRotation = true;
        BODY_DEF.type = BodyType.StaticBody;
        b2dComponent.body = world.createBody(BODY_DEF);
        b2dComponent.body.setUserData("GAMEOBJECT");
        b2dComponent.width = gameObject.getWidth();
        b2dComponent.heigth = gameObject.getHeight();
        b2dComponent.renderPosition.set(b2dComponent.body.getPosition());


        FIXTURE_DEF.filter.categoryBits = BIT_PLAYER;
        FIXTURE_DEF.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(halfW ,halfH);
        FIXTURE_DEF.shape = pShape;
        b2dComponent.body.createFixture(FIXTURE_DEF);
        pShape.dispose();
        gameObEntity.add(b2dComponent);

        this.addEntity(gameObEntity);
    }
    public void createBom(Vector2 position, float width, float heigth) {
        final Entity bomEntity = this.createEntity();
        //BomComponent
        final BomComponent bomComponent =  this.createComponent( BomComponent.class);
        bomComponent.timeOfExplote= 5;
        bomEntity.add(bomComponent);
        //B2dcomponent
        resetBodieAndFixture();
        final B2DComponent b2dComponent = this.createComponent(B2DComponent.class);
        BODY_DEF.position.set(position);
        BODY_DEF.fixedRotation = true;
        BODY_DEF.type = BodyType.StaticBody;
        b2dComponent.body = world.createBody(BODY_DEF);
        b2dComponent.body.setUserData("BOM");
        b2dComponent.width = width ;
        b2dComponent.heigth = heigth;
        b2dComponent.renderPosition.set(b2dComponent.body.getPosition());

        FIXTURE_DEF.filter.categoryBits = BIT_PLAYER;
        FIXTURE_DEF.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(width,heigth);
        FIXTURE_DEF.shape = pShape;
        b2dComponent.body.createFixture(FIXTURE_DEF);
        pShape.dispose();
        bomEntity.add(b2dComponent);
        //Animation
        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        animationComponent.aniType = AnimationType.BOM_IDLE;
        animationComponent.heigth = 16 *UNIT_SCALE;
        animationComponent.width = 16 *UNIT_SCALE;
        bomEntity.add(animationComponent);

        this.addEntity(bomEntity);
    }



}
