package com.edu4java.android.killthemall;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    GameView canvas;

    MediaPlayer mp;
    boolean isMusicLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        canvas = new GameView(this);
        setContentView(canvas);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mp = MediaPlayer.create(this, R.raw.game);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setLooping(true);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                isMusicLoaded = true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        canvas.startGame();
        if (isMusicLoaded){
            if (!mp.isPlaying()) {
                mp.start();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        canvas.stopGame();
        if (mp.isPlaying()) {
            mp.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
            mp = null;
        }
    }

}