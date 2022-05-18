package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bormberman.Bomberman;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.B2DComponent;
import com.bormberman.ecs.components.EnemyComponent;

public class EnemyMovementSystem extends IteratingSystem {

    public EnemyMovementSystem(Bomberman context) {
        super(Family.all(EnemyComponent.class, B2DComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final EnemyComponent enemyComponent = ESCEngine.E_COMPONENT_MAPPER.get(entity);
        final B2DComponent b2dComponent = ESCEngine.B2_COMPONENT_MAPPER.get(entity);
        b2dComponent.body.applyLinearImpulse(
                (enemyComponent.velocity.x - b2dComponent.body.getLinearVelocity().x)
                        * b2dComponent.body.getMass(),
                (enemyComponent.velocity.y - b2dComponent.body.getLinearVelocity().y)
                        * b2dComponent.body.getMass(),
                b2dComponent.body.getWorldCenter().x, b2dComponent.body.getWorldCenter().y, true);
    }



}

