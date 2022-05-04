package com.bormberman.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bormberman.Bomberman;

public class LoadingScreen extends AbstractScreen {
    private final AssetManager assetManager;

    public LoadingScreen(Bomberman contextBomberman) {
        super(contextBomberman);
        this.assetManager=context.getAssetManager();
        assetManager.load("map/stage1_1.tmx",TiledMap.class);
    
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,1,0,1);

        if(assetManager.update()) {
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
