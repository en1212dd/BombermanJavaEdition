package com.bormberman.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LoadingUI extends Table {
    private final ProgressBar progressBar;
    private final TextButton txtButton;
    private final TextButton pressAnyKeyButton;
    public LoadingUI( Skin skin) {
        super(skin);
        setFillParent(true);

        progressBar =  new ProgressBar(0, 1, 0.01f, false, skin,"default");
        progressBar.setAnimateDuration(1);
        txtButton =  new TextButton("Cargando..", skin,"big");
        txtButton.getLabel().setWrap(true);

        pressAnyKeyButton = new TextButton("Presiona cualquier bot\u00f3n", skin,"normal");
        pressAnyKeyButton.getLabel().setWrap(true);
        pressAnyKeyButton.setVisible(false);

        add(pressAnyKeyButton).expand().fill().center().row();
        add(txtButton).expandX().fillX().bottom().row();
        add(progressBar).expandX().fillX().bottom().pad(20,25,20,25);
        //setDebug(true, true);
    }
    public void setProgress( float progress) {
        progressBar.setValue(progress);
        if (progress>=1 && !pressAnyKeyButton.isVisible()) {
            pressAnyKeyButton.setVisible(true);
            pressAnyKeyButton.setColor(1,1,1,0);
            pressAnyKeyButton.addAction(Actions.forever(Actions.sequence(Actions.alpha(1,1), Actions.alpha(0,1))));
            txtButton.setVisible(false);
            
        }
    }
}
