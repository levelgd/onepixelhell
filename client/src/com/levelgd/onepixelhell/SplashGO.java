package com.levelgd.onepixelhell;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class SplashGO extends GameObject {

    private String title = "One Pixel Hell";
    private float secsBeforeHide = 1f;

    private int a = 255;

    private Paint selfPaint;

    public SplashGO(Game game){
        super(0,0,game);

        position.set(
            game.metrics.widthPixels / 2 - game.textPaint.measureText(title) / 2,
            game.metrics.heightPixels / 2 - game.textPaint.getTextSize() / 2
        );

        selfPaint = new Paint(game.textPaint);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if(secsBeforeHide <= 0){
            if(a <= 0){
                remove = true;
                game.loadScene((new Scene1(game)).getGameObjects());
            }else{
                a -= 5;
                if(a < 0) a = 0;
                selfPaint.setAlpha(a);
            }
        }else{
            secsBeforeHide -= dt;
        }
    }

    @Override
    public void render(Canvas canvas) {
        super.render(canvas);

        canvas.drawColor(Color.BLACK);

        canvas.drawText(
                title,
                position.x,
                position.y,
                selfPaint);
    }
}
