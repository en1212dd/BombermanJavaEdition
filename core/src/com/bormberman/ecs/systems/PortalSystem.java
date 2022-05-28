package com.bormberman.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.bormberman.Bomberman;
import com.bormberman.WorlContactListener;
import com.bormberman.audio.AudioType;
import com.bormberman.ecs.components.AnimationComponent;
import com.bormberman.ecs.components.GameObjectComponent;
import com.bormberman.ecs.components.PlayerComponent;
import com.bormberman.map.GameObjectType;
import com.bormberman.map.Map;
import com.bormberman.map.MapListener;
import com.bormberman.map.MapType;

public class PortalSystem extends IteratingSystem implements WorlContactListener, MapListener {
    private Bomberman context;
    private PortalSystem portalSystem = this;

    public PortalSystem(Bomberman context) {
        super(Family.all(GameObjectComponent.class, AnimationComponent.class).get());
        context.getWorldContactListener().addCollisionPlayer(this);
        context.getWorldContactListener().addCollisionGameobject(this);
        Timer.schedule(new Task() {

            @Override
            public void run() {
                context.getMapManager().addMapListener(portalSystem);
            }

        }, 10f);
        this.context = context;
    }

    @Override
    public void colisionEntity(Entity entityA, Entity entityB) {

        if (entityB.getComponent(PlayerComponent.class) != null) {
            if (entityA.getComponent(GameObjectComponent.class) != null) {
                if (entityA.getComponent(GameObjectComponent.class).type.equals(GameObjectType.PORTAL)) {
                    Timer.schedule(new Task() {

                        @Override
                        public void run() {
                            context.getAudioManager().playAudio(AudioType.TELEPORT);
                            context.getMapManager().setMap(MapType.MAP_2);
                        }

                    },0.5f);
                }
            }
        } else if (entityA.getComponent(PlayerComponent.class) != null) {
            if (entityB.getComponent(GameObjectComponent.class) != null) {
                if (entityB.getComponent(GameObjectComponent.class).type.equals(GameObjectType.PORTAL)) {
                    Timer.schedule(new Task() {

                        @Override
                        public void run() {
                            context.getAudioManager().playAudio(AudioType.TELEPORT);
                            context.getMapManager().setMap(MapType.MAP_2);
                        }

                    }, 0.5f);
                }
            }
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    @Override
    public void mapChange(Map map) {
        // TODO Auto-generated method stub

    }

}
