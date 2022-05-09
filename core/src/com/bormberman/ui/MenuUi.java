package com.bormberman.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class MenuUi extends Table{
    private TextButton playGame, options, marks;
    private Image title;
    public MenuUi(Skin skin){
        super(skin);
        setFillParent(true);
        playGame = new TextButton("Jugar", skin, "normal");
        options = new TextButton("Opciones", skin, "normal");
        marks = new TextButton("Marcadores", skin, "normal");
        title = new Image(skin,"bomberman_nombre");
        
        add(title).expand().fill().center().row();
        add(playGame).expand().fill().center().row();
        add(options).expand().fill().center().row();
        add(marks).expand().fill().center().row();

    }
}
