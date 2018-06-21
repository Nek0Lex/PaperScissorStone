package com.example.alex.paperscissorstone;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    TextView opponame ,oppoage,result;
    Button start,playagain;
    ImageButton btnPaper, btnStone, btnScissor;
    ImageView oppoHandShow;
    int oppoHand, roundCount=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        opponame = findViewById(R.id.name);
        btnPaper = findViewById(R.id.btn_paper);
        btnStone = findViewById(R.id.btn_stone);
        btnScissor = findViewById(R.id.btn_scissor);
        start = findViewById(R.id.btn_start);
        oppoage = findViewById(R.id.age);
        oppoHandShow = findViewById(R.id.oppoHandImage);
        result = findViewById(R.id.gameresult);
        playagain = findViewById(R.id.btn_playagain);
        playagain.setVisibility(View.INVISIBLE);
        btnStone.setClickable(false);
        btnPaper.setClickable(false);
        btnScissor.setClickable(false);
    }

    public void playStart(View view) {
        new TransTask().execute("https://h4vttbs0ai.execute-api.ap-southeast-1.amazonaws.com/ptms");
        start.setVisibility(View.INVISIBLE);
        btnPaper.setClickable(true);
        btnScissor.setClickable(true);
        btnStone.setClickable(true);

    }

    public void playAgain(View view) {
        new TransTask().execute("https://h4vttbs0ai.execute-api.ap-southeast-1.amazonaws.com/ptms");
        roundCount++;
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
            opponame.setText("Name: " + username);
            String userage = new JSONObject(s).getString("age");
            oppoage.setText("Age: " + userage);
            oppoHand = new JSONObject(s).getInt("hand");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void gameProgress(View view) {
        btnStone.setVisibility(View.INVISIBLE);
        btnScissor.setVisibility(View.INVISIBLE);
        btnPaper.setVisibility(View.INVISIBLE);
        opponame.setText("");
        oppoage.setText("");

        switch (oppoHand){
            case 0:
                oppoHandShow.setImageResource(R.drawable.paper139r);
            case 1:
                oppoHandShow.setImageResource(R.drawable.scissors139r);
            case 2:
                oppoHandShow.setImageResource(R.drawable.stone139r);
        }
        playagain.setVisibility(View.VISIBLE);
        switch (view.getId()){
            case R.id.btn_paper:
                if(oppoHand == 0)
                    result.setText("打和！super!");
                else if (oppoHand == 1)
                    result.setText("You Lose!");
                else if (oppoHand == 2)
                    result.setText("You Win");
            case R.id.btn_stone:
                if (oppoHand == 0)
                    result.setText("You Lose!");
                else if (oppoHand == 1)
                    result.setText("You Win");
                else if (oppoHand == 2)
                    result.setText("打和！super!");
            case R.id.btn_scissor:
                if (oppoHand == 0)
                    result.setText("You Win");
                else if (oppoHand == 1)
                    result.setText("打和！super!");
                else if (oppoHand == 2)
                    result.setText("You Lose");
        }

        if (roundCount > 1){
            playagain.setVisibility(View.VISIBLE);
            start.setVisibility(View.INVISIBLE);
        }
    }

}



