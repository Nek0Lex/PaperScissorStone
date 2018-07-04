package com.example.alex.paperscissorstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
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
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import pl.droidsonroids.gif.GifImageView;



public class GameActivity extends AppCompatActivity {
    TextView opponame ,oppoage,result;
    Button start,playagain;
    ImageButton btnPaper, btnStone, btnScissor;
    ImageView oppoHandShow;
    GifImageView chinoGif;
    SQLiteDatabase db;
    String oppoAge, oppoName;
    int oppoHand, userHand;
    int roundCount=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        chinoGif = findViewById(R.id.chino);
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

        initDB();
    }

    public void playStart(View view) {
        new TransTask().execute("https://h4vttbs0ai.execute-api.ap-southeast-1.amazonaws.com/ptms");
        oppoHandShow.setVisibility(View.INVISIBLE);
        start.setVisibility(View.INVISIBLE);
        btnPaper.setClickable(true);
        btnScissor.setClickable(true);
        btnStone.setClickable(true);

    }

    public void playAgain(View view) {
        new TransTask().execute("https://h4vttbs0ai.execute-api.ap-southeast-1.amazonaws.com/ptms");
        playagain.setVisibility(View.INVISIBLE);
        oppoHandShow.setVisibility(View.INVISIBLE);
        btnStone.setVisibility(View.VISIBLE);
        btnScissor.setVisibility(View.VISIBLE);
        btnPaper.setVisibility(View.VISIBLE);
        chinoGif.setVisibility(View.VISIBLE);
        result.setVisibility(View.INVISIBLE);
        userHand = -1;
        oppoHand = 0;
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
            oppoName = new JSONObject(s).getString("name");
            opponame.setText("Name: " + oppoName);
            oppoAge = new JSONObject(s).getString("age");
            oppoage.setText("Age: " + oppoAge);
            oppoHand = new JSONObject(s).getInt("hand");
            switch (oppoHand){
                case 0:
                    oppoHandShow.setImageResource(R.drawable.paper139r);
                    break;
                case 1:
                    oppoHandShow.setImageResource(R.drawable.scissors139r);
                    break;
                case 2:
                    oppoHandShow.setImageResource(R.drawable.stone139r);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void gameProgress(View view) {
        oppoHandShow.setVisibility(View.VISIBLE);
        result.setVisibility(View.VISIBLE);
        btnStone.setVisibility(View.INVISIBLE);
        btnScissor.setVisibility(View.INVISIBLE);
        btnPaper.setVisibility(View.INVISIBLE);
        chinoGif.setVisibility(View.INVISIBLE);
        opponame.setText("");
        oppoage.setText("");


        playagain.setVisibility(View.VISIBLE);
        switch (view.getId()){
            case R.id.btn_paper:
                btnPaper.setVisibility(View.VISIBLE);
                userHand = 0;
                if(oppoHand == 0)
                    result.setText("打和！super!");
                else if (oppoHand == 1)
                    result.setText("You Lose!");
                else if (oppoHand == 2)
                    result.setText("You Win");
                break;
            case R.id.btn_stone:
                btnStone.setVisibility(View.VISIBLE);
                userHand = 2;
                if (oppoHand == 0)
                    result.setText("You Lose!");
                else if (oppoHand == 1)
                    result.setText("You Win");
                else if (oppoHand == 2)
                    result.setText("打和！super!");
                break;
            case R.id.btn_scissor:
                userHand = 1;
                btnScissor.setVisibility(View.VISIBLE);
                if (oppoHand == 0)
                    result.setText("You Win");
                else if (oppoHand == 1)
                    result.setText("打和！super!");
                else if (oppoHand == 2)
                    result.setText("You Lose");
                break;
        }

        if (roundCount > 1){
            playagain.setVisibility(View.VISIBLE);
            start.setVisibility(View.INVISIBLE);
        }

        recordToDatabase();
    }

    public void initDB(){
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.alex.paperscissorstone/gamelogDB", null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);
            String sql;
            sql = "DROP TABLE if exists Gamelog;";
            db.execSQL(sql);
            sql = "CREATE TABLE Gamelog(" + "gameNo int PRIMARY KEY, " + "gameDate datetime, "
                    + "opponentName text, " + "opponentAge text," + "userHand int, " + "opponentHand Text); ";
            db.execSQL(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void recordToDatabase(){
        try {
        db = SQLiteDatabase.openDatabase("/data/data/com.example.alex.paperscissorstone/gamelogDB", null,
                SQLiteDatabase.OPEN_READWRITE);
        //GamesLog (gameNo, gamedate, gametime, opponentName, opponentAge, yourHand, opponentHand)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String gamedate = sdf.format(new Date());
            ContentValues insertValues = new ContentValues();
            insertValues.put("gameNo", roundCount);
            insertValues.put("gameDate", gamedate);
            insertValues.put("opponentName", oppoName);
            insertValues.put("opponentAge", oppoAge);
            insertValues.put("userHand", 1);
            insertValues.put("opponentHand", oppoHand);
            db.insert("Gamelog", null, insertValues);
            db.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}



