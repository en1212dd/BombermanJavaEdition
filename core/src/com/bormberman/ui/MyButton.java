package com.bormberman.ui;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyButton extends TextButton {
    public MyButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                clearActions();
                if (getActions().size < 1) {
                    Action action = Actions.forever(Actions.sequence(Actions.alpha(1, 0.5f), Actions.alpha(0, 0.5f)));
                    addAction(action);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                clearActions();
                if (getActions().size < 1) {
                    Action action = Actions.alpha(1, 1);
                    addAction(action);
                }

            }
        });
    }
}
