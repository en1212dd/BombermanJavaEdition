package com.bormberman.screens;

import com.badlogic.gdx.Screen;

public enum ScreenType {
    GAME(GameScreen.class),
    LOADING(LoadingScreen.class),  
    MENU(MenuScreen.class),
    GAME_OVER(GameOverScreen.class);


    private final Class<? extends AbstractScreen<?>> screenClass;

    ScreenType (final Class<? extends AbstractScreen<?>> screenClass){
        this.screenClass = screenClass;
    }
    public Class<? extends Screen> getScreenClass() {
        return screenClass;
    }
}
