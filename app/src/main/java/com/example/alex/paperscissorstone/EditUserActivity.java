package com.example.alex.paperscissorstone;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static java.util.Calendar.getInstance;

public class EditUserActivity extends AppCompatActivity {
    Calendar myCalendar;
    EditText name;
    EditText dob;
    EditText phoneNum;
    AutoCompleteTextView email;
    TextView test;
    SharedPreferences user;

    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        myCalendar = getInstance();
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        phoneNum = findViewById(R.id.phoneNum);

        dob.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year,month,day);
                        dob.setText(format);
                    }
                }, mYear,mMonth, mDay).show();
            }
        });
        name.setText(getSharedPreferences("registerPref", MODE_PRIVATE).getString("name", ""));
        email.setText(getSharedPreferences("registerPref", MODE_PRIVATE).getString("email", ""));
        phoneNum.setText(getSharedPreferences("registerPref", MODE_PRIVATE).getString("phone", ""));
        dob.setText(getSharedPreferences("registerPref", MODE_PRIVATE).getString("dob", ""));
        user = getSharedPreferences("registerPref", 0);
    }

    public void editUser(View view) {
        String inputName = name.getText().toString();
        String inputEmail = email.getText().toString();
        String inputDob = dob.getText().toString();
        String inputPhone = phoneNum.getText().toString();

        if (phoneNum.getText().length()>8){
            phoneNum.setError("Not over 8 char");
        } else if (inputName.isEmpty()||inputEmail.isEmpty()||inputDob.isEmpty()||inputPhone.isEmpty()){
            Toast.makeText(this,"Cant be null",Toast.LENGTH_SHORT).show();
        } else {
            user.edit()
                    .putString("name",inputName)
                    .putString("email", inputEmail)
                    .putString("dob", inputDob)
                    .putString("phone", inputPhone)
                    .apply();
            Intent i = new Intent(this,MainMenuActivity.class);
            startActivity(i);
        }
    }

    public void cancel(View view) {
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(monthOfYear + 1) + "/"
                + String.valueOf(dayOfMonth)+ "/"
                +String.valueOf(year);
    }
}
