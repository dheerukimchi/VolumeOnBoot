package com.volumefix;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStatus = findViewById(R.id.tv_status);
        Button btnApply = findViewById(R.id.btn_apply);
        updateStatus();
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
                if (am != null) {
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
                    am.setStreamVolume(AudioManager.STREAM_RING, 20, 0);
                    am.setStreamVolume(AudioManager.STREAM_SYSTEM, 20, 0);
                }
                updateStatus();
            }
        });
    }

    private void updateStatus() {
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (am == null) return;
        int cur = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        tvStatus.setText("Current Volume: " + cur + " / " + max
                + "\n\nBoot Receiver: ACTIVE"
                + "\nWill set volume to 20 on every boot.");
    }
}
