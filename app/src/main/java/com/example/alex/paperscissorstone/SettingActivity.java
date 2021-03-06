package com.example.alex.paperscissorstone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class SettingActivity extends AppCompatActivity {
    private SeekBar sb;
    private TextView tv;
    private final static int MAX_VOLUME = 100;
    MediaPlayer mPlayer = null;
    AudioManager audioManager;
    Switch swcheatMode;
    SharedPreferences user;
    String cheatmode, getCheatmode;
    int Volume = 0;
    int maxVolume,curVolume, orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            orientation = getResources().getConfiguration().orientation; //check orientation
            if (orientation == ORIENTATION_PORTRAIT) {
                setContentView(R.layout.activity_setting);
            } else if (orientation == ORIENTATION_LANDSCAPE){
                setContentView(R.layout.activity_setting_landscape);
            }
            getCheatmode = getSharedPreferences("registerPref", MODE_PRIVATE).getString("cheatmode","");
            swcheatMode = findViewById(R.id.swCheatMode);
            user = getSharedPreferences("registerPref", 0);
            swcheatMode.setChecked(Boolean.valueOf(getCheatmode));
            bindViews();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    private void bindViews() {
        sb = (SeekBar) findViewById(R.id.seekBar);
        tv = (TextView) findViewById(R.id.textView);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText("BGM Vol:"+progress + " / 100 ");
                audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                Volume = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void backtomenu(View view) { //back button
        Intent i = new Intent (this, MainMenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void cheatMode(View view) {  //cheat mode switch
        if (swcheatMode.isChecked()) {
            cheatmode = "true";
        } else {
            cheatmode = "false";
        }
        user.edit()
                .putString("cheatmode", cheatmode).apply();
    }
}
