package com.edu4java.android.killthemall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoopThread thread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private long lastClick;
    private Bitmap bmpBlood;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        createSprites();
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood);
    }

    private void createSprites() {
        sprites.add(createSprite(R.drawable.stu0));
        sprites.add(createSprite(R.drawable.stu1));
        sprites.add(createSprite(R.drawable.stu2));
        sprites.add(createSprite(R.drawable.stu3));
        sprites.add(createSprite(R.drawable.stu4));
    }

    private Sprite createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bmp);
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
        for (int i=temps.size()-1;i>=0;i--) {
            temps.get(i).onDraw(canvas);
        }
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 500) {
            float x = event.getX();
            float y = event.getY();
            lastClick = System.currentTimeMillis();
            synchronized (getHolder()) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    if (sprite.isCollition(x, y)) {
                        sprites.remove(sprite);
                        temps.add(new TempSprite(temps, this, x, y, bmpBlood));
                        break;
                    }
                }
            }
        }
        return true;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        startGame();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGame();
    }
}
