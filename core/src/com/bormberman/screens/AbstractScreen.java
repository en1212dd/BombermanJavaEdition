package com.bormberman.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bormberman.Bomberman;
import com.bormberman.input.InputListener;
import com.bormberman.input.InputManager;

public abstract class AbstractScreen<T extends Table> implements Screen , InputListener{
    protected final Bomberman context;
    protected final FitViewport viewport;
    protected final World world;
    protected final Box2DDebugRenderer box2dDebugRenderer;
    protected final Stage stage;
    protected final T screenUI;
    protected final InputManager inputManager;

    public AbstractScreen(final Bomberman context){
        this.context=context;
        this.viewport = context.getScreenViewport();
        this.world = context.getWorld();
        this.box2dDebugRenderer = context.getBox2dDebugRenderer();
        this.stage = context.getStage();
        this.screenUI = getScreenUI(context);
        this.inputManager = context.getInputManager();

    }
    protected abstract T getScreenUI(Bomberman context); 
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        this.stage.getViewport().update(width, height,true);
    }
    @Override
    public void show() {
        inputManager.addInputListener(this);
       stage.addActor(screenUI);
    }
    @Override
    public void hide() {
        inputManager.removeInputListener(this);
      stage.getRoot().removeActor(screenUI); 
    }
}
