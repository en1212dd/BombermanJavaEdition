package com.bormberman.components;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component{
    private float xPosition;
    private float yPosition;
    public float getxPosition() {
        return xPosition;
    }
    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }
    public float getyPosition() {
        return yPosition;
    }
    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }
}
