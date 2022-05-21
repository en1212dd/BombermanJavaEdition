package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bormberman.Bomberman;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.B2DComponent;
import com.bormberman.ecs.components.BomComponent;
import com.bormberman.ecs.components.RemoveComponent;
import com.bormberman.ui.AnimationType;

public class ExplotionSystem extends IteratingSystem{
    private ESCEngine engine;

    public ExplotionSystem(Bomberman context, ESCEngine engine) {
        super(Family.all(BomComponent.class, B2DComponent.class, AnimationComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final BomComponent bomComponent = ESCEngine.B_COMPONENT_MAPPER.get(entity);
        final B2DComponent b2dComponent = ESCEngine.B2_COMPONENT_MAPPER.get(entity);
        final AnimationComponent animationComponent = ESCEngine.A_COMPONENT_MAPPER.get(entity);

        if (bomComponent.timeOfExplote > 2.5) {
            bomComponent.timeOfExplote -= deltaTime;
        } else {
            animationComponent.aniType = AnimationType.BOM_TIMEOUT;
            if (bomComponent.timeOfExplote>0) {
                bomComponent.timeOfExplote-= deltaTime;
            }else{
                engine.createFire(b2dComponent.body.getPosition(), b2dComponent.width, b2dComponent.heigth, "UP");
                //engine.createFire(b2dComponent.body.getPosition(), b2dComponent.width, b2dComponent.heigth, "RIGTH");
                //engine.createFire(b2dComponent.body.getPosition(), b2dComponent.width, b2dComponent.heigth, "DOWN");
                //engine.createFire(b2dComponent.body.getPosition(), b2dComponent.width, b2dComponent.heigth, "LEFT");
                entity.add(((ESCEngine) getEngine()).createComponent(RemoveComponent.class));
            }
        }

    }


}
