package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bormberman.Bomberman;
import com.bormberman.WorlContactListener;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.FireComponent;
import com.bormberman.ecs.components.RemoveComponent;

public class BomDestructionSystem extends IteratingSystem implements WorlContactListener{
    private ESCEngine engine ;
    public BomDestructionSystem(Bomberman context, ESCEngine engine) {
        super(Family.all(RemoveComponent.class).get());
        this.engine = engine;
        context.getWorldContactListener().addCollisionFire(this);
        context.getWorldContactListener().addCollisionGameobject(this);
    }

    @Override
    public void colisionEntity(Entity entityA, Entity entityB) {
        if (entityB.getComponent(FireComponent.class) != null) {
            entityA.add(engine.createComponent(RemoveComponent.class));
        }else if ( entityA.getComponent(FireComponent.class) != null) {
            entityB.add(engine.createComponent(RemoveComponent.class));
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        
    }
    
}
