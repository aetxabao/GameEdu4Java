package com.edu4java.android.killthemall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap bmp;
    private GameLoopThread thread;
    private Sprite sprite;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.stu0);
        sprite = new Sprite(this,bmp);
    }

    public void startGame() {
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
        sprite.onDraw(canvas);
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
