package com.bormberman.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.bormberman.Bomberman;
import com.bormberman.HudListener;
import com.bormberman.screens.ScreenType;

public class GameOverUi extends Table implements HudListener{
    private final TextButton tittle, scoreTittle,score, name;
    private final TextField field;
    private final MyButton button;
    private final I18NBundle i18nBundle;
    private final String TAG = this.getClass().getSimpleName();
    public GameOverUi(Bomberman context){
        super(context.getSkin());
        i18nBundle = context.getI18nBundle();
        setFillParent(true);
        setDebug(true,true);
        setBackground("background");
        context.getHudManager().addListener(TAG, this);

        tittle = new TextButton(i18nBundle.format("interface.gameOver"),getSkin(),"notBackgroundBig" );
        score = new TextButton("",getSkin(),"notBackgroudNormal");
        name = new TextButton(i18nBundle.format("interface.name"), getSkin(),"notBackgroudNormal" );
        scoreTittle = new TextButton(i18nBundle.format("interface.scoreCM"),getSkin(),"notBackgroudNormal"); 
        field = new TextField("", getSkin(), "normal");
        field.setMaxLength(3);
        field.setAlignment(Align.center);
        button = new MyButton(i18nBundle.format("interface.save"), getSkin(), "normal");
        button.setScreen(context, ScreenType.MENU);

        add(tittle).fill().expand().center().pad(20,20,20,20).colspan(2).row();

        add(scoreTittle).width(250).height(55).center().pad(2,2,2,2);
        add(score).width(250).height(55).center().pad(2,2,2,2).row();

        add(name).width(250).height(55).center().pad(2,2,2,2);
        add(field).width(250).height(55).center().pad(2,2,2,2).row();
        
        add(button).width(550).height(55).center().pad(10,10,10,10).colspan(2).row();

        context.getHudManager().star(TAG);

    }
    @Override
    public void changeData(GameUi hud) {
        score.setText(hud.score.getText().toString());
    }
}
