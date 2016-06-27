package com.levelgd.onepixelhell;

import android.app.Activity;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Vector;

public class Game extends SurfaceView implements Choreographer.FrameCallback {

    public Activity activity;
    public DisplayMetrics metrics;

    public Choreographer choreographer;

    public Paint defaultPaint;
    public Paint textPaint;
    public Paint textPaintSmall;

    private final SurfaceHolder holder;
    public FPS fps;
    public Bitmap screenBuffer;
    public Canvas screenCanvas;

    public Vector<GameObject> loadedObjects;
    public Vector<GameObject> gameObjects;

    public Game(Activity activity) {
        super(activity);

        this.activity = activity;
        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        choreographer = Choreographer.getInstance();

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp60));
        textPaint.setAntiAlias(true);

        textPaintSmall = new Paint(textPaint);
        textPaintSmall.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp18));

        defaultPaint = new Paint();
        defaultPaint.setAntiAlias(false);
        defaultPaint.setFilterBitmap(false);

        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        holder = getHolder();

        fps = new FPS(activity);

        screenBuffer = Bitmap.createBitmap(metrics,metrics.widthPixels,metrics.heightPixels, Bitmap.Config.ARGB_8888);
        screenCanvas = new Canvas(screenBuffer);

        gameObjects = (new Scene0(this)).getGameObjects();

        setOnTouchListener(new TouchHandler());
    }

    @Override
    public void doFrame(long timeStampNanos) {
        choreographer.postFrameCallback(this);

        fps.update(timeStampNanos);

        update();
        if (fps.skipRender()) return;
        render();
    }

    public void update() {
        for (GameObject g : gameObjects) g.update(fps.deltaTime);

        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i).remove) {
                gameObjects.remove(i);
                i--;
            }
        }

        if(loadedObjects != null){
            gameObjects = new Vector<>(loadedObjects);
            loadedObjects.clear();
            loadedObjects = null;
        }
    }

    public void render() {

        if (!holder.getSurface().isValid()) return;

        Canvas canvas = holder.lockCanvas();

        for (GameObject g : gameObjects) g.render(screenCanvas);

        canvas.drawBitmap(screenBuffer,0,0,defaultPaint);

        holder.unlockCanvasAndPost(canvas);
    }

    public void loadScene(Vector<GameObject> newObjects){
        loadedObjects = newObjects;
    }

    public GameObject findById(int id){
        for(GameObject g : gameObjects){
            if(g.id == id) return g;
        }

        return null;
    }
}