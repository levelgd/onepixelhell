package com.levelgd.onepixelhell;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public class FPS {

    public int fps;
    public boolean newSecond = false;

    public float deltaTime;
    public long deltaTimeL;

    private long mRefreshPeriodNanos;
    private long mPrevTimeNanos;

    private long lastTimeNanos;
    private int frames;

    public FPS(Activity activity){
        mRefreshPeriodNanos = getDisplayRefreshNsec(activity);
    }

    public void update(long timeStampNanos){
        long intervalNanos;
        if (mPrevTimeNanos == 0) {
            intervalNanos = 0;
        } else {
            intervalNanos = timeStampNanos - mPrevTimeNanos;
            if (intervalNanos > 1000000000L)  intervalNanos = 0;
        }
        mPrevTimeNanos = timeStampNanos;
        deltaTimeL = intervalNanos;
        deltaTime = intervalNanos / 1000000000.0f;
        //fps count
        newSecond = false;
        lastTimeNanos += deltaTimeL;
        frames++;
        if(lastTimeNanos >= 1000000000L){
            lastTimeNanos -= 1000000000L;
            fps = frames;
            frames = 0;
            newSecond = true;
        }
    }

    public boolean skipRender(){
        return deltaTimeL > mRefreshPeriodNanos;
    }

    public long getDisplayRefreshNsec(Activity activity) {
        Display display = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        double displayFps = display.getRefreshRate();
        return  Math.round(1000000000L / displayFps);
    }
}