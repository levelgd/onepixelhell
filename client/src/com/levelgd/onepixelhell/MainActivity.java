package com.levelgd.onepixelhell;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Choreographer;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        game = new Game(this);
        setContentView(game);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Choreographer.getInstance().postFrameCallback(game);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Choreographer.getInstance().removeFrameCallback(game);
    }
}
