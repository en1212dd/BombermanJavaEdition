package com.bormberman.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class GroundComponent implements Component, Poolable{
    public String name ;
    @Override
    public void reset() {
        name = "GROUND";
    }
    
}
