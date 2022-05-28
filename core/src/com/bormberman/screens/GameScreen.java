package com.bormberman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.bormberman.Bomberman;
import com.bormberman.audio.AudioType;
import com.bormberman.input.GameKeys;
import com.bormberman.input.InputManager;
import com.bormberman.map.Map;
import com.bormberman.map.MapListener;
import com.bormberman.map.MapManager;
import com.bormberman.map.MapType;
import com.bormberman.ui.GameUi;


public class GameScreen extends AbstractScreen<GameUi> implements MapListener{

    private final MapManager mapManager;
    private  boolean isfirstTime = true;
    public GameScreen(final Bomberman contextBomberman) {
        super(contextBomberman);

        this.mapManager = context.getMapManager();
        mapManager.setMap(MapType.MAP_1);

        context.getHudManager().setHud(screenUI);;
    };


    @Override
    public void render(float delta) {
        if (isfirstTime) {
            audioManager.playAudio(AudioType.START_PLAY);
            Timer.schedule(new Task() {

                @Override
                public void run() {
                    audioManager.playAudio(AudioType.STAGE1);
                    
                }
                
            }, 3f);
            isfirstTime = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            mapManager.setMap(MapType.MAP_1);
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            mapManager.setMap(MapType.MAP_2);
        }
    }

    @Override
    protected GameUi getScreenUI(Bomberman context) {
        return new GameUi(context);
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
    public void mapChange(Map map) {
    }

}
