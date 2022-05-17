package com.bormberman.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.bormberman.ui.AnimationType;

public class AnimationComponent implements Component,Poolable{
    public AnimationType aniType;
    public float aniTime;
    @Override
    public void reset() {
        aniType = null;
        aniTime =0;
    }
    
}
