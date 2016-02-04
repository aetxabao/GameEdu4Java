package com.edu4java.android.killthemall;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLS = 3; 
    private int XSPEED = 5;

    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private Bitmap bmp;
    private GameView canvas;

    private int currentFrame = 0;
    private int width = 0;
    private int height = 0;

    public Sprite(GameView canvas, Bitmap bmp) {
        this.canvas = canvas;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLS;
        this.height = bmp.getHeight() / BMP_ROWS;
    }

    private void update() {
        if (x > canvas.getWidth() - width - xSpeed) {
            xSpeed = -1 * XSPEED;
        }
        if (x + xSpeed < 0) {
            xSpeed = 1 * XSPEED;
        }
        x = x + xSpeed;
        currentFrame = ++currentFrame % BMP_COLS;
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = 1 * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x+width, y+height);
        canvas.drawBitmap(bmp, src, dst, null);

        Paint paint = new Paint();
        paint.setARGB(255, 0, 255, 0);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Integer.toString(x), canvas.getWidth() / 2,
                canvas.getHeight() / 2, paint);
    }

}