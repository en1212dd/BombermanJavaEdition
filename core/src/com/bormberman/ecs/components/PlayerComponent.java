package com.bormberman.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PlayerComponent implements Component,Poolable {
    public int numberOfBooms;
    public int timeToRecharge;
    public Vector2 speed = new Vector2();
    @Override
    public void reset() {
       numberOfBooms = 2;
       speed.set(0, 0);
    }
}
