package com.bormberman.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    private boolean hasBooms;

    public boolean isHasBooms() {
        return hasBooms;
    }

    public void setHasBooms(boolean hasBooms) {
        this.hasBooms = hasBooms;
    }
}
