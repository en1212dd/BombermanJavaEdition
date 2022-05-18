package com.bormberman.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class EnemyComponent implements Component, Poolable{
    public int lives;
    public Vector2 velocity = new Vector2();

    @Override
    public void reset() {
        lives = 0;
        velocity.set(0, 0);
    }
    
}
