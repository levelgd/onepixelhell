package com.levelgd.onepixelhell;

import android.graphics.Canvas;
import android.graphics.Paint;

public class HelpGO extends GameObject {

    private boolean hiding = false;
    private int a = 255;

    private int measure;
    private int size;

    private Paint selfPaint;

    public HelpGO(Game game){
        super(0,0,game);

        selfPaint = new Paint(game.textPaint);

        measure = (int)selfPaint.measureText("↑");
        size = (int)selfPaint.getTextSize();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if(TouchHandler.touches[0].isTouch){
            hiding = true;
        }

        if(hiding){
            if(a <= 0){
                remove = true;
            }else{
                a -= 5;
                if(a < 0) a = 0;
                selfPaint.setAlpha(a);
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        super.render(canvas);

        int hwp = game.metrics.widthPixels/2;
        int hhp = game.metrics.heightPixels/2;

        canvas.drawText("↑", hwp - measure/2,size*2, selfPaint);
        canvas.drawText("↓", hwp - measure/2, game.metrics.heightPixels - size*2, selfPaint);
        canvas.drawText("←", 0, hhp + size/2, selfPaint);
        canvas.drawText("→", game.metrics.widthPixels - measure * 2, hhp + size/2, selfPaint);
    }
}
