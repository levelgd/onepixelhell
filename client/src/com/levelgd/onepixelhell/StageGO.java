package com.levelgd.onepixelhell;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.levelgd.onepixelhell.math.Vector2;

import java.util.Random;
import java.util.Vector;

public class StageGO extends GameObject {

    private Vector2 camera;

    private Vector<Line> lines;

    private Paint selfPaint;

    private Random random;

    private int my = 0;
    private int mx = 0;
    private boolean up = true;

    private int lineW = 15;
    private int nextW = 5;
    private int countW = nextW;

    public StageGO(Game game){
        super(0,0,game);

        selfPaint = new Paint(game.defaultPaint);
        selfPaint.setColor(Color.parseColor("#444444"));
        selfPaint.setStrokeCap(Paint.Cap.SQUARE);

        lines = new Vector<>();

        random = new Random(System.currentTimeMillis());

        int i = 50;
        while (i-- > 0){
            addLine(lineW, nextW * 8, nextW * 8);
        }
    }

    private void addLine(int width, int vert, int hor){
        if(up){
            up = false;

            int nmy = my + random.nextInt(vert) + vert;
            lines.add(new Line(mx, -my, mx, -nmy, width));
            my = nmy;

        }else{
            up = true;

            int nmx = (random.nextBoolean()) ? mx + random.nextInt(hor) + hor : mx - random.nextInt(hor) - hor;
            lines.add(new Line(mx, -my, nmx, -my, width));
            mx = nmx;

        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if(camera == null) camera = game.findById(10).position;
    }

    @Override
    public void render(Canvas canvas) {
        super.render(canvas);

        if(camera == null) return;

        for(Line l : lines){
            if(!l.draw(camera, canvas, selfPaint)){
                l.del = true;
            }
        }

        for(int i = 0; i < lines.size(); i++){
            if(lines.get(i).del){
                lines.remove(i);
                i--;

                countW--;
                if(countW <= 0){
                    if(lineW > 1) lineW--;

                    countW = nextW;
                }

                addLine(lineW, nextW * 8, nextW * 8);
            }
        }
    }

}
