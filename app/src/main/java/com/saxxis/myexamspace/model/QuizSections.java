package com.saxxis.myexamspace.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saxxis25 on 11/18/2017.
 */

public class QuizSections implements Parcelable{
            private String id;
            private String quizid;
            private String time;
            private String marks;
            private String qcount;
            private String qstart;
            private String title;
            private String qtime;
            private String cscore;
            private String wscore;
            private String cutoffmarks;

    public QuizSections(String id, String quizid, String time, String marks, String qcount, String qstart, String title, String qtime, String cscore, String wscore, String cutoffmarks) {
        this.id = id;
        this.quizid = quizid;
        this.time = time;
        this.marks = marks;
        this.qcount = qcount;
        this.qstart = qstart;
        this.title = title;
        this.qtime = qtime;
        this.cscore = cscore;
        this.wscore = wscore;
        this.cutoffmarks = cutoffmarks;
    }

    protected QuizSections(Parcel in) {
        id = in.readString();
        quizid = in.readString();
        time = in.readString();
        marks = in.readString();
        qcount = in.readString();
        qstart = in.readString();
        title = in.readString();
        qtime = in.readString();
        cscore = in.readString();
        wscore = in.readString();
        cutoffmarks = in.readString();
    }

    public static final Creator<QuizSections> CREATOR = new Creator<QuizSections>() {
        @Override
        public QuizSections createFromParcel(Parcel in) {
            return new QuizSections(in);
        }

        @Override
        public QuizSections[] newArray(int size) {
            return new QuizSections[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getQcount() {
        return qcount;
    }

    public void setQcount(String qcount) {
        this.qcount = qcount;
    }

    public String getQstart() {
        return qstart;
    }

    public void setQstart(String qstart) {
        this.qstart = qstart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQtime() {
        return qtime;
    }

    public void setQtime(String qtime) {
        this.qtime = qtime;
    }

    public String getCscore() {
        return cscore;
    }

    public void setCscore(String cscore) {
        this.cscore = cscore;
    }

    public String getWscore() {
        return wscore;
    }

    public void setWscore(String wscore) {
        this.wscore = wscore;
    }

    public String getCutoffmarks() {
        return cutoffmarks;
    }

    public void setCutoffmarks(String cutoffmarks) {
        this.cutoffmarks = cutoffmarks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(quizid);
        dest.writeString(time);
        dest.writeString(marks);
        dest.writeString(qcount);
        dest.writeString(qstart);
        dest.writeString(title);
        dest.writeString(qtime);
        dest.writeString(cscore);
        dest.writeString(wscore);
        dest.writeString(cutoffmarks);
    }
}
