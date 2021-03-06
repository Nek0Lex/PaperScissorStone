package com.example.alex.paperscissorstone;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import pl.droidsonroids.gif.GifImageView;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;


public class GameActivity extends AppCompatActivity {
    TextView opponame ,oppoage,result;
    Button start,playagain, btnBack;
    ImageButton btnPaper, btnStone, btnScissor;
    ImageView oppoHandShow;
    GifImageView chinoGif;
    SQLiteDatabase db;
    String oppoAge, oppoName, winLoseStatus;
    int oppoHand, userHand, orientation;
    int roundCount;
    String cheatmode;
    SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orientation = getResources().getConfiguration().orientation;
        if (orientation == ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_game);
        } else if (orientation == ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_game_landscape);
        }
        chinoGif = findViewById(R.id.chino);
        opponame = findViewById(R.id.name);
        btnPaper = findViewById(R.id.btn_paper);
        btnStone = findViewById(R.id.btn_stone);
        btnScissor = findViewById(R.id.btn_scissor);
       // start = findViewById(R.id.btn_start);
        oppoage = findViewById(R.id.age);
        oppoHandShow = findViewById(R.id.oppoHandImage);
        result = findViewById(R.id.gameresult);
        playagain = findViewById(R.id.btn_playagain);
        btnBack = findViewById(R.id.btn_back);
        playagain.setVisibility(View.INVISIBLE);
        user = getSharedPreferences("registerPref", 0);
        cheatmode = getSharedPreferences("registerPref", MODE_PRIVATE).getString("cheatmode","false");

//        btnStone.setClickable(false);
//        btnPaper.setClickable(false);
//        btnScissor.setClickable(false);
        playStart();
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

    public void playStart() {
        new TransTask().execute("https://h4vttbs0ai.execute-api.ap-southeast-1.amazonaws.com/ptms");
        if (cheatmode.equals("false")) {
            oppoHandShow.setVisibility(View.INVISIBLE);
            chinoGif.setVisibility(View.VISIBLE);
        } else if (cheatmode.equals("true")){
            oppoHandShow.setVisibility(View.VISIBLE);
            chinoGif.setVisibility(View.INVISIBLE);
        }
        btnPaper.setClickable(true);
        btnScissor.setClickable(true);
        btnStone.setClickable(true);
    }

    public void playAgain(View view) {
        new TransTask().execute("https://h4vttbs0ai.execute-api.ap-southeast-1.amazonaws.com/ptms");
        playagain.setVisibility(View.INVISIBLE);
        if (cheatmode.equals("false")) {
            oppoHandShow.setVisibility(View.INVISIBLE);
            chinoGif.setVisibility(View.VISIBLE);
        } else if (cheatmode.equals("true")){
            oppoHandShow.setVisibility(View.VISIBLE);
            chinoGif.setVisibility(View.INVISIBLE);
        }
        btnStone.setVisibility(View.VISIBLE);
        btnScissor.setVisibility(View.VISIBLE);
        btnPaper.setVisibility(View.VISIBLE);

        result.setVisibility(View.INVISIBLE);
        userHand = -1;
        oppoHand = 0;
        roundCount++;
    }

    public void backtomenu(View view) {
        Intent i = new Intent(this,MainMenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
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
                if(oppoHand == 0) {
                    result.setText("Tie!");
                    winLoseStatus = "Tie";
                } else if (oppoHand == 1) {
                    result.setText("You Lose!");
                    winLoseStatus = "Lose";
                } else if (oppoHand == 2) {
                    result.setText("You Win");
                    winLoseStatus = "Win";
                }
                break;
            case R.id.btn_stone:
                btnStone.setVisibility(View.VISIBLE);
                userHand = 2;
                if (oppoHand == 0) {
                    result.setText("You Lose!");
                    winLoseStatus = "Lose";
                } else if (oppoHand == 1){
                    result.setText("You Win");
                    winLoseStatus = "Win";
                } else if (oppoHand == 2) {
                    result.setText("Tie!");
                    winLoseStatus = "Tie";
                }
                break;
            case R.id.btn_scissor:
                userHand = 1;
                btnScissor.setVisibility(View.VISIBLE);
                if (oppoHand == 0) {
                    result.setText("You Win");
                    winLoseStatus = "Win";
                } else if (oppoHand == 1) {
                    result.setText("Tie");
                    winLoseStatus = "Tie";
                } else if (oppoHand == 2) {
                    result.setText("You Lose");
                    winLoseStatus = "Lose";
                }
                break;
        }

        recordToDatabase();
        if (roundCount > 1){
            playagain.setVisibility(View.VISIBLE);
            //start.setVisibility(View.INVISIBLE);
        }
    }

    public void recordToDatabase(){
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.alex.paperscissorstone/gamelogDB", null,
                    SQLiteDatabase.OPEN_READWRITE);
            //GamesLog (gameNo, gamedate, gametime, opponentName, opponentAge, yourHand, opponentHand, status)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
            Date currentTime = Calendar.getInstance().getTime();

            String gamedate = sdf.format(new Date());
            String gametime = sdfTime.format(new Date());

//            Cursor c = db.rawQuery("SELECT MAX(gameNo) FROM Gamelog", null);
//            c.moveToNext();
//            String myString = c.getString(0);
//            if (myString == null || myString.isEmpty()) {
//                roundCount = 1;
//            } else {
//                roundCount = c.getInt(0)+1;
//            }

            ContentValues insertValues = new ContentValues();
            //insertValues.put("gameNo", roundCount);
            insertValues.put("gameTime", gametime);
            insertValues.put("gameDate", gamedate);
            insertValues.put("opponentName", oppoName);
            insertValues.put("opponentAge", oppoAge);
            insertValues.put("userHand", userHand);
            insertValues.put("opponentHand", oppoHand);
            insertValues.put("status", winLoseStatus);
            db.insert("Gamelog", null, insertValues);
            db.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}



