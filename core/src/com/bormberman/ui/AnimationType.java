package com.bormberman.ui;

public enum AnimationType {
    BOMBERMAN_UP("animations/animationMap.atlas","bombermanSprite",0.15f,0),
    BOMBERMAN_LEFT("animations/animationMap.atlas","bombermanSprite",0.15f,1),
    BOMBERMAN_RIGHT("animations/animationMap.atlas","bombermanSprite",0.15f,2),
    BOMBERMAN_DOWN("animations/animationMap.atlas","bombermanSprite",0.15f,3),
    BOMBERMAN_PUNCH("animations/animationMap.atlas","bombermanSprite",0.15f,4),
    BOMBERMAN_DIE("animations/animationMap.atlas","bombermanSprite",0.15f,5);

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
