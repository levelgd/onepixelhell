package com.levelgd.onepixelhell;

public class CameraGO extends GameObject {

    public GameObject target;

    private int hw;
    private int hh;

    public CameraGO(GameObject target, Game game){
        super(0,0,game);

        this.target = target;

        hw = game.metrics.widthPixels / 2;
        hh = game.metrics.heightPixels / 2;

        id = 10;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        position.x = -target.position.x + hw;
        position.y = -target.position.y + hh;
    }
}
