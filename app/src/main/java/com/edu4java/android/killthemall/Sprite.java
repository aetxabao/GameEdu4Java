package com.edu4java.android.killthemall;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Sprite {
    // direction = 0 up, 1 left, 2 down, 3 right
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIR = {3, 1, 0, 2};
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLS = 3;
    private int MAX_SPEED = 5;
    private int ZOOM = 2;

    private int x = 0;
    private int y = 0;
    private int xSpeed;
    private int ySpeed;
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
        Random rnd = new Random();
        xSpeed = rnd.nextInt(MAX_SPEED*2) - MAX_SPEED;
        ySpeed = rnd.nextInt(MAX_SPEED*2) - MAX_SPEED;
    }

    private void update() {
        if (x > canvas.getWidth() - ZOOM*width - xSpeed || x + xSpeed < 0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y > canvas.getHeight() - ZOOM*height - ySpeed || y + ySpeed < 0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLS;
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x+ZOOM*width, y+ZOOM*height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    // direction = 0 up, 1 left, 2 down, 3 right
    // animation = 3 back, 1 left, 0 front, 2 right
    private int getAnimationRow(){
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int dir = (int)Math.round(dirDouble) % BMP_ROWS;
        return DIR[dir];
    }

    public boolean isCollition(float x2, float y2){
        return x2 > x && x2 < x + ZOOM*width && y2 > y && y2 < y + ZOOM*height;
    }

}
