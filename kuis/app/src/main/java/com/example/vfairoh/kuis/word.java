package com.example.vfairoh.kuis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class word extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ_WORD = 1;

    public static final String SHARED_PREFS_WORD = "sharedPrefs";
    public static final String KEY_HIGHSCORE_WORD = "keyHighscore";

    private TextView txt_highscore;
    private int high_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        txt_highscore = findViewById(R.id.high_score);
        loadHighscore();

        Button buttonstart = findViewById(R.id.button2);
        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(word.this,kuisWord.class);
                startActivityForResult(intent, REQUEST_CODE_QUIZ_WORD);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_QUIZ_WORD){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(kuisWord.EXTRA_SCORE_WORD, 0);
                if(score > high_score){
                    updateHighscore(score);
                }
            }
        }
    }

    private void loadHighscore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_WORD, MODE_PRIVATE);
        high_score = prefs.getInt(KEY_HIGHSCORE_WORD, 0);
        txt_highscore.setText("Highscore: " + high_score);
    }

    private void updateHighscore(int highscoreNew){
        high_score = highscoreNew;
        txt_highscore.setText("Highscore: " + high_score);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_WORD, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE_WORD, high_score);
        editor.apply();
    }
}
