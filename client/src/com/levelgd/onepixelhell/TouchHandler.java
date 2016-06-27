package com.levelgd.onepixelhell;

import android.view.MotionEvent;
import android.view.View;
import com.levelgd.onepixelhell.math.Vector2;

public class TouchHandler implements View.OnTouchListener {

    class Touch{
        public int x;
        public int y;

        public Vector2 position;

        public boolean isTouch;
        public int type;

        public Touch(){
            x = y = 0;
            position = new Vector2();
            isTouch = false;
        }
    }

    public static Touch[] touches;

    TouchHandler(){
        touches = new Touch[10];
        touches[0] = new Touch();
        touches[1] = new Touch();
        touches[2] = new Touch();
        touches[3] = new Touch();
        touches[4] = new Touch();
        touches[5] = new Touch();
        touches[6] = new Touch();
        touches[7] = new Touch();
        touches[8] = new Touch();
        touches[9] = new Touch();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        synchronized (this) {
            int count = event.getPointerCount();

            for(int i = 0; i < count; i++) {
                int pointerId = event.getPointerId(i);

                Touch t = touches[pointerId];

                if (t == null) t = new Touch();

                t.x = (int) event.getX(i);
                t.y = (int) event.getY(i);
                t.position.set(t.x,t.y);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        t.type = 1;
                        t.isTouch = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        t.type = 2;
                        t.isTouch = true;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        t.type = 0;
                        t.isTouch = false;
                        break;
                }
            }
            return true;
        }
    }
}
