package com.levelgd.onepixelhell;

import java.util.Vector;

public class Scene1 {
    private Vector<GameObject> gameObjects;

    public Scene1(Game game){
        gameObjects = new Vector<>();

        gameObjects.add(new ClearerGO(game));
        gameObjects.add(new StageGO(game));

        PlayerGO playerGO = new PlayerGO(game);

        gameObjects.add(playerGO);
        gameObjects.add(new CameraGO(playerGO,game));
        gameObjects.add(new HelpGO(game));
    }

    public Vector<GameObject> getGameObjects(){
        return gameObjects;
    }
}
