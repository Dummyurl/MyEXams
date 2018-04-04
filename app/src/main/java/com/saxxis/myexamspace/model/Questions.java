package com.saxxis.myexamspace.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saxxis25 on 9/12/2017.
 */

public class Questions implements Parcelable{


    private String QuestionId;
    private String Question;
    private String QuestionIndex;
    private  String Solution;
    private String QuestionVersionId;
    private String fevorite;
    private String Description;
    private String[] answers;
    private String Userdata;




    public Questions(String QuestionId, String question, String questionIndex, String solution, String questionVersionId, String fevorite , String description, String[] answers, String userdata) {
        this.QuestionId = QuestionId;
        this.Question = question;
        this.QuestionIndex = questionIndex;
        this.Solution = solution;
        this.QuestionVersionId = questionVersionId;
        this.fevorite = fevorite;
        this.answers = answers;
        this.Userdata = userdata;
        this.Description = description;
    }

    public Questions(String QuestionId, String Question, String questionIndex, String QuestionVersionId, String[] answers, String description){

        this.QuestionId=QuestionId;
        this.Question=Question;
        this.QuestionIndex=questionIndex;
        this.QuestionVersionId=QuestionVersionId;
        this.answers=answers;
        this.Description = description;
    }

    protected Questions(Parcel in) {
        QuestionId = in.readString();
        Question = in.readString();
        QuestionIndex = in.readString();
        Solution = in.readString();
        QuestionVersionId = in.readString();
        fevorite = in.readString();
        answers = in.createStringArray();
        Userdata = in.readString();
        Description = in.readString();
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    public String getQuestionVersionId() {
        return QuestionVersionId;
    }

    public void setQuestionVersionId(String questionVersionId) {
        QuestionVersionId = questionVersionId;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String QuestionId) {
        QuestionId = QuestionId;
    }

    public String getQuestionIndex() {
        return QuestionIndex;
    }

    public void setQuestionIndex(String questionIndex) {
        QuestionIndex = questionIndex;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getSolution() {
        return Solution;
    }

    public void setSolution(String solution) {
        Solution = solution;
    }

    public String getUserdata() {
        return Userdata;
    }

    public void setUserdata(String userdata) {
        Userdata = userdata;
    }

    public String getFevorite() {
        return fevorite;
    }

    public void setFevorite(String fevorite) {
        this.fevorite = fevorite;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(QuestionId);
        dest.writeString(Question);
        dest.writeString(QuestionIndex);
        dest.writeString(Solution);
        dest.writeString(QuestionVersionId);
        dest.writeString(fevorite);
        dest.writeString(Description);
        dest.writeStringArray(answers);
        dest.writeString(Userdata);
    }


    private String answerTick="0";
    // 0 : not visited
    // 1 : visited
    // 2 : answered
    // 3 : wronganswered
    // 4 : correctanswered
    // 5 : mark for review

    public String getAnswerTick() {
        return answerTick;
    }

    public void setAnswerTick(String answerTick) {
        this.answerTick = answerTick;
    }
}
