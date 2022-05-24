package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.bormberman.Bomberman;
import com.bormberman.WorlContactListener;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.B2DComponent;
import com.bormberman.ecs.components.EnemyComponent;
import com.bormberman.ecs.components.PlayerComponent;

public class EnemyMovementSystem extends IteratingSystem implements WorlContactListener{
       private int xFactor = 1;
       private int yFactor = 1;
    public EnemyMovementSystem(Bomberman context) {
        super(Family.all(EnemyComponent.class, B2DComponent.class).get());
        context.getWorldContactListener().addCollisionGround(this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final B2DComponent b2dComponent = ESCEngine.B2_COMPONENT_MAPPER.get(entity);
        final EnemyComponent enemyComponent = ESCEngine.E_COMPONENT_MAPPER.get(entity);
        b2dComponent.body.applyLinearImpulse(
                (xFactor * enemyComponent.velocity.x - b2dComponent.body.getLinearVelocity().x)
                        * b2dComponent.body.getMass(),
                (yFactor * enemyComponent.velocity.y - b2dComponent.body.getLinearVelocity().y)
                        * b2dComponent.body.getMass(),
                b2dComponent.body.getWorldCenter().x, b2dComponent.body.getWorldCenter().y, true);
    }

    @Override
    public void colisionEntity(Entity entityA, Entity entityB) {
        if(entityB.getComponent(EnemyComponent.class) != null ||entityB.getComponent(PlayerComponent.class) != null) {
            
            int number = MathUtils.random(0, 12);
            if (number<=3) {
                xFactor = 1;
                yFactor =0 ;
            }else if (number<= 6) {
                xFactor = -1;
                yFactor =0 ;
            }else if(number<=9){
                xFactor =0;
                yFactor =1;
            }else{
                xFactor =0;
                yFactor =-1;
            }
        }
    }



}

