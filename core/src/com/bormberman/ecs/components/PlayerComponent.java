package com.bormberman.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PlayerComponent implements Component,Poolable {
    public boolean hasBooms;
    public int numberOfBooms;
    public Vector2 speed = new Vector2();
    @Override
    public void reset() {
       hasBooms = false;
       numberOfBooms = 2;
       speed.set(0, 0);
    }
}
