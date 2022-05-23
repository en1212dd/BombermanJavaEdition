package com.bormberman.audio;

public enum AudioType {
    INTRO("sounds/Tittle.mp3",true,0.3f),
    SELECT("sounds/choseOption.mp3",false,0.3f),
    STAGE1("sounds/Level_1.mp3",true,0.3f),
    START_PLAY("sounds/Start.mp3",false,0.3f),
    GAME_OVER("sounds/Game_Over.mp3",true,0.3f),
    DIE_PLAYER("sounds/DiePlayer.ogg",false,0.3f),
    DIE_ENEMY("sounds/EnemyDie.ogg",false,0.3f),
    EXPlOTION("sounds/Explosion.ogg",false,0.3f),
    PLACEBOM("sounds/PlaceBomb.ogg",false,0.3f),
    TELEPORT("sounds/Teleport.ogg",false,0.3f);

    private final String filePath;
    private final boolean isMusic;
    private final float volume;
    private AudioType(String filePath, boolean isMusic, float volume) {
        this.filePath = filePath;
        this.isMusic = isMusic;
        this.volume = volume;
    }
    public String getFilePath() {
        return filePath;
    }
    public boolean isMusic() {
        return isMusic;
    }
    public float getVolume() {
        return volume;
    }
}
