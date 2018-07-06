package com.example.alex.paperscissorstone;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.View;


public class barChartActivity extends AppCompatActivity {
    SQLiteDatabase db;
    String winLoseStatus;
    String[] status;
    int winCount=0, loseCount=0, tieCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PanelBar(this));
        int panelHeight;
        int panelWidth;
        db =SQLiteDatabase.openDatabase("/data/data/com.example.alex.paperscissorstone/gamelogDB", null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM Gamelog", null);
        c.moveToNext();
        int dataCount = c.getInt(0);
        status = new String[dataCount];
        c = db.rawQuery("SELECT * FROM Gamelog", null);
        int i = 0;
        while (c.moveToNext()) {
            winLoseStatus = c.getString(c.getColumnIndex("status"));
            status[i] = winLoseStatus;
            switch (status[i]){
                case "Win":
                    winCount++;
                    break;
                case "Lose":
                    loseCount++;
                    break;
                case "Tie":
                    tieCount++;
                    break;
            }
            i++;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
         return true;
    }

    int panelHeight;
    int panelWidth;
    int maxX;
    int maxY;
    int wintop, losetop, tietop;

    class PanelBar extends View {

        public PanelBar(Context context) {
            super(context);
            Display mdisp = getWindowManager().getDefaultDisplay();
            Point mdispSize = new Point();
            mdisp.getSize(mdispSize);
            this.setBackgroundResource(R.drawable.gradientselector);
            maxX = mdispSize.x;
            maxY = mdispSize.y;
            wintop = 1536;
            losetop = 1536;
            tietop = 1536;
        }

        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            panelHeight = h;
            panelWidth = w;

        }

        public void onDraw(Canvas c) {
            Paint paint = new Paint(); paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.TRANSPARENT);
            c.drawPaint(paint);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(4);

            paint.setColor(Color.GREEN);
            c.drawRect(50, 150, 100,200, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            c.drawText(" = win(s)", 100+30, 185, paint);

            paint.setColor(Color.RED);
            c.drawRect(50, 250, 100,300, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            c.drawText(" = lose(s)", 100+30, 285, paint);

            paint.setColor(Color.GRAY);
            c.drawRect(50, 350, 100,400, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            c.drawText(" = tie(s)", 100+30, 385, paint);




            for (int i=0; i<status.length; i++){
                //top = 1536;
                switch (status[i]){
                    case "Win":
                        paint.setColor(Color.GREEN);
                        wintop = wintop-30;
                        c.drawRect(250, wintop, 350, panelHeight, paint);
                        break;
                    case "Lose":
                        paint.setColor(Color.RED);
                        losetop = losetop-30;
                        c.drawRect(550, losetop, 650, panelHeight, paint);
                        break;
                    case  "Tie":
                        paint.setColor(Color.GRAY);
                        tietop = tietop-30;
                        c.drawRect(850, tietop, 950, panelHeight, paint);
                        break;
                }
            }

            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            c.drawText(String.valueOf(winCount)+" wins", 250, wintop-25, paint);
            c.drawText(String.valueOf(loseCount)+ " loses", 550, losetop-25, paint);
            c.drawText(String.valueOf(tieCount)+ " ties", 850, tietop-25, paint);
        }
    }
}



