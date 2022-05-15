package com.bormberman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bormberman.Bomberman;
import com.bormberman.input.GameKeys;
import com.bormberman.input.InputManager;
import com.bormberman.map.Map;
import com.bormberman.map.MapListener;
import com.bormberman.map.MapManager;
import com.bormberman.map.MapType;
import com.bormberman.ui.GameUi;

import static com.bormberman.Bomberman.UNIT_SCALE;

public class GameScreen extends AbstractScreen<GameUi> implements MapListener {

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera orthographicCamera;
    private final MapManager mapManager;

    public GameScreen(final Bomberman contextBomberman) {
        super(contextBomberman);

        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, context.getSpriteBatch());
        this.mapManager = context.getMapManager();
        this.orthographicCamera = context.getOrthographicCamera();

        mapManager.addMapListener(this);
        mapManager.setMap(MapType.MAP_1);
        context.getEscEngine().createPlayer(mapManager.getCurrentMap().parcePlayerStartLayer(), 0.45f, 0.45f);
    };

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply(true);
        if (mapRenderer.getMap() != null) {
            mapRenderer.setView(orthographicCamera);
            mapRenderer.render();

        }
        box2dDebugRenderer.render(world, viewport.getCamera().combined);

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
        mapRenderer.dispose();
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {
    }

    @Override
    public void mapChange(Map map) {
        mapRenderer.setMap(map.getTiledMap());
    }

}
