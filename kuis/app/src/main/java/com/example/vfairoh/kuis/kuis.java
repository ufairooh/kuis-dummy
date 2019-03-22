package com.example.vfairoh.kuis;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vfairoh.kuis.data.DbHelper;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class kuis extends AppCompatActivity {
    public static String EXTRA_SCORE = "extra score";
    public static final long COUNTDOWN_IN_MILLIS = 30000;
    private TextView soal;
    private TextView txtscore;
    private TextView count;
    private TextView timer;
    private RadioGroup radioGroup;
    private RadioButton radiobutton1;
    private RadioButton radiobutton2;
    private RadioButton radiobutton3;
    private Button confirm;
    private List<soal> questionList;
    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionCountTotal;
    private soal currentQuestion;
    private int score;
    private boolean answer;
    private long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuis);

        soal = findViewById(R.id.soal);
        txtscore = findViewById(R.id.score);
        count = findViewById(R.id.count);
        timer = findViewById(R.id.timer);
        radioGroup = findViewById(R.id.radiogroup);
        radiobutton1 = findViewById(R.id.radiobutton1);
        radiobutton2 = findViewById(R.id.radiobutton2);
        radiobutton3 = findViewById(R.id.radiobutton3);
        confirm = findViewById(R.id.confirm);

        textColorDefaultRb = radiobutton1.getTextColors();
        textColorDefaultCd = timer.getTextColors();

        DbHelper dbHelper = new DbHelper(this);
        questionList = dbHelper.getAllQuestion();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answer){
                    if(radiobutton1.isChecked() || radiobutton2.isChecked() || radiobutton3.isChecked()){
                        checkAnswer();
                    }
                    else{
                        Toast.makeText(kuis.this, "please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    showNextQuestion();
                }
            }
        });

    }

    private void showNextQuestion(){
        radiobutton1.setTextColor(textColorDefaultRb);
        radiobutton2.setTextColor(textColorDefaultRb);
        radiobutton3.setTextColor(textColorDefaultRb);
        radioGroup.clearCheck();

        if(questionCounter < questionCountTotal){
            currentQuestion = questionList.get(questionCounter);

            soal.setText(currentQuestion.getQuestion());
            radiobutton1.setText(currentQuestion.getOpta());
            radiobutton2.setText(currentQuestion.getOptb());
            radiobutton3.setText(currentQuestion.getOptc());

            questionCounter++;
            count.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answer = false;
            confirm.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        }
        else{
            finishQuiz();
        }
    }

    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();

            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountdownText();
                checkAnswer();

            }
        }.start();
    }

    private void updateCountdownText(){
        int minutes = (int)(timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timer.setText(timeFormat);

        if(timeLeftInMillis < 10000){
            timer.setTextColor(Color.RED);
        }
        else{
            timer.setTextColor(textColorDefaultCd);
        }
    }
    private void checkAnswer(){
        answer = true;
        countDownTimer.cancel();
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int jawaban = radioGroup.indexOfChild(rbSelected) + 1;

        if(jawaban == currentQuestion.getAnswer()){
            score++;
            txtscore.setText("Score :" + score);
        }
        showSolution();
    }

    private void showSolution(){
        radiobutton1.setTextColor(Color.RED);
        radiobutton2.setTextColor(Color.RED);
        radiobutton3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswer()){
            case 1:
                radiobutton1.setTextColor(Color.GREEN);
                soal.setText("A is Correct");
                break;
            case 2:
                radiobutton2.setTextColor(Color.GREEN);
                soal.setText("B is Correct");
                break;
            case 3:
                radiobutton3.setTextColor(Color.GREEN);
                soal.setText("C is Correct!");
                break;
        }
        if(questionCounter < questionCountTotal){
            confirm.setText("Next");
        }
        else{
            confirm.setText("finish");
        }
    }
    private void finishQuiz(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }
}
