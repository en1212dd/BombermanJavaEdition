package com.bormberman;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

import static com.bormberman.Bomberman.BIT_PLAYER;
import static com.bormberman.Bomberman.BIT_FIRE;
import static com.bormberman.Bomberman.BIT_ENEMY;
import static com.bormberman.Bomberman.BIT_GAMEOBJECT;

public class WorldContactManager implements ContactListener {
    private final Array<WorlContactListener> playerCollisions;
    private final Array<WorlContactListener> enemyCollisions;
    private final Array<WorlContactListener> fireCollisions;
    private final Array<WorlContactListener> gameObjectCollision;

    public WorldContactManager() {
        playerCollisions = new Array<>();
        enemyCollisions = new Array<>();
        fireCollisions = new Array<>();
        gameObjectCollision = new Array<>();
    }
    public void addCollisionPlayer(WorlContactListener listener) {
        playerCollisions.add(listener);
    }
    public void addCollisionEnemy(WorlContactListener listener) {
        enemyCollisions.add(listener);
    }
    public void addCollisionFire(WorlContactListener listener) {
        fireCollisions.add(listener);
    }
    public void addCollisionGameobject(WorlContactListener listener) {
        gameObjectCollision.add(listener);
    }

    @Override
    public void beginContact(Contact contact) {
        final Entity entityA;
        Entity entityB = null;

        final Body bodyA = contact.getFixtureA().getBody();
        final Body bodyB = contact.getFixtureB().getBody();

        final int catFixA = contact.getFixtureA().getFilterData().categoryBits;
        final int catFixB = contact.getFixtureB().getFilterData().categoryBits;

        if ((int) (catFixA & BIT_PLAYER) == BIT_PLAYER) {
            entityA = (Entity) bodyA.getUserData();
            collisionreacTo(entityA, entityB, bodyB, catFixB,playerCollisions, "player");
        } else if ((int) (catFixA & BIT_FIRE) == BIT_FIRE) {
            entityA = (Entity) bodyA.getUserData();
            collisionreacTo(entityA, entityB, bodyB, catFixB, fireCollisions,"Fire");
        }else if ((int) (catFixA & BIT_ENEMY) == BIT_ENEMY) {
            entityA = (Entity) bodyA.getUserData();
            collisionreacTo(entityA, entityB, bodyB, catFixB, enemyCollisions,"enemy");
        }else if((int) (catFixA & BIT_GAMEOBJECT) == BIT_GAMEOBJECT){
            entityA = (Entity) bodyA.getUserData();
            collisionreacTo(entityA, entityB, bodyB, catFixB, gameObjectCollision,"gameObject");
        }

    }

    private void collisionreacTo(Entity entityA, Entity entityB, Body bodyB, int catFixB, Array<WorlContactListener> list, String string) {
            if ((int) (catFixB & BIT_GAMEOBJECT) == BIT_GAMEOBJECT) {
                entityB = (Entity) bodyB.getUserData();
                System.out.println(string + " colisiona con gameObject");
                for (WorlContactListener worlContactListener : list) {
                    worlContactListener.colisionEntity(entityA, entityB);
                }
            }else if ((int) (catFixB & BIT_ENEMY) == BIT_ENEMY) {
                entityB = (Entity) bodyB.getUserData();
                System.out.println(string + " colisiona con enemy");
                for (WorlContactListener worlContactListener : list) {
                    worlContactListener.colisionEntity(entityA, entityB);
                }
            }else if ((int) (catFixB & BIT_FIRE) == BIT_FIRE) {
                entityB = (Entity) bodyB.getUserData();
                System.out.println(string + " colisiona con Fire");
                for (WorlContactListener worlContactListener : list) {
                    worlContactListener.colisionEntity(entityA, entityB);
                }
            }else if ((int) (catFixB & BIT_PLAYER) == BIT_PLAYER) {
                entityB = (Entity) bodyB.getUserData();
                System.out.println(string + " colisiona con player");
                for (WorlContactListener worlContactListener : list) {
                    worlContactListener.colisionEntity(entityA, entityB);
                }
            }
    }
    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
