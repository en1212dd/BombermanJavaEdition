package com.bormberman.ui;


import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.bormberman.Bomberman;
import com.bormberman.screens.ScreenType;


public class MenuUi extends Table{
    private MyButton playGame, options, marks;
    private Image title;
    private I18NBundle i18nBundle;
    public MenuUi(Bomberman context){
        super(context.getSkin());
        i18nBundle = context.getI18nBundle();
        setFillParent(true);
        playGame = new MyButton("[orangeGame]"+i18nBundle.format("interface.playGame"),getSkin(), "default");
        playGame.setScreen(context, ScreenType.LOADING);
        options = new MyButton("[orangeGame]"+i18nBundle.format("interface.options"), getSkin()  , "default");
        options.setScreen(context, ScreenType.LOADING);
        marks = new MyButton("[orangeGame]"+i18nBundle.format("interface.ranking"),  getSkin() , "default");
        marks.setScreen(context, ScreenType.LOADING);
        title = new Image( getSkin() ,"tittle");
        setBackground("background");
        add(title).expand().fill().center().pad(20,20,20,20).row();
        add(playGame).width(250).height(55).center().pad(10,10,10,10).row();
        add(options).width(350).height(55).center().pad(10,10,10,10).row();
        add(marks).width(450).height(55).center().pad(10,10,10,10).row();
        context.getStage().addActor(this);
        //Gdx.input.setInputProcessor(context.getStage());
    }
}
