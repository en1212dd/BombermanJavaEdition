package com.bormberman.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.bormberman.Bomberman;


public class MenuUi extends Table{
    private MyButton playGame, options, marks;
    private Image title;
    private I18NBundle i18nBundle;
    public MenuUi(Bomberman context){
        super(context.getSkin());
        i18nBundle = context.getI18nBundle();
        setFillParent(true);
        playGame = new MyButton("[orangeGame]"+i18nBundle.format("interface.playGame"),getSkin(), "big");
        options = new MyButton(i18nBundle.format("interface.options"), getSkin()  , "big");
        marks = new MyButton(i18nBundle.format("interface.ranking"),  getSkin() , "big");
        title = new Image( getSkin() ,"tittle");
        setBackground("background");
        add(title).expand().fill().center().pad(20,20,20,20).row();
        add(playGame).expand().fill().center().row();
        add(options).expand().fill().center().row();
        add(marks).expand().fill().center().row();
        setDebug(true, true );
        context.getStage().addActor(this);
        Gdx.input.setInputProcessor(context.getStage());
    }
}
