package com.levelgd.onepixelhell;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

public class GameoverGO extends GameObject {

    private long newscore = 0;
    private long oldscore = 0;

    private boolean hiding = false;
    private int a = 255;

    private Paint selfPaint;
    private Paint scoresPaint;
    private TextPaint scoresTextPaint;
    private StaticLayout staticLayout;

    private RequestTask requestTask;
    private SharedPreferences preferences;

    public String id = "none";
    public String scores = "loading scores";

    public GameoverGO(Game game, long newscore){
        super(0,0,game);

        selfPaint = new Paint(game.textPaint);
        selfPaint.setTextSize(game.activity.getResources().getDimensionPixelSize(R.dimen.sp36));

        scoresPaint = new Paint(game.textPaintSmall);
        scoresPaint.setColor(Color.parseColor("#444444"));

        scoresTextPaint = new TextPaint(scoresPaint);
        staticLayout = new StaticLayout(scores, scoresTextPaint, game.metrics.widthPixels, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        this.newscore = newscore;

        preferences = PreferenceManager.getDefaultSharedPreferences(game.activity);
        oldscore = preferences.getLong("score",0);

        if(oldscore < newscore) {
            preferences.edit().putLong("score",newscore).apply();
            oldscore = newscore;
        }

        id = preferences.getString("id","none");
        requestTask = new RequestTask();

        requestTask.execute("http://levelgd.ru/onepixelhell/score.php?id="+id+"&score="+oldscore+"&name="+ getDeviceName().replaceAll(" ","_"));
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
                game.loadScene((new Scene1(game)).getGameObjects());
            }else{
                a -= 10;
                if(a < 0) a = 0;
                selfPaint.setAlpha(a);
                scoresTextPaint.setAlpha(a);
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        super.render(canvas);

        String line1 = "your score: " + newscore;
        String line2 = "";

        if(oldscore <= newscore){
            line2 = "NEW RECORD!";
        }else{
            line2 = "best: " + oldscore;
        }

        //canvas.drawText(scores, 0, scoresPaint.getTextSize(), scoresPaint);
        staticLayout.draw(canvas);

        canvas.drawText(
                line1,
                game.metrics.widthPixels / 2 - selfPaint.measureText(line1) /2,
                game.metrics.heightPixels / 2 - selfPaint.getTextSize(),
                selfPaint);

        canvas.drawText(
                line2,
                game.metrics.widthPixels / 2 - selfPaint.measureText(line2) /2,
                game.metrics.heightPixels / 2 + selfPaint.getTextSize(),
                selfPaint);

    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    class RequestTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            try {
                HttpRequest request =  HttpRequest.get(urls[0]);
                String body = "";
                if (request.ok()) {
                    body = request.body();
                }else{
                    body = "error loading scores";
                }
                return body;
            } catch (HttpRequest.HttpRequestException exception) {
                Log.e("HttpRequestException",exception.getMessage());
                return "request exception";
            }
        }

        protected void onPostExecute(String body) {
            if(body.length() == 8){
                id = body;
                preferences.edit().putString("id",id).apply();
                scores = "registration complete";
            }else{
                scores = body.replaceAll("_"," ");
            }

            staticLayout = new StaticLayout(scores, scoresTextPaint, game.metrics.widthPixels, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
    }

}
