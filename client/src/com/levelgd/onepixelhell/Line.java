package com.levelgd.onepixelhell;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.levelgd.onepixelhell.math.Vector2;

public class Line {

    public boolean del = false;

    private int width;

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Line(int x1, int y1, int x2, int y2, int width){

        this.width = width;

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean draw(Vector2 camera, Canvas canvas, Paint paint){

        int w = canvas.getWidth();
        int h = canvas.getHeight();

        int dx1 = (int)(x1 + camera.x);
        int dy1 = (int)(y1 + camera.y);
        int dx2 = (int)(x2 + camera.x);
        int dy2 = (int)(y2 + camera.y);

        if((dx1 > 0 && dx1 < w && dy1 > 0 && dy1 < h) || (dx2 > 0 && dx2 < w && dy2 > 0 && dy2 < h)){
            paint.setStrokeWidth(width);
            canvas.drawLine(x1 + camera.x, y1 + camera.y, x2 + camera.x, y2 + camera.y, paint);
        }else{
            if(dy2 > h) return false;
        }

        return true;
    }

}
