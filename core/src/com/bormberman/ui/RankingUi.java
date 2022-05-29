package com.bormberman.ui;

import java.sql.SQLException;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.bormberman.Bomberman;
import com.bormberman.data.DatabaseManager;
import com.bormberman.screens.ScreenType;

public class RankingUi extends Table{
    private TextButton title;
    private MyButton button;
    private I18NBundle i18nBundle;
    public RankingUi(Bomberman context){
        super(context.getSkin());
        setFillParent(true);
        
        i18nBundle = context.getI18nBundle();
        title = new TextButton("[orangeGame]"+i18nBundle.format("interface.Congrulations"), getSkin(), "notBackgroundBig");
        button = new MyButton("[orangeGame]"+i18nBundle.format("interface.BackMenu"), getSkin(), "normal");
        button.setScreen(context, ScreenType.MENU);

        add(title).colspan(2).center().fill().expand().pad(10,10,10,10).row();
        crearTablas();
        add(button).width(550).height(55).center().pad(10,10,10,10).colspan(2).row();
    }
    private void crearTablas() {
        DatabaseManager databaseManager;
        try {
            databaseManager = new DatabaseManager();
            final Array<String[]> data = databaseManager.allPlayers();
            for (String[] user : data) {
                add(new TextButton("[orangeGame]"+user[0],getSkin(), "notBackgroundBig")).width(250).height(55).center().pad(2,2,2,2);
                add(new TextButton("[orangeGame]"+user[1],getSkin(), "notBackgroundBig")).width(250).height(55).center().pad(2,2,2,2).row();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
