package com.edu4java.android.killthemall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;

    public GameView(Context context) {
          super(context);
          setWillNotDraw(false);
          holder = getHolder();
          holder.addCallback(new SurfaceHolder.Callback() {

                 @Override
                 public void surfaceDestroyed(SurfaceHolder holder) {
                 }

                 @Override
                 public void surfaceCreated(SurfaceHolder holder) {
                        Canvas c = holder.lockCanvas(null);
                        draw(c);
                        holder.unlockCanvasAndPost(c);
                 }

                 @Override
                 public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
                 }
          });
          bmp = BitmapFactory.decodeResource(getResources(), R.drawable.stu0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
          canvas.drawColor(Color.BLACK);
          canvas.drawBitmap(bmp, 10, 10, null);
    }
}
