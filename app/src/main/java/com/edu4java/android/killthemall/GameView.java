package com.edu4java.android.killthemall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoopThread thread;
    private List<Sprite> sprites = new ArrayList<Sprite>();

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    private void createSprites(){
        sprites.add(createSprite(R.drawable.stu0));
        sprites.add(createSprite(R.drawable.stu1));
        sprites.add(createSprite(R.drawable.stu2));
        sprites.add(createSprite(R.drawable.stu3));
        sprites.add(createSprite(R.drawable.stu4));
    }

    private Sprite createSprite(int resource){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this,bmp);
    }

    public void startGame() {
        if (sprites.size() == 0){
            createSprites();
        }
        if (thread == null) {
            thread = new GameLoopThread(this);
            thread.startThread();
        }
    }

    public void stopGame() {
        if (thread != null) {
            thread.stopThread();
            boolean retry = true;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
            thread = null;
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for(Sprite sprite : sprites){
            sprite.onDraw(canvas);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        startGame();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGame();
    }
}
