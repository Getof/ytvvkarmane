package ru.getof.ytvvkarmane.Utils;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RadioPlayer extends Service {

    public MediaPlayer mpl;
    ExecutorService es;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mpl = new MediaPlayer();
        es = Executors.newFixedThreadPool(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String st = intent.getStringExtra("stream");
        MyRun mr = new MyRun(st);
        es.execute(mr);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mpl.isPlaying()) {
            mpl.stop();
        }
        mpl.release();
        stopSelf();
    }

    class MyRun implements Runnable {

        String stm;

        public MyRun(String stm) {
            this.stm = stm;
        }

        @Override
        public void run() {
            mpl.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            try {
                mpl.setDataSource(stm); //http://95.154.254.129:13625
                mpl.setAudioStreamType(AudioManager.STREAM_MUSIC);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            mpl.prepareAsync();
        }
    }
}
