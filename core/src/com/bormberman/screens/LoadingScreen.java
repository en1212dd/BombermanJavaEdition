package com.bormberman.screens;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bormberman.Bomberman;
import com.bormberman.ui.LoadingUI;

public class LoadingScreen extends AbstractScreen {
    private final AssetManager assetManager;

    public LoadingScreen(Bomberman contextBomberman) {
        super(contextBomberman);
        this.assetManager=context.getAssetManager();
        assetManager.load("map/stage1_1.tmx",TiledMap.class);
    
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        // if(assetManager.update()) {
        //     context.setScreen(ScreenType.GAME);
        // }
    }
    @Override
    protected Table getScreenUI(Skin skin) {
        return new LoadingUI(stage,skin);
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
    
}
