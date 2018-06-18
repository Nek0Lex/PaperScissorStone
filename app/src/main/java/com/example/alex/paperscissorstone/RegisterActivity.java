package com.example.alex.paperscissorstone;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static java.util.Calendar.getInstance;

public class RegisterActivity extends AppCompatActivity{
    Calendar myCalendar;
    EditText name;
    EditText dob;
    EditText phoneNum;
    AutoCompleteTextView email;
    SharedPreferences user;
    final String email_pattern = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(email_pattern);
    private Matcher matcher;

    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myCalendar = getInstance();
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        phoneNum = findViewById(R.id.phoneNum);

        dob.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year,month,day);
                        dob.setText(format);
                    }
                }, mYear,mMonth, mDay).show();
            }
        });

        user = getSharedPreferences("registerPref", 0);
    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(monthOfYear + 1) + "/"
                + String.valueOf(dayOfMonth)+ "/"
                +String.valueOf(year);
    }


    public void register(View view) {
        try {

            String inputName = name.getText().toString();
            String inputEmail = email.getText().toString();
            String inputDob = dob.getText().toString();
            String inputPhone = phoneNum.getText().toString();

            if (inputName.isEmpty()||inputEmail.isEmpty()||inputDob.isEmpty()||inputPhone.isEmpty()) {
                if (inputName.isEmpty()) {
                    name.setError("This input cant be null");
                } else if (inputEmail.isEmpty()) {
                    email.setError("This input cant be null");
                } else if (inputDob.isEmpty()) {
                    dob.setError("This input cant be null");
                } else if (inputPhone.isEmpty()) {
                    phoneNum.setError("This input cant be null");
                }
            } else if (!validateEmail(inputEmail)){
                    email.setError("Not a correct email");
            } else if (phoneNum.getText().length()>8){
                    phoneNum.setError("Over 8 char");
            } else {
                user.edit()
                        .putString("name",inputName)
                        .putString("email", inputEmail)
                        .putString("dob", inputDob)
                        .putString("phone", inputPhone)
                        .apply();
                Intent i = new Intent(this,MainMenuActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }

        } catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void cancel(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
