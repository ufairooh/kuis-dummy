package com.example.vfairoh.kuis;

import android.os.Parcel;
import android.os.Parcelable;

public class soal implements Parcelable {
    private String question;
    private String opta;
    private String optb;
    private String optc;
    private String answer;

    public  soal(){}

    public soal(String question, String opta, String optb, String optc, String answer) {
        this.question = question;
        this.opta = opta;
        this.optb = optb;
        this.optc = optc;
        this.answer = answer;
    }

    protected soal(Parcel in){
        question = in.readString();
        opta = in.readString();
        optb = in.readString();
        optc = in.readString();
        answer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(opta);
        dest.writeString(optb);
        dest.writeString(optc);
        dest.writeString(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<soal> CREATOR = new Creator<soal>() {
        @Override
        public soal createFromParcel(Parcel source) {
            return new soal(source);
        }

        @Override
        public soal[] newArray(int size) {
            return new soal[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOpta() {
        return opta;
    }

    public void setOpta(String opta) {
        this.opta = opta;
    }

    public String getOptb() {
        return optb;
    }

    public void setOptb(String optb) {
        this.optb = optb;
    }

    public String getOptc() {
        return optc;
    }

    public void setOptc(String optc) {
        this.optc = optc;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
