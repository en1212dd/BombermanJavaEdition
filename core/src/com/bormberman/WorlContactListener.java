package com.bormberman;

import com.badlogic.ashley.core.Entity;

public interface WorlContactListener {
    void colisionEntity( Entity entityA, Entity entityB);
}
