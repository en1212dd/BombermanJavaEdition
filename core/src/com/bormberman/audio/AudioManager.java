package com.bormberman.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.bormberman.Bomberman;

public class AudioManager {
    private  AudioType currentMusicType;   
    private  Music currentMusic;
    private  AssetManager assetManager;

    public AudioManager(Bomberman context){
        this.assetManager = context.getAssetManager();
        currentMusic = null;
        currentMusicType = null;
    }
    public void playAudio(AudioType type) {
        if (type.isMusic()) {
            if (currentMusicType == type) {
                return;
            }else if (currentMusic!= null) {
                currentMusic.stop();
            }
            currentMusicType = type;
            currentMusic = assetManager.get(type.getFilePath(),Music.class);
            currentMusic.setLooping(true);
            currentMusic.setVolume(type.getVolume());
            currentMusic.play();
        }else{
            assetManager.get(type.getFilePath(),Sound.class).play(type.getVolume());
        }
    }
    public void stopAudio() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
            currentMusicType = null;
        }
    }
}
