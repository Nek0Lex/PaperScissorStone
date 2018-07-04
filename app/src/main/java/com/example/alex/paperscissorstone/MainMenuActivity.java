package com.example.alex.paperscissorstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainMenuActivity extends Activity {
    String NOTES = "notes.txt";
    TextView name;
    TextView phone;
    TextView email;
    TextView dob;
    String username,useremail, phoneNumber, Dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phoneNum);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);
        username = getSharedPreferences("registerPref", MODE_PRIVATE).getString("name", "");
        useremail = getSharedPreferences("registerPref", MODE_PRIVATE).getString("email", "");
        phoneNumber = getSharedPreferences("registerPref", MODE_PRIVATE).getString("phone", "");
        Dob = getSharedPreferences("registerPref", MODE_PRIVATE).getString("dob", "");

        try {
            name.setText(username);
            email.setText(useremail);
            phone.setText(phoneNumber);
            dob.setText(Dob);
        } catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void backToRegister(View view) {
        Intent i =  new Intent(this, RegisterActivity.class);
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
}
