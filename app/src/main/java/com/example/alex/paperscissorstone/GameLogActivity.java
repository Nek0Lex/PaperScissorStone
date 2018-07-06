package com.example.alex.paperscissorstone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class GameLogActivity extends AppCompatActivity {
    ListView gameLog;
    String[] gameNo, gameDate, gameTime, opponentName, opponentAge, yourHand, opponentHand, status;
    TextView emptyText;
    SharedPreferences user;
    String username;
    int count=0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_log);
        gameLog = findViewById(R.id.gameLog);
        emptyText = findViewById(R.id.emptyText);
        //gameLog.setEmptyView(findViewById(R.id.empty));
        user = getSharedPreferences("registerPref", 0);
        username = getSharedPreferences("registerPref", MODE_PRIVATE).getString("name", "Guest");

        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.alex.paperscissorstone/gamelogDB", null,
                    SQLiteDatabase.OPEN_READWRITE);
            Cursor c = db.rawQuery("SELECT COUNT(*) FROM Gamelog", null);
            c.moveToNext();
            int dataCount = c.getInt(0);
            gameNo = new String[dataCount];
            gameDate = new String[dataCount];
            gameTime = new String[dataCount];
            opponentName = new String[dataCount];
            opponentAge = new String[dataCount];
            yourHand = new String[dataCount];
            opponentHand = new String[dataCount];
            status = new String[dataCount];

            c = db.rawQuery("SELECT * FROM Gamelog", null);
            int i = 0;
            while (c.moveToNext()) {
                String gameno = c.getString(c.getColumnIndex("gameNo"));
                String gamedate = c.getString(c.getColumnIndex("gameDate"));
                String gametime = c.getString(c.getColumnIndex("gameTime"));
                String oppoHand = c.getString(c.getColumnIndex("opponentHand"));
                String oppoName = c.getString(c.getColumnIndex("opponentName"));
                String oppoAge = c.getString(c.getColumnIndex("opponentAge"));
                String userHand = c.getString(c.getColumnIndex("userHand"));
                String winLoseStatus = c.getString(c.getColumnIndex("status"));

                gameNo[i] = gameno;
                gameDate[i] = gamedate;
                gameTime[i] = gametime;
                opponentHand[i] = oppoHand;
                opponentName[i] = oppoName;
                opponentAge[i] = oppoAge;
                yourHand[i] = userHand;
                status[i] = winLoseStatus;
                i++;
            }
            c.close();
            db.close();

            CustomListAdapter cla = new CustomListAdapter(this, gameNo, gameDate, gameTime, opponentName, opponentAge, yourHand, opponentHand, status, username);
            if (!cla.isEmpty()){
                emptyText.setVisibility(View.INVISIBLE);
            }
            gameLog.setAdapter(cla);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gamelogmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle item selection
        if (item.getItemId() == R.id.setting) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        }
        if (item.getItemId() == R.id.barchart) {
            Intent i = new Intent(this, barChartActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
