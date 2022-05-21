package com.bormberman.ui;

public enum AnimationType {
    //PLAYER
BOMBERMAN_UP("animations/animationMap.atlas","bombermanSprite",0.15f,0),
    BOMBERMAN_LEFT("animations/animationMap.atlas","bombermanSprite",0.15f,1),
    BOMBERMAN_RIGHT("animations/animationMap.atlas","bombermanSprite",0.15f,2),
    BOMBERMAN_DOWN("animations/animationMap.atlas","bombermanSprite",0.15f,3),
    BOMBERMAN_PUNCH("animations/animationMap.atlas","bombermanSprite",0.15f,4),
    //PLAYER DIE
    BOMBERMAN_DIE("animations/animationMap.atlas","bombermanSpritesDie",0.15f,0),
    //ENEMY
    ENEMY_UP("animations/animationMap.atlas","firsEnemy",0.15f,1),
    ENEMY_LEFT("animations/animationMap.atlas","firsEnemy",0.15f,2),
    ENEMY_RIGHT("animations/animationMap.atlas","firsEnemy",0.15f,3),
    ENEMY_DOWN("animations/animationMap.atlas","firsEnemy",0.15f,0),
    //ENEMY DIE
    ENEMY_DIE("animations/animationMap.atlas","firsEnemyDie",0.15f,0),
    //BOM
    BOM_IDLE("animations/animationMap.atlas","bomAnimation",0.20f,0),
    BOM_TIMEOUT("animations/animationMap.atlas","bomAnimationTimeOut",0.15f,0),
    //FIRE
    FIRE("animations/animationMap.atlas","fireAnimation",0.15f,0);

    private String atlasPath,atalsKey;
    private float frameTime;
    private int rowIndex;

    AnimationType(String atlasPath, String altlasKey,float frameTime,int rowIndex){
        this.atalsKey = altlasKey;
        this.atlasPath = atlasPath;
        this.frameTime = frameTime;
        this.rowIndex = rowIndex;
    }
    public String getAtalsKey() {
        return atalsKey;
    }
    public String getAtlasPath() {
        return atlasPath;
    }
    public float getFrameTime() {
        return frameTime;
    }
    public int getRowIndex() {
        return rowIndex;
    }
    

}
