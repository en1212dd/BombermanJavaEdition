package com.bormberman.ecs.systems;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bormberman.Bomberman;
import com.bormberman.HudListener;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.PlayerComponent;
import com.bormberman.ecs.components.PlayerDieComponent;
import com.bormberman.ui.AnimationType;
import com.bormberman.ui.GameUi;

public class TimeSystem extends IteratingSystem implements HudListener{

    private LocalTime date = LocalTime.of(0,3,0);
    private ESCEngine engine;
    private Bomberman context;
    private String TAG = this.getClass().getSimpleName();
    public TimeSystem(Bomberman context, ESCEngine engine) {
        super(Family.all(PlayerComponent.class,AnimationComponent.class).get());
        context.getHudManager().addListener(TAG, this);
        this.context = context;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final AnimationComponent animationComponent = ESCEngine.A_COMPONENT_MAPPER.get(entity);
        context.getHudManager().star(TAG);
        if (date.equals(LocalTime.of(0, 0,0))) {
            animationComponent.aniType = AnimationType.BOMBERMAN_DIE;
            entity.remove(PlayerComponent.class);
            entity.add(engine.createComponent(PlayerDieComponent.class));

        }
        
    }

    @Override
    public void changeData(GameUi hud) {
        boolean action = true;
       hud.time.setText("");
       date = date.minusNanos(10000000);
       if (date.equals(LocalTime.of(0, 0, 0))) {
           action = false;
       }else if (action && !date.equals(LocalTime.of(0, 0, 0)) ) {
            hud.time.setText(date.format(DateTimeFormatter.ofPattern("mm:ss")));
       }
    }
    
}
