package com.bormberman.input;

public interface InputListener {
    void keyPressed(InputManager manager, GameKeys key);
    void keyUp(InputManager manager, GameKeys key);
}
