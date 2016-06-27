package com.levelgd.onepixelhell;

import android.graphics.Canvas;
import android.graphics.Color;

public class ClearerGO extends GameObject {
    public ClearerGO(Game game){
        super(0,0,game);
    }

    @Override
    public void render(Canvas canvas) {
        super.render(canvas);

        canvas.drawColor(Color.BLACK);
    }
}
