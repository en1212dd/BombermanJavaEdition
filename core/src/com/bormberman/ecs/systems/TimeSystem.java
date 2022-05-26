package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bormberman.HudListener;
import com.bormberman.ecs.components.PlayerComponent;
import com.bormberman.ui.GameUi;

public class TimeSystem extends IteratingSystem implements HudListener{

    public TimeSystem() {
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        
    }

    @Override
    public void changeData(GameUi hud) {
        
    }
    
}
