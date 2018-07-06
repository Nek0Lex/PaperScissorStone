package com.example.alex.paperscissorstone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainMenuActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    String NOTES = "notes.txt";
    TextView name,phone, email, dob;
    String username,useremail, phoneNumber, Dob;
    int orientation;
    SharedPreferences user;
    SQLiteDatabase db;
    MediaPlayer mPlayer = null;
    AudioManager audioManager;
    CircleImageView profile_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orientation = getResources().getConfiguration().orientation;
        if (orientation == ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_menu);
        } else if (orientation == ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_main_menu_landscape);
        }
        user = getSharedPreferences("registerPref", 0);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phoneNum);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);
        profile_image = findViewById(R.id.profile_image);
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
        return super.onOptionsItemSelected(item);
    }


    public void backToRegister(View view) {
        SharedPreferences.Editor editor = user.edit();
        editor.clear();
        editor.apply();

        db = SQLiteDatabase.openDatabase("/data/data/com.example.alex.paperscissorstone/gamelogDB", null,
                SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql;
        sql = "DROP TABLE if exists Gamelog;";
        db.execSQL(sql);
        finish();
        System.exit(0);
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
            sql = "CREATE TABLE IF NOT EXISTS Gamelog (" + "gameNo int PRIMARY KEY, " + "gameDate datetime, "+ "gameTime datetime, " + "opponentName text, " + "opponentAge text," + "userHand int, " + "opponentHand Text, "+ "status Text); ";
            db.execSQL(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changeProfilephoto(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profile_image.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
