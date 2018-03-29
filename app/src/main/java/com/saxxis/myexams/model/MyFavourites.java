package com.saxxis.myexams.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saxxis25 on 11/7/2017.
 */

public class MyFavourites implements Parcelable{

    private String QuestionVersionId;
    private String StatisticsInfoId;
    private String UserId;
    private String CreatedDate;
    private String Status;
    private String QuestionId;
    private String QuestionTypeId;
    private String Question;
    private String Solution;
    private String HashCode;
    private String Created;
    private String CreatedBy;
    private String Data;
    private String ShowAsImage;
    private String Score;
    private String wscore;
    private String comp;
    private String CompId;
    private String PublishedDate;

    public MyFavourites(String questionVersionId, String statisticsInfoId, String userId, String createdDate, String status, String questionId, String questionTypeId, String question, String solution, String hashCode, String created, String createdBy, String data, String showAsImage, String score, String wscore, String comp, String compId, String publishedDate) {
        this.QuestionVersionId = questionVersionId;
        this.StatisticsInfoId = statisticsInfoId;
        this.UserId = userId;
        this.CreatedDate = createdDate;
        this.Status = status;
        this.QuestionId = questionId;
        this.QuestionTypeId = questionTypeId;
        this.Question = question;
        this.Solution = solution;
        this.HashCode = hashCode;
        this.Created = created;
        this.CreatedBy = createdBy;
        this.Data = data;
        this.ShowAsImage = showAsImage;
        this.Score = score;
        this.wscore = wscore;
        this.comp = comp;
        this.CompId = compId;
        this.PublishedDate = publishedDate;
    }


    protected MyFavourites(Parcel in) {
        QuestionVersionId = in.readString();
        StatisticsInfoId = in.readString();
        UserId = in.readString();
        CreatedDate = in.readString();
        Status = in.readString();
        QuestionId = in.readString();
        QuestionTypeId = in.readString();
        Question = in.readString();
        Solution = in.readString();
        HashCode = in.readString();
        Created = in.readString();
        CreatedBy = in.readString();
        Data = in.readString();
        ShowAsImage = in.readString();
        Score = in.readString();
        wscore = in.readString();
        comp = in.readString();
        CompId = in.readString();
        PublishedDate = in.readString();
    }

    public static final Creator<MyFavourites> CREATOR = new Creator<MyFavourites>() {
        @Override
        public MyFavourites createFromParcel(Parcel in) {
            return new MyFavourites(in);
        }

        @Override
        public MyFavourites[] newArray(int size) {
            return new MyFavourites[size];
        }
    };

    public String getQuestionVersionId() {
        return QuestionVersionId;
    }

    public void setQuestionVersionId(String questionVersionId) {
        QuestionVersionId = questionVersionId;
    }

    public String getStatisticsInfoId() {
        return StatisticsInfoId;
    }

    public void setStatisticsInfoId(String statisticsInfoId) {
        StatisticsInfoId = statisticsInfoId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getQuestionTypeId() {
        return QuestionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        QuestionTypeId = questionTypeId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getSolution() {
        return Solution;
    }

    public void setSolution(String solution) {
        Solution = solution;
    }

    public String getHashCode() {
        return HashCode;
    }

    public void setHashCode(String hashCode) {
        HashCode = hashCode;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getShowAsImage() {
        return ShowAsImage;
    }

    public void setShowAsImage(String showAsImage) {
        ShowAsImage = showAsImage;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getWscore() {
        return wscore;
    }

    public void setWscore(String wscore) {
        this.wscore = wscore;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getCompId() {
        return CompId;
    }

    public void setCompId(String compId) {
        CompId = compId;
    }

    public String getPublishedDate() {
        return PublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        PublishedDate = publishedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(QuestionVersionId);
        dest.writeString(StatisticsInfoId);
        dest.writeString(UserId);
        dest.writeString(CreatedDate);
        dest.writeString(Status);
        dest.writeString(QuestionId);
        dest.writeString(QuestionTypeId);
        dest.writeString(Question);
        dest.writeString(Solution);
        dest.writeString(HashCode);
        dest.writeString(Created);
        dest.writeString(CreatedBy);
        dest.writeString(Data);
        dest.writeString(ShowAsImage);
        dest.writeString(Score);
        dest.writeString(wscore);
        dest.writeString(comp);
        dest.writeString(CompId);
        dest.writeString(PublishedDate);
    }
}
