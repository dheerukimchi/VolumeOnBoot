package com.volumefix;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;

public class VolumeService extends Service {

    private final Handler handler = new Handler();
    private int attempts = 0;
    private static final int TARGET_VOLUME = 20;
    private static final int MAX_ATTEMPTS = 15;
    private static final int INTERVAL_MS = 3000;

    private final Runnable volumeRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
                if (am != null) {
                    am.setStreamVolume(AudioManager.STREAM_MUSIC,  TARGET_VOLUME, 0);
                    am.setStreamVolume(AudioManager.STREAM_RING,   TARGET_VOLUME, 0);
                    am.setStreamVolume(AudioManager.STREAM_SYSTEM, TARGET_VOLUME, 0);
                }
            } catch (Exception ignored) {}
            attempts++;
            if (attempts < MAX_ATTEMPTS) {
                handler.postDelayed(this, INTERVAL_MS);
            } else {
                stopSelf();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        attempts = 0;
        handler.post(volumeRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(volumeRunnable);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
}
