package com.bormberman.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bormberman.Bomberman;

public class LoadingScreen extends AbstractScreen {

    public LoadingScreen(Bomberman contextBomberman) {
        super(contextBomberman);
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,1,0,1);

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            context.setScreen(ScreenType.GAME);
        }
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }
    
}
