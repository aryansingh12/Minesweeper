package com.example.aryansingh.minesweeper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aryansingh.minesweeper.MButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{

    LinearLayout rootLayout;

    public final int MINE = -1;

    public boolean longPress = false;


    public MButton button12;

    private boolean firstClick = true;

    public ArrayList<LinearLayout> rows;
    public MButton[][] board;
    public int size = 0;

//    private int x[] = {-1,-1,-1,0,0,1,1,1};
//    private int y[] = {-1,0,1,-1,1,-1,0,1};

    public int neighborcount = 0;

    public int mines = 0; // number of mines

    String name;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBoard();

        Intent intent = getIntent();
        name = intent.getStringExtra(HomeActivity.NAME_KEY);
        level = intent.getStringExtra(HomeActivity.DIFFICULTY);

        if(level.equals("easy")){
            size = 8;
            mines = 10;
        }
        if(level.equals("medium")){
            size = 10;
            mines = 40;
        }
        if(level.equals("hard")){
            size = 12;
            mines = 100;
        }

    }

    public void setupBoard(){ // can also use for reset

        rows = new ArrayList<>();


        rootLayout = findViewById(R.id.rootLayout);
        board = new MButton[size][size];
        rootLayout.removeAllViews();

        // making a new linear layout for the textview
        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,0);
        linearLayout1.setLayoutParams(layoutParams1);
        rootLayout.addView(linearLayout1);
        TextView tv = new TextView(this);
        tv.setLayoutParams(layoutParams1);
        tv.setBackgroundColor(Color.RED);


        // adding the linear layouts
        for(int i=0;i<size;i++){
            LinearLayout linearLayout = new LinearLayout(this );
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);

            linearLayout.setLayoutParams(layoutParams);

            rootLayout.addView(linearLayout); // adding to the root layout
            rows.add(linearLayout);

        }


        for(int i=0;i<size;i++){ // row

            for(int j=0;j<size;j++){ // column

                MButton button = new MButton(this);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);



                button.setLayoutParams(layoutParams);

               button.setBackgroundResource(R.drawable.circle);


                button.setOnClickListener(this);
                button.setOnLongClickListener(this);

                LinearLayout row = rows.get(i);
                row.addView(button);

                button.setCoord(i,j);
                board[i][j] = button;

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public void onClick(View view) {

        MButton button = (MButton)view;// this button is clicked

        if (firstClick) {

            button12 = button;
            setMines();
            clearZeroes(button);
            firstClick = false;

            return;
        }
        else {
            if (button.getValue() == MINE) { // game is over
                button.setBackgroundResource(R.drawable.mine);
                over(button);
            } else {

                //did not press a mine
                //open the neighbors
                //open the zeroes -- if pressed on a zero
                //show the number -- pressed on a number

                if (button.getValue() == 0) {
                    clearZeroes(button);
                } else {
                    button.setBackgroundResource(R.drawable.color_bg);
                    button.setText(button.getValue() + "");
                    button.setEnabled(false);
                }
            }
        }
    }


    @Override
    public boolean onLongClick(View view) {
        MButton button = (MButton) view;
        button.setBackgroundResource(R.drawable.mine);
        button.setText("F");
        button.setEnabled(false);

        return true;
    }

    public void clearZeroes(MButton button){

        int x = button.getcX();
        int y = button.getcY();

        if(button.getValue() == MINE){
            button.setBackgroundResource(R.drawable.mine);
            return;
        }

        button.setBackgroundResource(R.drawable.color_bg);

        if(button.getValue() != MINE && button.getValue() > 0){
            button.setText(button.getValue() + "");
            button.setEnabled(false);
            return;
        }

        else {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if ((x + i) >= 0 && (x + i) < board.length && (y + j) >= 0 && (y + j) < board.length) {

                        if (i == 0 && j == 0) {
                            board[x + i][y + j].setEnabled(false);
                            board[x + i][y + j].setBackgroundResource(R.drawable.color_bg);
                            continue;
                        }
                        else {
                            if (board[x + i][y + j].getValue() != MINE && board[x + i][y + j].isEnabled()) {
                                board[x + i][y + j].setText("");
                                clearZeroes(board[x + i][y + j]);
                            }
                        }
                    }
                }
            }
        }
    }

    public void setMines() { // setting random mines on the board

        int x = button12.getcX();
        int y = button12.getcY();


        // adding the neighbors to the list
//        ArrayList<MButton> list = new ArrayList<>();
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <= 1; j++) {
//                if ((x + i) >= 0 && (x + i) < board.length && (y + j) >= 0 && (y + j) < board.length) {
//                    list.add(board[x + i][y + i]);
//                }
//            }
//        }



        // if the random button is a neighbor , don't make it a mine
        int k=0;
        while(k<mines) {

            int a = (int) (Math.random() * board.length);
            int b = (int) (Math.random() * board.length);

            MButton button2 = board[a][b];

            if(a == x && b == y){
                continue;
            }
                button2.setValue(-1);
                k++;
        }
        setNeighborcount();
    }

        // when you press a mine
        public void over(MButton button){
            Toast.makeText(this,"Game Over",Toast.LENGTH_LONG).show();
            for(int i=0;i<board.length;i++){
                for(int j=0;j<board[0].length;j++){
                    MButton button1 = board[i][j];
                    if(button1.getValue() == MINE){
                        button1.setBackgroundResource(R.drawable.mine);
                    }
                    button1.setEnabled(false);
                }
            }
        }

