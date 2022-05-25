package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bormberman.Bomberman;
import com.bormberman.WorlContactListener;
import com.bormberman.audio.AudioType;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.EnemyComponent;
import com.bormberman.ecs.components.EnemyDieComponent;
import com.bormberman.ecs.components.FireComponent;
import com.bormberman.ui.AnimationType;

public class EnemyDieSystem extends IteratingSystem implements WorlContactListener{
    private ESCEngine engine;
    private Bomberman context;
    public EnemyDieSystem(Bomberman context,ESCEngine engine ) {
        super(Family.all(EnemyComponent.class, AnimationComponent.class).get());
        context.getWorldContactListener().addCollisionFire(this);
        context.getWorldContactListener().addCollisionEnemy(this);
        this.context = context;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final EnemyComponent enemyComponent = ESCEngine.E_COMPONENT_MAPPER.get(entity);
        final AnimationComponent animationComponent = ESCEngine.A_COMPONENT_MAPPER.get(entity);
        if (enemyComponent.lives <= 0) {
            entity.remove(EnemyComponent.class);
            entity.add(engine.createComponent(EnemyDieComponent.class));
            animationComponent.aniType = AnimationType.ENEMY_DIE;
        }
    }

    @Override
    public void colisionEntity(Entity entityA, Entity entityB) {
        if (entityA.getComponent(FireComponent.class)!=null) {
            if (entityB.getComponent(EnemyComponent.class) != null) {
                final EnemyComponent enemyComponent = ESCEngine.E_COMPONENT_MAPPER.get(entityB);
                context.getAudioManager().playAudio(AudioType.DIE_ENEMY);
                enemyComponent.lives --;
            }
        }else if (entityB.getComponent(FireComponent.class)!=null) {
            if (entityA.getComponent(EnemyComponent.class) != null) {
                final EnemyComponent enemyComponent = ESCEngine.E_COMPONENT_MAPPER.get(entityA);
                context.getAudioManager().playAudio(AudioType.DIE_ENEMY);
                enemyComponent.lives --;
            }
        }
    }
    
}
