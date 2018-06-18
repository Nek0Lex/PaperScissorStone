package com.example.alex.paperscissorstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText name;
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
    }


    public void registerProcess(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }


    public void loginprocess(View view) {
        String username = name.getText().toString();
        String password = passwordInput.getText().toString();


    }
}
