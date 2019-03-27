package com.example.vfairoh.kuis.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.vfairoh.kuis.data.QuizContract.*;
import com.example.vfairoh.kuis.soal;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "soal.db";
    private static final int DATABASE_VERSION =1;

    private SQLiteDatabase db;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public ArrayList<soal> getAlpQuestion(){
        ArrayList<soal> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM quiz WHERE idCategory = 1 ORDER BY RANDOM()LIMIT 5",null);
        if(c.moveToFirst()){
            do{
                soal soal = new soal();
                soal.setQuestion(c.getString(c.getColumnIndex("question")));
                soal.setOpta(c.getString(c.getColumnIndex("opt1")));
                soal.setOptb(c.getString(c.getColumnIndex("opt2")));
                soal.setOptc(c.getString(c.getColumnIndex("opt3")));
                soal.setAnswer(c.getString(c.getColumnIndex("answer")));
                questionList.add(soal);
            }while(c.moveToNext());
        }
     c.close();
        return questionList;
    }

    public ArrayList<soal> getWordQuestion(){
        ArrayList<soal> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM quiz WHERE idCategory = 2 ORDER BY RANDOM()LIMIT 5",null);
        if(c.moveToFirst()){
            do{
                soal soal = new soal();
                soal.setQuestion(c.getString(c.getColumnIndex("question")));
                soal.setOpta(c.getString(c.getColumnIndex("opt1")));
                soal.setOptb(c.getString(c.getColumnIndex("opt2")));
                soal.setOptc(c.getString(c.getColumnIndex("opt3")));
                soal.setAnswer(c.getString(c.getColumnIndex("answer")));
                questionList.add(soal);
            }while(c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<soal> getSenQuestion(){
        ArrayList<soal> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM quiz WHERE idCategory = 3 ORDER BY RANDOM()LIMIT 5",null);
        if(c.moveToFirst()){
            do{
                soal soal = new soal();
                soal.setQuestion(c.getString(c.getColumnIndex("question")));
                soal.setOpta(c.getString(c.getColumnIndex("opt1")));
                soal.setOptb(c.getString(c.getColumnIndex("opt2")));
                soal.setOptc(c.getString(c.getColumnIndex("opt3")));
                soal.setAnswer(c.getString(c.getColumnIndex("answer")));
                questionList.add(soal);
            }while(c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
