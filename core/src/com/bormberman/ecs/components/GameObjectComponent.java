package com.bormberman.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.bormberman.map.GameObjectType;

public class GameObjectComponent implements Component, Poolable{
    public GameObjectType type;
    public int animationIndex;
    @Override
    public void reset() {
        type = null;
        animationIndex = - 1;
    }
    
}
