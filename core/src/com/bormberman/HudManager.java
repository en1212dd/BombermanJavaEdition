package com.bormberman;

import java.util.HashMap;
import java.util.Map;

import com.bormberman.ui.GameUi;

public class HudManager {
    public final Map<String,HudListener> listeners;
    private GameUi hud;
   public HudManager(){
        listeners = new HashMap<>();
    }
    public void addListener( String name, HudListener tabla) {
        listeners.put(name,tabla);
    }
    public void star( String name){
        listeners.get(name).changeData(hud);
    }
    public GameUi getHud() {
        return hud;
    }
    public void setHud(GameUi hud) {
        this.hud = hud;
    }
}
