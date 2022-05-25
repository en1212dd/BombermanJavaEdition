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
import com.bormberman.ecs.components.PlayerComponent;
import com.bormberman.ecs.components.PlayerDieComponent;
import com.bormberman.ui.AnimationType;

public class PlayerDieSystem extends IteratingSystem implements WorlContactListener{
    private ESCEngine engine;
    private Bomberman context;
    public PlayerDieSystem(Bomberman context,ESCEngine engine ) {
        super(Family.all(PlayerComponent.class, AnimationComponent.class).get());
        context.getWorldContactListener().addCollisionFire(this);
        context.getWorldContactListener().addCollisionPlayer(this);
        this.context = context;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PlayerComponent playerComponent = ESCEngine.P_COMPONENT_MAPPER.get(entity);
        final AnimationComponent animationComponent = ESCEngine.A_COMPONENT_MAPPER.get(entity);
        if (playerComponent.lives <= 0) {
            entity.remove(PlayerComponent.class);
            entity.add(engine.createComponent(PlayerDieComponent.class));
            animationComponent.aniType = AnimationType.BOMBERMAN_DIE;
        }
    }

    @Override
    public void colisionEntity(Entity entityA, Entity entityB) {
        if (entityA.getComponent(PlayerComponent.class)!=null) {
            if (entityB.getComponent(EnemyComponent.class) != null) {
                final PlayerComponent playerComponent = ESCEngine.P_COMPONENT_MAPPER.get(entityA);
                context.getAudioManager().playAudio(AudioType.DIE_ENEMY);
                playerComponent.lives --;
            }
        }else if (entityB.getComponent(PlayerComponent.class)!=null) {
            if (entityA.getComponent(EnemyComponent.class) != null) {
                final PlayerComponent playerComponent = ESCEngine.P_COMPONENT_MAPPER.get(entityB);
                context.getAudioManager().playAudio(AudioType.DIE_ENEMY);
                playerComponent.lives --;
            }
        }
    }
    
}

