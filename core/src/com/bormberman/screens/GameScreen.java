package com.bormberman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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
        mapManager.addMapListener(this);
        mapManager.setMap(MapType.MAP_1);

        context.getEscEngine().createPlayer(mapManager.getCurrentMap().parcePlayerStartLayer(), 0.47f, 0.47f);
        createEnemys();
    };

    private void createEnemys() {
        Array<Vector2> positions = mapManager.getCurrentMap().parceEnemysPositions();
        for (int i = 0; i < positions.size; i++) {
            context.getEscEngine().createEnemy(positions.get(i), 0.47f, 0.47f, 2, new Vector2(3,3));
        }
    }

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
