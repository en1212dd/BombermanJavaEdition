package com.bormberman.screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.bormberman.Bomberman;
import com.bormberman.audio.AudioType;
import com.bormberman.input.GameKeys;
import com.bormberman.input.InputManager;
import com.bormberman.ui.MenuUi;

public class MenuScreen extends AbstractScreen<MenuUi> {
    private boolean isMusicLoaded;
    public MenuScreen(Bomberman context) {
        super(context);
        //load Audio
		isMusicLoaded = false;
        for (AudioType audioType : AudioType.values()) {
            if (audioType.isMusic()) {
                assetManager.load(audioType.getFilePath(), Music.class);
            }else{
                assetManager.load(audioType.getFilePath(), Sound.class);
            }
        }
    }

    @Override
    public void render(float delta) {
		assetManager.update();
		if (!isMusicLoaded && assetManager.isLoaded(AudioType.INTRO.getFilePath())) {
			isMusicLoaded = true;
			audioManager.playAudio(AudioType.INTRO);	
		}
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
        
    }

    @Override
    protected MenuUi getScreenUI(Bomberman context) {
        return new MenuUi(context);
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {
        
    }
    
}
