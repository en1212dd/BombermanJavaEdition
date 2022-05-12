package com.bormberman.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class InputManager implements InputProcessor {
    private GameKeys[] keyMapping;
    private boolean[]  keystate;
    private Array<InputListener> listeners;
    public InputManager() {
        this.keyMapping = new GameKeys[256];
        for (GameKeys gameKey : GameKeys.values()) {
            for (int code : gameKey.keyCodeL) {
                keyMapping[code] = gameKey;
            }
        }
        keystate = new boolean[GameKeys.values().length];
        listeners = new Array<>();
    }
    public void addInputListener(InputListener listener) {
        listeners.add(listener);
    }
    public void remuveInputListener(InputListener listener) {
        listeners.removeValue(listener,true);
    }

    @Override
    public boolean keyDown(int keycode) {
        GameKeys gameKey = keyMapping[keycode];
        if (gameKey == null) {
            return false;
        }

        notifyKeyDown(gameKey);
        return false;
    }

    public void notifyKeyDown(GameKeys gameKey) {
        keystate[gameKey.ordinal()] = true;
        for (InputListener listener : listeners) {
            listener.keyPressed(this, gameKey);
        }
        
    }
    @Override
    public boolean keyUp(int keycode) {
        GameKeys gameKey = keyMapping[keycode];
        if (gameKey == null) {
            return false;
        }

        notifyKeyUp(gameKey);
        return false;
    }
    public void notifyKeyUp(GameKeys gameKey) {
        keystate[gameKey.ordinal()] = true;
        for (InputListener listener : listeners) {
            listener.keyUp(this, gameKey);
        }
        
    }
    public boolean isKeyPress(GameKeys gameKey) {
        return keystate[gameKey.ordinal()];
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    
}
