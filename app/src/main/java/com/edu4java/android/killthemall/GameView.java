package com.edu4java.android.killthemall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap bmp;
    private GameLoopThread thread;
    private int x = 0;
    private int xSpeed = 1;
    private int XPEED = 4;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.stu0);
    }

    public void onDraw(Canvas canvas) {
        if (x == getWidth() - bmp.getWidth()) {
            xSpeed = -1 * XPEED;
        }
        if (x == 0) {
            xSpeed = 1 * XPEED;
        }
        x = x + xSpeed;
        Log.d("GameView", "x=" + x);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmp, x , 10, null);

        Paint paint = new Paint();
        paint.setARGB(255, 0, 255, 0);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(Integer.toString(x), getWidth() / 2, getHeight() / 2, paint);
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

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        startGame();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGame();
    }
}