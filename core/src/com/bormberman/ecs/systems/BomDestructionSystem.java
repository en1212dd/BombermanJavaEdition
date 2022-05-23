package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bormberman.Bomberman;
import com.bormberman.WorlContactListener;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.FireComponent;
import com.bormberman.ecs.components.GameObjectComponent;
import com.bormberman.ecs.components.GameObjectDESTComponent;
import com.bormberman.ui.AnimationType;

public class BomDestructionSystem extends IteratingSystem implements WorlContactListener{
    private ESCEngine engine;
    public BomDestructionSystem(Bomberman context, ESCEngine engine) {
        super(Family.all(GameObjectComponent.class, AnimationComponent.class).get());
        this.engine = engine;
        context.getWorldContactListener().addCollisionFire(this);
        context.getWorldContactListener().addCollisionGameobject(this);
    }

    @Override
    public void colisionEntity(Entity entityA, Entity entityB) {
        
        if (entityB.getComponent(FireComponent.class) != null) {
            if (entityA.getComponent(GameObjectComponent.class)!= null) {
                final AnimationComponent animationComponent = ESCEngine.A_COMPONENT_MAPPER.get(entityA);
                entityA.remove(GameObjectComponent.class);
                entityA.add(engine.createComponent(GameObjectDESTComponent.class));
                animationComponent.aniTime = AnimationType.GAME_OBJECT_DEST.getFrameTime();
                animationComponent.aniType = AnimationType.GAME_OBJECT_DEST;
            }
        }else if ( entityA.getComponent(FireComponent.class) != null) {
            if (entityB.getComponent(GameObjectComponent.class)!= null) {
                final AnimationComponent animationComponent = ESCEngine.A_COMPONENT_MAPPER.get(entityB);
                entityB.remove(GameObjectComponent.class);
                entityB.add(engine.createComponent(GameObjectDESTComponent.class));
                animationComponent.aniTime = AnimationType.GAME_OBJECT_DEST.getFrameTime();
                animationComponent.aniType = AnimationType.GAME_OBJECT_DEST;
            }
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        
    }
    
}
