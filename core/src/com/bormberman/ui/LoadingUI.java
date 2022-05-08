package com.bormberman.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LoadingUI extends Table {
    private final ProgressBar progressBar;
    private final TextButton txtButton;
    public LoadingUI(Stage stage, Skin skin) {
        super(skin);
        setFillParent(true);

        progressBar =  new ProgressBar(0, 1, 0.01f, false, skin,"default");
        txtButton =  new TextButton("Cargando..", skin,"huge");
        txtButton.getLabel().setWrap(true);

        add(txtButton).expand().fill().bottom().row();
        add(progressBar).expandX().fillX().bottom().padBottom(20);
    }
    
}
