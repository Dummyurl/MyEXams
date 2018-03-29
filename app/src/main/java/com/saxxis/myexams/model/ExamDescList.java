package com.saxxis.myexams.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android2 on 6/5/2017.
 */

public class ExamDescList implements Parcelable {

    private String Details;
    private String syllabus;
    private String Analysis;
    private String strategy;
    private String MaterialDesc;
    private String quizdetails;


    public ExamDescList(String Details,String syllabus,String Analysis,
        String strategy,String MaterialDesc,String quizdetails){

        this.Details=Details;
        this.syllabus=syllabus;
        this.Analysis=Analysis;
        this.strategy=strategy;
        this.MaterialDesc=MaterialDesc;
        this.quizdetails=quizdetails;

    }


    protected ExamDescList(Parcel in) {
        Details = in.readString();
        syllabus = in.readString();
        Analysis = in.readString();
        strategy = in.readString();
        MaterialDesc = in.readString();
        quizdetails = in.readString();
    }

    public static final Creator<ExamDescList> CREATOR = new Creator<ExamDescList>() {
        @Override
        public ExamDescList createFromParcel(Parcel in) {
            return new ExamDescList(in);
        }

        @Override
        public ExamDescList[] newArray(int size) {
            return new ExamDescList[size];
        }
    };

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getAnalysis() {
        return Analysis;
    }

    public void setAnalysis(String analysis) {
        Analysis = analysis;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getMaterialDesc() {
        return MaterialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        MaterialDesc = materialDesc;
    }


    public String getQuizdetails() {
        return quizdetails;
    }

    public void setQuizdetails(String quizdetails) {
        this.quizdetails = quizdetails;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Details);
        dest.writeString(syllabus);
        dest.writeString(Analysis);
        dest.writeString(strategy);
        dest.writeString(MaterialDesc);
        dest.writeString(quizdetails);
    }
}
