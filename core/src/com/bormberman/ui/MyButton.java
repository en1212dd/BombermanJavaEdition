package com.bormberman.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bormberman.Bomberman;
import com.bormberman.audio.AudioType;
import com.bormberman.screens.ScreenType;

public class MyButton extends TextButton {
    private Bomberman context;
    private ScreenType screen;
    public MyButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(SystemCursor.Hand);
                if (context.getAssetManager().isLoaded(AudioType.SELECT.getFilePath())) {
                    context.getAudioManager().playAudio(AudioType.SELECT);
                }
                clearActions();
                if (getActions().size < 1) {
                    Action action = Actions.forever(Actions.sequence(Actions.alpha(1, 0.5f), Actions.alpha(0, 0.5f)));
                    addAction(action);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
                clearActions();
                if (getActions().size < 1) {
                    Action action = Actions.alpha(1, 1);
                    addAction(action);
                }

            }
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (context.getAssetManager().isLoaded(AudioType.SELECT.getFilePath())) {
                    context.getAudioManager().playAudio(AudioType.SELECT);
                }
                context.setScreen(screen);
            }
        });
    }
    public void setScreen(Bomberman context, ScreenType screen) {
        this.context = context;
        this.screen = screen;
    }
}
