package com.bormberman.screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.bormberman.Bomberman;
import com.bormberman.audio.AudioType;
import com.bormberman.input.GameKeys;
import com.bormberman.input.InputManager;
import com.bormberman.map.MapType;
import com.bormberman.ui.LoadingUI;

public class LoadingScreen extends AbstractScreen<LoadingUI> {

    public LoadingScreen(Bomberman contextBomberman) {
        super(contextBomberman);
        assetManager.load(MapType.MAP_1.getFilePath(), TiledMap.class);
        assetManager.load(MapType.MAP_2.getFilePath(), TiledMap.class);
        assetManager.load("animations/animationMap.atlas",TextureAtlas.class);
    }

    @Override
    public void render(float delta) {
        assetManager.update();
        screenUI.setProgress(assetManager.getProgress());
    }

    @Override
    protected LoadingUI getScreenUI(Bomberman context) {
        return new LoadingUI(context);
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
        audioManager.playAudio(AudioType.SELECT);
        if (assetManager.getProgress() >=1) {
            context.setScreen(ScreenType.GAME);
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {
        
    }

}
