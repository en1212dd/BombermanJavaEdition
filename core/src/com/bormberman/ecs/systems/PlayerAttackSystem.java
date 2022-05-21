package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bormberman.Bomberman;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.ecs.components.B2DComponent;
import com.bormberman.ecs.components.PlayerComponent;
import com.bormberman.input.GameKeys;
import com.bormberman.input.InputListener;
import com.bormberman.input.InputManager;

public class PlayerAttackSystem extends IteratingSystem implements InputListener {
    private boolean atack = false;
    private long nextBom = 0;
    private long time;
    private long coolDown;
    private ESCEngine engine;

    public PlayerAttackSystem(Bomberman context, ESCEngine engine) {
        super(Family.all(PlayerComponent.class, B2DComponent.class).get());
        context.getInputManager().addInputListener(this);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PlayerComponent playerComponent = ESCEngine.P_COMPONENT_MAPPER.get(entity);
        final B2DComponent b2dComponent = ESCEngine.B2_COMPONENT_MAPPER.get(entity);
        time = System.currentTimeMillis();
        coolDown = playerComponent.timeToRecharge;
        if (time > nextBom + coolDown && atack) {
            engine.createBom(b2dComponent.body.getPosition(), b2dComponent.width, b2dComponent.heigth);
            atack = false;
            nextBom = time;
        }

    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        switch (key) {
            case ATTACK:
                if (time > nextBom + coolDown) {
                    atack = true;
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {

    }

}