//
//        public void neighborcount(MButton button) {
//
//            // button is a mine
//
//            int x = button.getcX();
//            int y  = button.getcY();
//
//            for (int i = -1; i <= 1; i++) {
//                for (int j = -1; j <= 1; j++) {
//                    if(i==0 && j==0)
//                        continue;
//                    if ( (x + i >= 0) && (x + i < board.length) && (y + j >= 0) && (y + j < board.length) ) {
//                        if(board[x+i][y+j].getValue() != MINE && board[x+i][y+j].isEnabled() )
//                        board[x+i][y+j].setValue(board[x+i][y+j].getValue() + 1);
//                    }
//                }
//            }
//        }



    public void setNeighborcount() { // sets the value of each and every button

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[0].length; j++) {

                int count = 0;

                if (board[i][j].getValue() != MINE && board[i][j].isEnabled()) { // not a mine

                    if (i > 0 && board[i - 1][j].getValue() == MINE)//up
                        count++;

                    if (i < board.length - 1 && board[i + 1][j].getValue() == MINE) // down
                        count++;

                    if (j < board[0].length - 1 && board[i][j + 1].getValue() == MINE) // right
                        count++;

                    if (j > 0 && board[i][j - 1].getValue() == MINE) // left
                        count++;

                    if (i > 0 && j > 0 && board[i - 1][j - 1].getValue() == MINE) // up left
                        count++;

                    if (i > 0 && j < board[0].length - 1 && board[i - 1][j + 1].getValue() == MINE) // up right
                        count++;

                    if (j > 0  && i < board.length - 1 && board[i + 1][j - 1].getValue() == MINE)//down left
                        count++;

                    if (i < board.length - 1 && j < board[0].length - 1 && board[i + 1][j + 1].getValue() == MINE)//down right
                        count++;

                    board[i][j].setValue(count);

                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.reset){
            firstClick = true;
            setupBoard();
        }
        else if(id == R.id.easy){
            firstClick = true;
            size = 8;
            mines = 10;
            setupBoard();
        }
        else if(id == R.id.medium){
            firstClick = true;
            size = 10;
            mines = 40;
            setupBoard();
        }
        else if(id == R.id.difficult){
            firstClick = true;
            size = 12;
            mines = 100;
            setupBoard();

        }
        return super.onOptionsItemSelected(item);
    }

}

