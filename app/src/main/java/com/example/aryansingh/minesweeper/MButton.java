package com.example.aryansingh.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by Aryan Singh on 6/19/2018.
 */

public class MButton extends AppCompatButton{

    //private int player = MainActivity.noPlayer;
    private int num = 0;
    private int x;
    private int y;

    public MButton(Context context)
    {
        super(context);
    }

    public void setValue(int num){
        this.num = num;
    }
    public int getValue(){
        return this.num;
    }

    public void setCoord(int x, int y){
        this.x = x;
        this.y = y;

    }

    public int getcX(){
        return this.x;
    }

    public int getcY(){
        return this.y;
    }


}
