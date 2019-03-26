package com.example.vfairoh.kuis;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vfairoh.kuis.data.DbHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class kuisWord extends AppCompatActivity {
    public static String EXTRA_SCORE_WORD = "extra score";
    public static final long COUNTDOWN_IN_MILLIS = 30000;

    private static final String KEY_SCORE_W = "keyScoreW";
    private static final String KEY_QUESTION_COUNT_W = "keyQuestionCountW";
    private static final String KEY_MILLIS_LEFT_W = "keyMillisLeftW";
    private static final String KEY_ANSWERED_W = "keyAnsweredW";
    private static final String KEY_QUESTION_LIST_W = "keyQuestionListW";

    private TextView soal;
    private TextView txtscore;
    private TextView count;
    private TextView timer;
    private TextView answers;
    private Button opta;
    private Button optb;
    private Button optc;
    private ArrayList<soal> questionList;
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
        setContentView(R.layout.activity_kuis_word);

        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        soal = findViewById(R.id.soalWord);
        txtscore = findViewById(R.id.scoreWord);
        count = findViewById(R.id.countWord);
        timer = findViewById(R.id.timerWord);
        opta = findViewById(R.id.optaWord);
        optb = findViewById(R.id.optbWord);
        optc = findViewById(R.id.optcWord);
        answers = findViewById(R.id.answerWord);

        textColorDefaultRb = opta.getTextColors();
        textColorDefaultCd = timer.getTextColors();

        if(savedInstanceState == null) {
            DbHelper dbHelper = new DbHelper(this);
            questionList = dbHelper.getWordQuestion();
            questionCountTotal = questionList.size();

            showNextQuestion();
        }
        else{
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST_W);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT_W);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE_W);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT_W);
            answer = savedInstanceState.getBoolean(KEY_ANSWERED_W);

            if(!answer){
                startCountDown();
            }
            else{
                updateCountdownText();
            }
        }

        opta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                opta();
            }
        });

        optb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                optb();
            }
        });

        optc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                optc();
            }
        });

    }

    private void opta(){
        if(opta.getText().equals(answers.getText())){
            opta.setTextColor(Color.GREEN);
            optb.setTextColor(Color.RED);
            optc.setTextColor(Color.RED);
            score++;
            txtscore.setText("Score :" + score);
            if(questionCounter < questionCountTotal){
                AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
                msgBox.setMessage("Right Answer!");
                msgBox.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showNextQuestion();
                    }
                });
                msgBox.create().show();
            }
            else{
                done();
            }
        }
        else{
            if(optb.getText().equals(answers.getText())){

                opta.setTextColor(Color.RED);
                optb.setTextColor(Color.GREEN);
                optc.setTextColor(Color.RED);
                if(questionCounter < questionCountTotal) {
                    AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
                    msgBox.setMessage("Wrong Answer!");
                    msgBox.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showNextQuestion();
                        }
                    });
                    msgBox.create().show();
                }
                else{
                    done();
                }
            }
            else if(optc.getText().equals(answers.getText())) {
                opta.setTextColor(Color.RED);
                optb.setTextColor(Color.RED);
                optc.setTextColor(Color.GREEN);
                if(questionCounter < questionCountTotal) {
                    AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
                    msgBox.setMessage("Wrong Answer!");
                    msgBox.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showNextQuestion();
                        }
                    });
                    msgBox.create().show();
                }
                else{
                    done();
                }
            }
        }
    }



    private void optb(){
        if(optb.getText().equals(answers.getText())){
            opta.setTextColor(Color.RED);
            optc.setTextColor(Color.RED);
            optb.setTextColor(Color.GREEN);
            score++;
            txtscore.setText("Score :" + score);
            if(questionCounter < questionCountTotal) {
                AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
                msgBox.setMessage("Right Answer!");
                msgBox.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showNextQuestion();
                    }
                });
                msgBox.create().show();
            }
            else{
                done();;
            }
        }
        else{
            if(opta.getText().equals(answers.getText())){

                optc.setTextColor(Color.RED);
                optb.setTextColor(Color.RED);
                opta.setTextColor(Color.GREEN);
                if(questionCounter < questionCountTotal) {
                    AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
                    msgBox.setMessage("Wrong Answer!");
                    msgBox.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showNextQuestion();
                        }
                    });
                    msgBox.create().show();
                }
                else{
                    done();
                }
            }
            else if(optc.getText().equals(answers.getText())){
                opta.setTextColor(Color.RED);
                optb.setTextColor(Color.RED);
                optc.setTextColor(Color.GREEN);
                if(questionCounter < questionCountTotal) {
                    AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
                    msgBox.setMessage("Wrong Answer!");
                    msgBox.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showNextQuestion();
                        }
                    });
                    msgBox.create().show();
                }
                else{
                    done();
                }
            }
        }
    }

    private void optc(){
        if(optc.getText().equals(answers.getText())){
            score++;
            txtscore.setText("Score :" + score);
            opta.setTextColor(Color.RED);
            optb.setTextColor(Color.RED);
            optc.setTextColor(Color.GREEN);
            if(questionCounter < questionCountTotal) {
                AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
                msgBox.setMessage("Right Answer!");
                msgBox.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showNextQuestion();
                    }
                });
                msgBox.create().show();
            }
            else{
                done();
            }
        }
        else{
            if(optb.getText().equals(answers.getText())){
                opta.setTextColor(Color.RED);
                optc.setTextColor(Color.RED);
                optb.setTextColor(Color.GREEN);
                if(questionCounter < questionCountTotal) {
                    AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
                    msgBox.setMessage("Wrong Answer!");
                    msgBox.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showNextQuestion();
                        }
                    });
                    msgBox.create().show();
                }
                else{
                    done();
                }
            }
            else if(opta.getText().equals(answers.getText())){
                optc.setTextColor(Color.RED);
                optb.setTextColor(Color.RED);
                opta.setTextColor(Color.GREEN);
                if(questionCounter < questionCountTotal) {
                    AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
                    msgBox.setMessage("Wrong Answer!");
                    msgBox.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showNextQuestion();
                        }
                    });
                    msgBox.create().show();
                }
                else{
                    done();
                }
            }
        }
    }

    private void showNextQuestion(){
        opta.setTextColor(textColorDefaultRb);
        optb.setTextColor(textColorDefaultRb);
        optc.setTextColor(textColorDefaultRb);

        if(questionCounter < questionCountTotal){
            currentQuestion = questionList.get(questionCounter);

            soal.setText(currentQuestion.getQuestion());
            answers.setText(currentQuestion.getAnswer());
            opta.setText(currentQuestion.getOpta());
            optb.setText(currentQuestion.getOptb());
            optc.setText(currentQuestion.getOptc());

            questionCounter++;
            count.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answer = false;

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
                showNextQuestion();

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

    private void done(){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
        msgBox.setMessage("Score:"+score);
        msgBox.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishQuiz();
            }
        });
        msgBox.create().show();
    }
    private void finishQuiz(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE_WORD, score);
        setResult(RESULT_OK, resultIntent);
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.close();
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(kuisWord.this);
        msgBox.setMessage("Do you want to quit?");
        msgBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishQuiz();
            }
        });
        msgBox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        msgBox.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE_W, score);
        outState.putInt(KEY_QUESTION_COUNT_W, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT_W, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED_W, answer);
        outState.putParcelableArrayList(KEY_QUESTION_LIST_W, questionList);

    }
}
