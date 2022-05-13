package com.bormberman.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.bormberman.Bomberman;
import com.bormberman.ecs.components.B2DComponent;
import com.bormberman.ecs.components.PlayerComponent;
import com.bormberman.ecs.systems.PlayerMovementSystem;

import static com.bormberman.screens.GameScreen.BIT_GROUND;
import static com.bormberman.screens.GameScreen.BIT_PLAYER;

public class ESCEngine extends PooledEngine{
    public static final ComponentMapper<PlayerComponent> P_COMPONENT_MAPPER = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> B2_COMPONENT_MAPPER = ComponentMapper.getFor(B2DComponent.class);

    private final World world;
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    public ESCEngine(Bomberman context) {
        super();
        world = context.getWorld();
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        this.addSystem(new PlayerMovementSystem(context));
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
        bodyDef.position.set(startSpawnLocation.x,startSpawnLocation.y);
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyType.DynamicBody;
        b2dComponent.body = world.createBody(bodyDef);
        b2dComponent.body.setUserData("PLAYER");
        b2dComponent.width = width;
        b2dComponent.heigth = heigth;

        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(width,heigth,b2dComponent.body.getLocalCenter(), 0);
        fixtureDef.shape = pShape;
        b2dComponent.body.createFixture(fixtureDef);
        pShape.dispose();

        player.add(b2dComponent);
        this.addEntity(player);
    }

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


}
