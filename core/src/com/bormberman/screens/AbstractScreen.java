package com.bormberman.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bormberman.Bomberman;

public abstract class AbstractScreen implements Screen {
    protected final Bomberman context;
    protected final FitViewport viewport;
    protected final World world;
    protected final Box2DDebugRenderer box2dDebugRenderer;
    protected final Stage stage;
    protected final Table screenUI;

    public AbstractScreen(final Bomberman context){
        this.context=context;
        this.viewport = context.getScreenViewport();
        this.world = context.getWorld();
        this.box2dDebugRenderer = context.getBox2dDebugRenderer();
        this.stage = context.getStage();
        screenUI = getScreenUI(context.getSkin());
    }
    protected abstract Table getScreenUI(Skin skin); 
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        this.stage.getViewport().update(width, height,true);
    }
    @Override
    public void show() {
       stage.addActor(screenUI);
    }
    @Override
    public void hide() {
      stage.getRoot().removeActor(screenUI); 
    }
}
