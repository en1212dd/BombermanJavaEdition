package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.bormberman.Bomberman;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.FireComponent;
import com.bormberman.ecs.components.RemoveComponent;

public class FireSystem extends IteratingSystem{
    private ESCEngine engine;
    public FireSystem( Bomberman context, ESCEngine engine) {
        super(Family.all(FireComponent.class, AnimationComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final AnimationComponent animationComponent = ESCEngine.A_COMPONENT_MAPPER.get(entity);
        if (animationComponent.aniTime == 0) {
            Timer.schedule(new Task() {

                @Override
                public void run() {
                    entity.add(engine.createComponent(RemoveComponent.class));
                }
                
            }, 0.85f);
        }
    }
    
}
