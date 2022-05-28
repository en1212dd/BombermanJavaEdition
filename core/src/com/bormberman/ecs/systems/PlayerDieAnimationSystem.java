package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.bormberman.Bomberman;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.PlayerDieComponent;
import com.bormberman.ecs.components.RemoveComponent;
import com.bormberman.screens.ScreenType;

public class PlayerDieAnimationSystem extends IteratingSystem{
    private ESCEngine engine;
    private Bomberman context;
    public PlayerDieAnimationSystem(Bomberman context, ESCEngine engine) {
        super(Family.all(PlayerDieComponent.class, AnimationComponent.class).get());
        this.engine = engine;
        this.context = context;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Timer.schedule(new Task() {

            @Override
            public void run() {
                entity.add(engine.createComponent(RemoveComponent.class));
                context.setScreen(ScreenType.GAME_OVER);
            }
            
        },3f );
    }
    
}

