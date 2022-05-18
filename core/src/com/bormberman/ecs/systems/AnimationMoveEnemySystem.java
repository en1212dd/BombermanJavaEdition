package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.bormberman.Bomberman;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.B2DComponent;
import com.bormberman.ecs.components.EnemyComponent;
import com.bormberman.ui.AnimationType;

public class AnimationMoveEnemySystem extends IteratingSystem {

    public AnimationMoveEnemySystem(Bomberman context) {
        super(Family.all(AnimationComponent.class, EnemyComponent.class, B2DComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final B2DComponent b2dComponent = ESCEngine.B2_COMPONENT_MAPPER.get(entity);
        final AnimationComponent animationComponent = ESCEngine.A_COMPONENT_MAPPER.get(entity);

        Vector2 linearVelocity = b2dComponent.body.getLinearVelocity();
        if (linearVelocity.equals(Vector2.Zero)) {
            animationComponent.aniTime = 0;
        } else if (linearVelocity.x > 0) {
            animationComponent.aniType = AnimationType.ENEMY_RIGHT;
        } else if (linearVelocity.x < 0) {
            animationComponent.aniType = AnimationType.ENEMY_LEFT;
        } else if (linearVelocity.y > 0) {
            animationComponent.aniType = AnimationType.ENEMY_UP;
        } else if (linearVelocity.y < 0) {
            animationComponent.aniType = AnimationType.ENEMY_DOWN;
        }

    }

}
