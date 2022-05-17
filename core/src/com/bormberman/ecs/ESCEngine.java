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
import com.bormberman.ecs.components.PlayerComponent;
import com.bormberman.ecs.systems.AnimationSystem;
import com.bormberman.ecs.systems.PlayerAnimationSystem;
import com.bormberman.ecs.systems.PlayerMovementSystem;
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
    private final World world;
    public ESCEngine(Bomberman context) {
        super();
        world = context.getWorld();

        this.addSystem(new PlayerMovementSystem(context));
        this.addSystem(new AnimationSystem(context));
        this.addSystem(new PlayerAnimationSystem(context));
    }
    public void createPlayer(final Vector2 startSpawnLocation, final float width,final float heigth) {
        final Entity player = this.createEntity();
        //Add Components
        final PlayerComponent playerComponent =this.createComponent(PlayerComponent.class);
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



}
