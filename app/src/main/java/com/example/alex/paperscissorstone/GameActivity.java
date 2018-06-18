package com.example.alex.paperscissorstone;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class GameActivity extends AppCompatActivity {
    TextView name;
    TextView age;
    Button start;
    ImageButton btn_paper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        name = findViewById(R.id.name);
        btn_paper = findViewById(R.id.btn_paper);
        start = findViewById(R.id.btn_start);
        age = findViewById(R.id.age);
    }

    public void playAgain(View view) {
        new TransTask().execute("https://h4vttbs0ai.execute-api.ap-southeast-1.amazonaws.com/ptms");
    }

    public void playStart(View view) {
        new TransTask().execute("https://h4vttbs0ai.execute-api.ap-southeast-1.amazonaws.com/ptms");
        start.setVisibility(View.INVISIBLE);
    }


    class TransTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                StringBuilder sb = new StringBuilder();
                try {
                    URL url = new URL(params[0]);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(url.openStream()));
                    String line = in.readLine();
                    while (line != null) {
                        Log.d("HTTP", line);
                        sb.append(line);
                        line = in.readLine();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("JSON", s);
                parseJSON(s);
            }


        }

    private void parseJSON(String s) {
        try {
            String username = new JSONObject(s).getString("name");
            name.setText("Name: " + username);
            String userage = new JSONObject(s).getString("age");
            age.setText("Age: " + userage);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}



