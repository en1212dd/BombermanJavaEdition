package com.bormberman.map;

public enum MapType {
    MAP_1("map/stage1_1.tmx"),
    MAP_2("map/stage1_2.tmx");
    
    private final String filePath;

    MapType(final String filePath){
        this.filePath = filePath;
    }
    public String getFilePath() {
        return filePath;
    }
}
