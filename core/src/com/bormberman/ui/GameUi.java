package com.bormberman.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.bormberman.Bomberman;

public class GameUi extends Table {
    private float heightOftheHud = 90;
    private float widthOfTheHud = 512;

    private float widthOfCells = 83.3f;
    private float heightOfCells = 23.3f;
    private float ajustOfImages = 50f;
    private float padding = 10f;

    private Image playerIcon,timeIcon;
    private TextButton lives,scoreittle,score,time;

    private I18NBundle i18nBundle;
    public GameUi(Bomberman context) {
        super(context.getSkin());

        i18nBundle = context.getI18nBundle();
        setDebug(true,true);
        setPosition(64, Gdx.graphics.getHeight()-heightOftheHud);
        background("backgroundHud");
        setWidth(widthOfTheHud);
        setHeight(heightOftheHud);

        playerIcon = new Image(context.getSkin(),"playerIcon");
        lives = new TextButton("5",context.getSkin(),"hudBackground");
        scoreittle = new TextButton(i18nBundle.format("interface.score"), context.getSkin(),"notBackgroundBig");
        score = new TextButton("500",context.getSkin(),"hudBackground");
        timeIcon = new Image(context.getSkin(),"timerIcon");
        time = new TextButton("2:00", context.getSkin(),"hudBackground");

        add(playerIcon).center().width(widthOfCells-ajustOfImages).height(heightOfCells).padRight(padding);
        add(lives).center().width(widthOfCells).height(heightOfCells);
        add(scoreittle).padLeft(padding);
        add(score).center().width(widthOfCells).height(heightOfCells);
        add(timeIcon).center().width(widthOfCells-ajustOfImages).height(heightOfCells).padLeft(padding);
        add(time).center().width(widthOfCells).height(heightOfCells);
    }
    
}
