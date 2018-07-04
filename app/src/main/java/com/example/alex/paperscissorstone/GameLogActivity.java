package com.example.alex.paperscissorstone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLogActivity extends AppCompatActivity {
    ListView gameLog;
    String [] gameNo,gameDate,opponentName,opponentAge,yourHand,opponentHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_log);
        gameLog = findViewById(R.id.gameLog);
        //gameLog.setEmptyView(findViewById(R.id.empty));

        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.alex.paperscissorstone/gamelogDB", null,
                    SQLiteDatabase.OPEN_READWRITE);
            Cursor c = db.rawQuery("SELECT COUNT(*) FROM Gamelog", null);
            c.moveToNext();
            int dataCount = c.getInt(0);
            gameNo = new String[dataCount];
            gameDate = new String[dataCount];
            opponentName = new String[dataCount];
            opponentAge = new String[dataCount];

            c = db.rawQuery("SELECT * FROM Gamelog", null);
            int i = 0;
            while (c.moveToNext()) {
                String gameno = c.getString(c.getColumnIndex("gameNo"));
                String gamedate = c.getString(c.getColumnIndex("gameDate"));
                gameNo[i] = gameno;
                gameDate[i] = gamedate;
                i++;
            }
            c.close();

            db.close();

            ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gameNo);
            gameLog.setAdapter(aa);

        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
