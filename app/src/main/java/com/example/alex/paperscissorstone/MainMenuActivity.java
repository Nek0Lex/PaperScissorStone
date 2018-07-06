package com.example.alex.paperscissorstone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainMenuActivity extends AppCompatActivity {
    String NOTES = "notes.txt";
    TextView name;
    TextView phone;
    TextView email;
    TextView dob;
    String username,useremail, phoneNumber, Dob;
    SharedPreferences user;
    SQLiteDatabase db;
    MediaPlayer mPlayer = null;
    AudioManager audioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getSharedPreferences("registerPref", 0);
        setContentView(R.layout.activity_main_menu);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phoneNum);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);
        username = getSharedPreferences("registerPref", MODE_PRIVATE).getString("name", "Guest");
        useremail = getSharedPreferences("registerPref", MODE_PRIVATE).getString("email", "");
        phoneNumber = getSharedPreferences("registerPref", MODE_PRIVATE).getString("phone", "");
        Dob = getSharedPreferences("registerPref", MODE_PRIVATE).getString("dob", "");
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mPlayer = new MediaPlayer();
        mPlayer.setLooping(true);
        mPlayer = MediaPlayer.create(this, R.raw.instrumental);
        try {
            name.setText(username);
            email.setText(useremail);
            phone.setText(phoneNumber);
            dob.setText(Dob);
        } catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (email.getText().equals("")){
            email.setBackgroundResource(R.drawable.transborder);
        }
        if (phone.getText().equals("")){
            phone.setBackgroundResource(R.drawable.transborder);
        }
        if (dob.getText().equals("")){
            dob.setBackgroundResource(R.drawable.transborder);
        }

        initDB();
        mPlayer.start();


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (!mPlayer.isPlaying()) {
//            mPlayer.start();
//        } else {
//            mPlayer.stop();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle item selection
        if (item.getItemId() == R.id.setting) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        }
        if (item.getItemId() == R.id.barchart) {
            Intent i = new Intent(this, barChartActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    public void backToRegister(View view) {
        user.edit()
                .putString("name",null)
                .putString("email", null)
                .putString("dob", null)
                .putString("phone", null)
                .apply();
        db = SQLiteDatabase.openDatabase("/data/data/com.example.alex.paperscissorstone/gamelogDB", null,
                SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql;
        sql = "DROP TABLE if exists Gamelog;";
        db.execSQL(sql);
        Intent i =  new Intent(this, MainMenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    public void editUser(View view) {
        Intent i = new Intent (this, EditUserActivity.class);
        startActivity(i);
    }

    public void toGame(View view) {
        Intent i = new Intent (this, GameActivity.class);
        startActivity(i);
    }

    public void jumpToGameLog(View view) {
        Intent i = new Intent (this, GameLogActivity.class);
        startActivity(i);
    }

    public void initDB(){
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.alex.paperscissorstone/gamelogDB", null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);
            String sql;
            sql = "CREATE TABLE IF NOT EXISTS Gamelog (" + "gameNo int PRIMARY KEY, " + "gameDate datetime, "
                    + "opponentName text, " + "opponentAge text," + "userHand int, " + "opponentHand Text, "+ "status Text); ";
            db.execSQL(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
