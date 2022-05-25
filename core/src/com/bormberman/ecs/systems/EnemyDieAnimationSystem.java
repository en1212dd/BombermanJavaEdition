package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.EnemyDieComponent;
import com.bormberman.ecs.components.RemoveComponent;

public class EnemyDieAnimationSystem extends IteratingSystem{
    private ESCEngine engine;
    public EnemyDieAnimationSystem( ESCEngine engine) {
        super(Family.all(EnemyDieComponent.class, AnimationComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Timer.schedule(new Task() {

            @Override
            public void run() {
                entity.add(engine.createComponent(RemoveComponent.class));
                
            }
            
        },1f );
    }
    
}
