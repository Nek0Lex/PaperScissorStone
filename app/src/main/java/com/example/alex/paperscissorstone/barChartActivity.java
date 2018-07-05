package com.example.alex.paperscissorstone;

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
import android.view.Menu;
import android.view.View;


public class barChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PanelBar(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
         return true;
    }


    class PanelBar extends View {

        private int ScrHeight;
        private int ScrWidth;

        private Paint[] arrPaintArc;
        private Paint PaintText = null;

        final int[] colors = new int[]{
                Color.RED,
                Color.WHITE,
                Color.GREEN,
                Color.YELLOW,
                Color.BLUE,
        };

        final float arrPer[] = new float[]{20f, 30f, 10f, 40f};

        //柱形图演示用的比例,实际使用中，即为外部传入的比例参数
        private final int[] arrNum = {1, 4, 3, 2};

        public PanelBar(Context context) {
            super(context);

            //屏幕信息
            DisplayMetrics dm = getResources().getDisplayMetrics();
            ScrHeight = dm.heightPixels;
            ScrWidth = dm.widthPixels;

            //设置边缘特殊效果
            BlurMaskFilter PaintBGBlur = new BlurMaskFilter(1, BlurMaskFilter.Blur.INNER);

            arrPaintArc = new Paint[5];
           // Resources res = this.getResources();
            for (int i = 0; i < 5; i++) {
                arrPaintArc[i] = new Paint();
                arrPaintArc[i].setColor(colors[i]);
                arrPaintArc[i].setStyle(Paint.Style.FILL);
                arrPaintArc[i].setStrokeWidth(4);
                arrPaintArc[i].setMaskFilter(PaintBGBlur);
            }
            PaintText = new Paint();
            PaintText.setColor(Color.BLUE);
            PaintText.setTextSize(30);
            PaintText.setTypeface(Typeface.DEFAULT_BOLD);
        }

        public void onDraw(Canvas canvas) {
            //画布背景
            canvas.drawColor(Color.WHITE);

            //饼图标题
            canvas.drawText("自绘柱形图", 100, 50, PaintText);

            arrPaintArc[0].setTextSize(25);
            arrPaintArc[3].setTextSize(25);

            int i = 0;

            int lnWidth = 10; //标识线宽度
            int lnSpace = 40; //标识间距

            int startx = 120;
            int endx = startx + 20;

            int starty = ScrHeight/3;
            int endy = ScrHeight/3;

            int initX = startx;
            int initY = starty;

            int rectHeight = 10; //柱形框高度
            for (i = 0; i < 5; i++) {
                starty = initY - (i + 1) * lnSpace;
                endy = starty;
                if (i == 0) continue;
                canvas.drawLine(startx - lnWidth, starty + lnSpace, initX, endy+lnSpace , PaintText);
                canvas.drawText(Integer.toString(i), initX - 30, endy + lnSpace, PaintText);
            }

            // Y 轴
            canvas.drawLine(startx, starty, initX, initY, PaintText);


            //X 轴
            for (i = 0; i < arrNum.length; i++) {
                startx = initX + (i + 1) * lnSpace;
                endx = startx;

                //柱形
                canvas.drawRect(startx - rectHeight, initY,startx + rectHeight, initY - arrNum[i] * lnSpace, arrPaintArc[0]);

                //标识文字
                canvas.drawText(Integer.toString(arrNum[i]), startx, initY + lnSpace, arrPaintArc[0]);

            }
            canvas.drawLine(initX, initY, ScrWidth - ScrWidth/2, initY, PaintText);

            //////////////////////////////////////////////////////////////////
            /////////////////////////
            //横向柱形图
            ///////////////////////////
            startx = 120;// ScrWidth / 2 - 50;
            endx = startx + 20;

            starty = ScrHeight - ScrHeight / 3;
            endy = ScrHeight - ScrHeight / 3;

            initX = startx;
            initY = starty;

            // Y 轴  传入参数及柱形
            for (i = 0; i < arrNum.length; i++) {
                starty = initY - (i + 1) * lnSpace; //起始线要提高一位
                endy = starty;

                canvas.drawLine(startx - lnWidth, starty, initX, endy, PaintText);
                //文字 偏移30，下移10
                canvas.drawText(Integer.toString(arrNum[i]), initX - 30, endy + 10, arrPaintArc[3]);
                //柱形
                canvas.drawRect(initX, endy,
                        initX + arrNum[i] * lnSpace, endy + rectHeight, arrPaintArc[3]);
            }

            // Y 轴
            canvas.drawLine(startx, starty - 30, initX, initY, PaintText);

            // X 轴 刻度与标识
            for (i = 0; i < 6; i++) {
                startx = initX + (i + 1) * lnSpace;
                endx = startx;
                //canvas.drawLine( startx  ,initY ,startx ,initY + lnSpace, PaintText);
                canvas.drawLine(startx, initY, startx, endy, PaintText);
                //文字右移10位
                canvas.drawText(Integer.toString(i + 1), startx - 10, initY + lnSpace, PaintText);
            }
            // X 轴
            canvas.drawLine(initX, initY, ScrWidth - 10, initY, PaintText);
            ///////////////////////////////


        }
    }
}



