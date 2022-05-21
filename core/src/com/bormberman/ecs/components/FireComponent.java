package com.bormberman.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class FireComponent implements Component,Poolable {
    public float liveTime;   
    public float damage;
    @Override
    public void reset() {
        liveTime = 0f;
        damage = 0f;
    }
}
