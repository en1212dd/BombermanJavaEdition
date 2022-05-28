package com.bormberman.screens;

import com.bormberman.Bomberman;
import com.bormberman.input.GameKeys;
import com.bormberman.input.InputManager;
import com.bormberman.ui.GameOverUi;

public class GameOverScreen extends AbstractScreen<GameOverUi> {

    public GameOverScreen(Bomberman context) {
        super(context);
    }

    @Override
    public void render(float delta) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {
        
    }

    @Override
    protected GameOverUi getScreenUI(Bomberman context) {
        return new GameOverUi(context);
    }
    
}
