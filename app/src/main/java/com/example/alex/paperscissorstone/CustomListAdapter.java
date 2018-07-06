package com.example.alex.paperscissorstone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class CustomListAdapter extends ArrayAdapter {
    private final Activity context;
    private final String[] gameNo;
    private final String[] gameDate;
    private final String[] gameTime;
    private final String[] opponentName;
    private final String[] opponentAge;
    private final String[] yourHand;
    private final String[] opponentHand;
    private final String[] status;
    private final String username;
    int orientation;

    public CustomListAdapter(Activity context, String[] gameNo , String[] gameDate, String[] gameTime, String[] opponentName, String[] opponentAge,
                             String[] yourHand, String[] opponentHand, String[] status, String username){
        super(context, R.layout.listview_row, gameNo);
        this.context = context;
        this.gameNo = gameNo;
        this.gameDate = gameDate;
        this.gameTime = gameTime;
        this.opponentName = opponentName;
        this.opponentAge = opponentAge;
        this.yourHand = yourHand;
        this.opponentHand = opponentHand;
        this.status = status;
        this.username = username;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        ImageView userHandID = (ImageView) rowView.findViewById(R.id.userHandID);
        TextView resultStatus = (TextView) rowView.findViewById(R.id.resultStatus);
        TextView playersName = (TextView) rowView.findViewById(R.id.playersName);
        TextView gamedate = (TextView) rowView.findViewById(R.id.gamedate);
        ImageView oppoHandID = (ImageView) rowView.findViewById(R.id.oppoHandID);



        //this code sets the values of the objects to values from the arrays
        switch (Integer.parseInt(yourHand[position])){
            case 0:
                userHandID.setImageResource(R.drawable.paper86);
                break;
            case 1:
                userHandID.setImageResource(R.drawable.scissors86);
                break;
            case 2:
                userHandID.setImageResource(R.drawable.stone86);
                break;
        }

        switch (Integer.parseInt(opponentHand[position])){
            case 0:
                oppoHandID.setImageResource(R.drawable.paper86);
                break;
            case 1:
                oppoHandID.setImageResource(R.drawable.scissors86);
                break;
            case 2:
                oppoHandID.setImageResource(R.drawable.stone86);
                break;
        }
        //oppoHandID.setText(opponentHand[position]);

        gamedate.setText(gameDate[position]+" "+gameTime[position]);

        resultStatus.setText(status[position]);

        if (status[position].equals("Win")){
            resultStatus.setTextColor(Color.GREEN);
        } else if (status[position].equals("Lose")){
            resultStatus.setTextColor(Color.RED);
        } else {
            resultStatus.setTextColor(Color.GRAY);
        }
        playersName.setText(username+" V.S "+opponentName[position]);
        return rowView;

    };

}
