package com.example.alex.paperscissorstone;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Calendar.getInstance;

public class EditUserActivity extends AppCompatActivity {
    Calendar myCalendar;
    EditText name;
    EditText dob;
    EditText phoneNum;
    AutoCompleteTextView email;
    TextView test;
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

    public void editUser(View view) {
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
            startActivity(i);
        }

//        if (phoneNum.getText().length()>8){
//            phoneNum.setError("Not over 8 char");
//        } else if (inputName.isEmpty()||inputEmail.isEmpty()||inputDob.isEmpty()||inputPhone.isEmpty()){
//            Toast.makeText(this,"Cant be null",Toast.LENGTH_SHORT).show();
//        } else {
//            user.edit()
//                    .putString("name",inputName)
//                    .putString("email", inputEmail)
//                    .putString("dob", inputDob)
//                    .putString("phone", inputPhone)
//                    .apply();
//            Intent i = new Intent(this,MainMenuActivity.class);
//            startActivity(i);
//        }
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

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
