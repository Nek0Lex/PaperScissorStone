package com.example.alex.paperscissorstone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.getInstance;

public class RegisterActivity extends AppCompatActivity{
    Calendar myCalendar;
    EditText dob;
    DatePickerDialog dialog;
    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myCalendar = getInstance();
        dob = (EditText) findViewById(R.id.dob);

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
    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "/"
                + String.valueOf(monthOfYear + 1) + "/"
                + String.valueOf(dayOfMonth);
    }



}


