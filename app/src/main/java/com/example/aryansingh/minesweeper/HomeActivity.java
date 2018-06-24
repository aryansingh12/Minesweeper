package com.example.aryansingh.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    public static final String NAME_KEY = "name";
    public static final String DIFFICULTY = "difficulty";

    EditText editText;
    //SharedPreferences sharedPreferences;
    Button easy,medium,hard;
    String level = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editText = findViewById(R.id.editText);
        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);

       // sharedPreferences = getSharedPreferences("my_shared_preference",MODE_PRIVATE);
    }

    public void click(View view){

        if(view.getId() == R.id.easy)
            level = "easy";

        if(view.getId() == R.id.medium)
            level = "medium";

        if(view.getId() == R.id.hard)
            level = "hard";
    }

    public void start(View view){

        String name = editText.getText().toString();

        //SharedPreferences.Editor editor = sharedPreferences.edit();

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(NAME_KEY,name);
        intent.putExtra(DIFFICULTY,level);

        startActivity(intent);

    }
}
