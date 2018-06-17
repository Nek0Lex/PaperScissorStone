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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phoneNum);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);

        try {
            Intent intent = getIntent();
            name.setText(intent.getStringExtra("name"));
            email.setText(intent.getStringExtra("email"));
            phone.setText(intent.getStringExtra("phone"));
            dob.setText(intent.getStringExtra("dob"));
        } catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void backToRegister(View view) {
        Intent i =  new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
