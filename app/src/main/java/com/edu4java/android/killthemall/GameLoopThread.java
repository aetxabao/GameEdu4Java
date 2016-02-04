package com.edu4java.android.killthemall;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

@SuppressLint("WrongCall")
public class GameLoopThread extends Thread {
    static final long FPS = 30;
    private boolean running = false;
    private GameView canvas;
    private SurfaceHolder surfaceHolder = null;

    public GameLoopThread(GameView view) {
        super();
        this.canvas = view;
        this.surfaceHolder = view.getHolder();
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public void startThread() {
        running = true;
        super.start();
    }

    public void stopThread() {
        running = false;
    }

    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        Canvas c = null;
        while (running) {
            c = null;
            startTime = System.currentTimeMillis();
            try {
                c = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if (c != null) {
                        canvas.onDraw(c);
                    }
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            Log.d("GameLoopThread", "sleepTime=" + sleepTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
            }
        }

        Log.d("GameLoopThread", "running=" + running);
    }
}