package com.example.vfairoh.kuis.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.vfairoh.kuis.data.QuizContract.*;
import com.example.vfairoh.kuis.soal;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kuis.db";
    private static final int DATABASE_VERSION =1;

    private SQLiteDatabase db;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final  String SQL_CREATE_QUESTION_TABLE = " CREATE TABLE " +
                KuisEntry.TABLE_QUEST + " ( " +
                KuisEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KuisEntry.KEY_QUES + " TEXT, " +
                KuisEntry.KEY_OPTA + " TEXT, " +
                KuisEntry.KEY_OPTB + " TEXT, " +
                KuisEntry.KEY_OPTC + " TEXT, " +
                KuisEntry.KEY_ANSWER + " INTEGER " + ")";

        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + KuisEntry.TABLE_QUEST);
        onCreate(db);
    }

    private  void fillQuestionTable(){
        soal q1 = new soal("A is correct", "A", "B","C", 1);
        addSoal(q1);
        soal q2 = new soal("B is correct", "A", "B","C", 2);
        addSoal(q2);
        soal q3 = new soal("C is correct", "A", "B","C", 3);
        addSoal(q3);
    }

    private void addSoal(soal soal){
        ContentValues cv = new ContentValues();
        cv.put(KuisEntry.KEY_QUES, soal.getQuestion());
        cv.put(KuisEntry.KEY_OPTA, soal.getOpta());
        cv.put(KuisEntry.KEY_OPTB, soal.getOptb());
        cv.put(KuisEntry.KEY_OPTC, soal.getOptc());
        cv.put(KuisEntry.KEY_ANSWER, soal.getAnswer());
        db.insert(KuisEntry.TABLE_QUEST, null, cv);
    }

    public List<soal> getAllQuestion(){
        List<soal> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + KuisEntry.TABLE_QUEST, null);
        if(c.moveToFirst()){
            do{
                soal soal = new soal();
                soal.setQuestion(c.getString(c.getColumnIndex(KuisEntry.KEY_QUES)));
                soal.setOpta(c.getString(c.getColumnIndex(KuisEntry.KEY_OPTA)));
                soal.setOptb(c.getString(c.getColumnIndex(KuisEntry.KEY_OPTB)));
                soal.setOptc(c.getString(c.getColumnIndex(KuisEntry.KEY_OPTC)));
                soal.setAnswer(c.getInt(c.getColumnIndex(KuisEntry.KEY_ANSWER)));
                questionList.add(soal);
            }while(c.moveToNext());
        }
     c.close();
        return questionList;
    }
}
