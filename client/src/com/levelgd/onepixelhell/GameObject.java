package com.levelgd.onepixelhell;

import android.graphics.Canvas;
import com.levelgd.onepixelhell.math.Vector2;

public class GameObject implements Renderable, Updateable{

    public int id = 0;
    public Vector2 position;
    public Game game;

    public boolean remove = false;

    public GameObject(float x, float y, Game game){
        this.position = new Vector2(x,y);
        this.game = game;
    }

    @Override
    public void update(float dt){
        if(remove) return;
    }

    @Override
    public void render(Canvas canvas){
        if(remove) return;
    }
}
