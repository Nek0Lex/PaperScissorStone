package com.example.alex.paperscissorstone;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
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


import static java.util.Calendar.getInstance;

public class RegisterActivity extends AppCompatActivity{
    Calendar myCalendar;
    EditText name;
    EditText dob;
    EditText phoneNum;
    AutoCompleteTextView email;
    TextView test;

//    String inputName= name.getText().toString();
//    String inputEmail = email.getText().toString();
//    String inputDob = dob.getText().toString();
//    String inputPhone = phoneNum.getText().toString();

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
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, emailDomain);
        email = findViewById(R.id.email);
        email.setAdapter(adapter);
        name = findViewById(R.id.name);
        phoneNum = findViewById(R.id.phoneNum);
        test = findViewById(R.id.test);

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

        SharedPreferences settings = getSharedPreferences("registerPref", 0);

    }

    private static final String[] emailDomain = new String[] {
             "@gmail.com", "@yahoo.com","@hotmail.com","@live.com", "@facebook.com", "@msn.com","@qq.com", "@naver.com"
    };

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
            test.setText(inputName);
        } catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}


