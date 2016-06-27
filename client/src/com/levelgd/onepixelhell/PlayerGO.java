package com.levelgd.onepixelhell;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.levelgd.onepixelhell.math.Vector2;

public class PlayerGO extends GameObject {

    public long points = 0;

    public int speedx = 0;
    public int speedy = 0;

    private long maxy = 0;

    private long pixels = 0;
    private int dpi = 0;

    private Paint selfPaint;

    private Vector2 camera;

    private int color = Color.WHITE;

    public PlayerGO(Game game){
        super(0,0,game);

        selfPaint = new Paint(game.defaultPaint);

        id = 777;

        dpi = game.metrics.densityDpi;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if(camera == null) {
            camera = game.findById(10).position;
        }else{

            if(TouchHandler.touches[0].isTouch){
                int hw = game.metrics.widthPixels / 2;
                int hh = game.metrics.heightPixels / 2;

                Vector2 t = TouchHandler.touches[0].position;

                int diffx = Math.abs((int)t.x - hw);
                int diffy = Math.abs((int)t.y - hh);

                if(diffx < diffy){
                    speedy = (t.y - hh > 0) ? 1:-1;
                    speedx = 0;
                }else{
                    speedx = (t.x - hw > 0) ? 1:-1;
                    speedy = 0;
                }
            }

            position.add(speedx, speedy);
            if(speedy < 0 && maxy > position.y) {
                pixels++;
                maxy = (long) position.y;
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        super.render(canvas);

        if(camera == null) return;

        if((color -= 5) <= Color.BLACK) color = Color.WHITE;

        selfPaint.setColor(color);

        if(game.screenBuffer.getPixel((int)(position.x + camera.x),(int)(position.y + camera.y)) == Color.BLACK){
            game.loadScene((new Scene2(game,points)).getGameObjects());
        }

        canvas.drawPoint(position.x + camera.x, position.y + camera.y, selfPaint);

        points = pixels * dpi;

        String text = pixels + "px * " + dpi + "dpi = " + points + " points";

        canvas.drawText(
                text,
                game.metrics.widthPixels/2 - game.textPaintSmall.measureText(text)/2,
                game.metrics.heightPixels - game.textPaintSmall.getTextSize()*2,
                game.textPaintSmall);
    }
}
