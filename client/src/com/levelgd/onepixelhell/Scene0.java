package com.levelgd.onepixelhell;

import java.util.Vector;

public class Scene0 {

    private Vector<GameObject> gameObjects;

    public Scene0(Game game){
        gameObjects = new Vector<>();

        gameObjects.add(new SplashGO(game));
    }

    public Vector<GameObject> getGameObjects(){
        return gameObjects;
    }
}
