package com.bormberman.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BomComponent implements Component, Poolable{
    public float timeOfExplote;
    @Override
    public void reset() {
        timeOfExplote = 5;
    }
    
}
