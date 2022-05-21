package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bormberman.ecs.components.RemoveComponent;

public class RemoveSystem extends IteratingSystem{

    public RemoveSystem() {
        super(Family.all(RemoveComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        getEngine().removeEntity(entity);
    }
    
}
