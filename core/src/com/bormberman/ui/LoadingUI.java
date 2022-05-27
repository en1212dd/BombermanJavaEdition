package com.bormberman.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.bormberman.Bomberman;

public class LoadingUI extends Table {
    private final ProgressBar progressBar;
    private final TextButton txtButton;
    private final TextButton pressAnyKeyButton;
    private final I18NBundle i18nBundle;
    public LoadingUI(Bomberman context) {
        super(context.getSkin());
        setFillParent(true);
        i18nBundle = context.getI18nBundle();
        progressBar =  new ProgressBar(0, 1, 0.01f, false,getSkin(),"default");
        progressBar.setAnimateDuration(2);
        txtButton =  new TextButton(i18nBundle.format("interface.loading"), getSkin(),"notBackgroundBig");
        txtButton.getLabel().setWrap(true);

        pressAnyKeyButton = new TextButton(i18nBundle.format("interface.pressAnyButton"), getSkin(),"notBackgroudNormal");
        pressAnyKeyButton.getLabel().setWrap(true);
        pressAnyKeyButton.setVisible(false);

        add(pressAnyKeyButton).expand().fill().center().row();
        add(txtButton).expandX().fillX().bottom().row();
        add(progressBar).expandX().fillX().bottom().pad(20,25,20,25);
        //setDebug(true, true);
    }
    public void setProgress( float progress) {
        progressBar.setValue(progress);
        if (progressBar.getVisualPercent()>=1 && !pressAnyKeyButton.isVisible()) {
            pressAnyKeyButton.setVisible(true);
            pressAnyKeyButton.setColor(1,1,1,0);
            pressAnyKeyButton.addAction(Actions.forever(Actions.sequence(Actions.alpha(1,1), Actions.alpha(0,1))));
            txtButton.setVisible(false);
            
        }
    }
}
