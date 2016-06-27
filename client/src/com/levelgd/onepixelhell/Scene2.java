package com.levelgd.onepixelhell;

import java.util.Vector;

public class Scene2 {
    private Vector<GameObject> gameObjects;

    public Scene2(Game game, long points) {
        gameObjects = new Vector<>();

        gameObjects.add(new ClearerGO(game));
        gameObjects.add(new GameoverGO(game,points));
    }

    public Vector<GameObject> getGameObjects(){
        return gameObjects;
    }
}
