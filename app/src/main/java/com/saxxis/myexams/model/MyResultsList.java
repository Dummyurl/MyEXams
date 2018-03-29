package com.saxxis.myexams.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saxxis25 on 6/27/2017.
 */

public class MyResultsList implements Parcelable{

    private String StatisticsInfoId;
    private String QuizId;
    private String UserId;
    private String Status;
    private String TicketId;
    private String StartDate;
    private String EndDate;
    private String PassedScore;
    private String UserScore;
    private String MaxScore;
    private String Passed;
    private String CreatedDate;
    private String QuestionCount;
    private String TotalTime;
    private String ResultEmailed;
    private String Quizname;


    public MyResultsList(
            String StatisticsInfoId, String QuizId, String UserId,String Status,String TicketId,
            String StartDate,String EndDate,String PassedScore,String UserScore,String MaxScore,
            String Passed,String CreatedDate,String QuestionCount,String TotalTime,String ResultEmailed,
            String Quizname){

        this.StatisticsInfoId=StatisticsInfoId;
        this.QuizId=QuizId;
        this.UserId=UserId;
        this.Status=Status;
        this.TicketId=TicketId;
        this.StartDate=StartDate;
        this.EndDate=EndDate;
        this.PassedScore=PassedScore;
        this.UserScore=UserScore;
        this.MaxScore=MaxScore;
        this.Passed=Passed;
        this.CreatedDate=CreatedDate;
        this.QuestionCount=QuestionCount;
        this.TotalTime=TotalTime;
        this.ResultEmailed=ResultEmailed;
        this.Quizname=Quizname;


    }


    protected MyResultsList(Parcel in) {
        StatisticsInfoId = in.readString();
        QuizId = in.readString();
        UserId = in.readString();
        Status = in.readString();
        TicketId = in.readString();
        StartDate = in.readString();
        EndDate = in.readString();
        PassedScore = in.readString();
        UserScore = in.readString();
        MaxScore = in.readString();
        Passed = in.readString();
        CreatedDate = in.readString();
        QuestionCount = in.readString();
        TotalTime = in.readString();
        ResultEmailed = in.readString();
        Quizname = in.readString();
    }

    public static final Creator<MyResultsList> CREATOR = new Creator<MyResultsList>() {
        @Override
        public MyResultsList createFromParcel(Parcel in) {
            return new MyResultsList(in);
        }

        @Override
        public MyResultsList[] newArray(int size) {
            return new MyResultsList[size];
        }
    };

    public String getStatisticsInfoId() {
        return StatisticsInfoId;
    }

    public void setStatisticsInfoId(String statisticsInfoId) {
        StatisticsInfoId = statisticsInfoId;
    }

    public String getQuizId() {
        return QuizId;
    }

    public void setQuizId(String quizId) {
        QuizId = quizId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTicketId() {
        return TicketId;
    }

    public void setTicketId(String ticketId) {
        TicketId = ticketId;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getPassedScore() {
        return PassedScore;
    }

    public void setPassedScore(String passedScore) {
        PassedScore = passedScore;
    }

    public String getUserScore() {
        return UserScore;
    }

    public void setUserScore(String userScore) {
        UserScore = userScore;
    }

    public String getMaxScore() {
        return MaxScore;
    }

    public void setMaxScore(String maxScore) {
        MaxScore = maxScore;
    }

    public String getPassed() {
        return Passed;
    }

    public void setPassed(String passed) {
        Passed = passed;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getQuestionCount() {
        return QuestionCount;
    }

    public void setQuestionCount(String questionCount) {
        QuestionCount = questionCount;
    }

    public String getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(String totalTime) {
        TotalTime = totalTime;
    }

    public String getResultEmailed() {
        return ResultEmailed;
    }

    public void setResultEmailed(String resultEmailed) {
        ResultEmailed = resultEmailed;
    }

    public String getQuizname() {
        return Quizname;
    }

    public void setQuizname(String quizname) {
        Quizname = quizname;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(StatisticsInfoId);
        dest.writeString(QuizId);
        dest.writeString(UserId);
        dest.writeString(Status);
        dest.writeString(TicketId);
        dest.writeString(StartDate);
        dest.writeString(EndDate);
        dest.writeString(PassedScore);
        dest.writeString(UserScore);
        dest.writeString(MaxScore);
        dest.writeString(Passed);
        dest.writeString(CreatedDate);
        dest.writeString(QuestionCount);
        dest.writeString(TotalTime);
        dest.writeString(ResultEmailed);
        dest.writeString(Quizname);
    }
}
