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

public class PlayerMovementSystem extends IteratingSystem implements InputListener {
    private int xFactor;
    private int yFactor;

    public PlayerMovementSystem(Bomberman context) {
        super(Family.all(PlayerComponent.class, B2DComponent.class).get());
        context.getInputManager().addInputListener(this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PlayerComponent playerComponent = ESCEngine.P_COMPONENT_MAPPER.get(entity);
        final B2DComponent b2dComponent = ESCEngine.B2_COMPONENT_MAPPER.get(entity);

        b2dComponent.body.applyLinearImpulse(
                (xFactor * playerComponent.speed.x - b2dComponent.body.getLinearVelocity().x)
                        * b2dComponent.body.getMass(),
                (yFactor * playerComponent.speed.y - b2dComponent.body.getLinearVelocity().y)
                        * b2dComponent.body.getMass(),
                b2dComponent.body.getWorldCenter().x, b2dComponent.body.getWorldCenter().y, true);
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        switch (key) {
            case UP:
                yFactor = 1;
                break;
            case DOWN:
                yFactor = -1;
                break;
            case LEFT:
                xFactor = -1;
                break;
            case RIGTH:
                xFactor = 1;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {

        switch (key) {
            case UP:
                yFactor = manager.isKeyPressed(GameKeys.DOWN) ? -1 : 0;
                break;
            case DOWN:
                yFactor = manager.isKeyPressed(GameKeys.UP) ? 1 : 0;
                break;
            case LEFT:
                xFactor = manager.isKeyPressed(GameKeys.RIGTH) ? 1 : 0;
                break;
            case RIGTH:
                xFactor = manager.isKeyPressed(GameKeys.LEFT) ? -1 : 0;
                break;
            default:
                break;
        }

    }

}
