package com.bormberman.screens;

import com.bormberman.Bomberman;
import com.bormberman.ui.MenuUi;

public class MenuScreen extends AbstractScreen<MenuUi> {
    public MenuScreen(Bomberman context) {
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
    protected MenuUi getScreenUI(Bomberman context) {
        return new MenuUi(context);
    }
    
}
