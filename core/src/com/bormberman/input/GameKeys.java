package com.bormberman.input;

import com.badlogic.gdx.Input.Keys;

public enum GameKeys {
    UP(Keys.W,Keys.UP),
    DOWN(Keys.S,Keys.DOWN),
    LEFT(Keys.A,Keys.LEFT),
    RIGTH(Keys.D,Keys.RIGHT),
    SELECT(Keys.ENTER,Keys.SPACE),
    BACK(Keys.ESCAPE,Keys.BACKSPACE);

    final int[] keyCodeL;
    private GameKeys(int... keyCodeL) {
        this.keyCodeL = keyCodeL;
    }
    public int[] getKeyCodeL() {
        return keyCodeL;
    }


}
