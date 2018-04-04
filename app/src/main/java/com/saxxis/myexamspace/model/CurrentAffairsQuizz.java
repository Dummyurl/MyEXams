package com.saxxis.myexamspace.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saxxis25 on 10/12/2017.
 */

public class CurrentAffairsQuizz implements Parcelable{

    private String Question;
    private String Solution;
    private String QuestionVersionId;
    private String Data;
    private String comp;
    private String CompId;
    private String Score;
    private String PublishedDate;
    private String status;
    private String QlanguageType;
    private String QuestionType;
    private String ClassName;
    private String Created;
    private String QtypeName;
    private String QlevelName;
    private String examtypename;
    private String CategoryName;
    private String SubjectName;
    private String SublevelName;
    private String SubchapterName;
    private String fevorite;


    public CurrentAffairsQuizz(String Question, String Solution, String QuestionVersionId, String Data, String comp, String CompId,
                               String Score, String PublishedDate, String status, String QlanguageType, String QuestionType,
                               String ClassName, String Created, String QtypeName, String QlevelName, String examtypename,
                               String CategoryName, String SubjectName, String SublevelName, String SubchapterName, String fevorite){
        this.Question=Question;
        this.Solution=Solution;
        this.QuestionVersionId=QuestionVersionId;
        this.Data=Data;
        this.comp=comp;
        this.CompId=CompId;
        this.Score=Score;
        this.PublishedDate=PublishedDate;
        this.status=status;
        this.QlanguageType=QlanguageType;
        this.QuestionType=QuestionType;
        this.ClassName=ClassName;
        this.Created=Created;
        this.QtypeName=QtypeName;
        this.QlevelName=QlevelName;
        this.examtypename=examtypename;
        this.CategoryName=CategoryName;
        this.SubjectName=SubjectName;
        this.SublevelName=SublevelName;
        this.SubchapterName=SubchapterName;
        this.fevorite = fevorite;

    }

    protected CurrentAffairsQuizz(Parcel in) {
        Question = in.readString();
        Solution = in.readString();
        QuestionVersionId = in.readString();
        Data = in.readString();
        comp = in.readString();
        CompId = in.readString();
        Score = in.readString();
        PublishedDate = in.readString();
        status = in.readString();
        QlanguageType = in.readString();
        QuestionType = in.readString();
        ClassName = in.readString();
        Created = in.readString();
        QtypeName = in.readString();
        QlevelName = in.readString();
        examtypename = in.readString();
        CategoryName = in.readString();
        SubjectName = in.readString();
        SublevelName = in.readString();
        SubchapterName = in.readString();
        fevorite = in.readString();
    }

    public static final Creator<CurrentAffairsQuizz> CREATOR = new Creator<CurrentAffairsQuizz>() {
        @Override
        public CurrentAffairsQuizz createFromParcel(Parcel in) {
            return new CurrentAffairsQuizz(in);
        }

        @Override
        public CurrentAffairsQuizz[] newArray(int size) {
            return new CurrentAffairsQuizz[size];
        }
    };

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

    public String getQuestionVersionId() {
        return QuestionVersionId;
    }

    public void setQuestionVersionId(String questionVersionId) {
        QuestionVersionId = questionVersionId;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
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

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getPublishedDate() {
        return PublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        PublishedDate = publishedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQlanguageType() {
        return QlanguageType;
    }

    public void setQlanguageType(String qlanguageType) {
        QlanguageType = qlanguageType;
    }

    public String getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(String questionType) {
        QuestionType = questionType;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getQtypeName() {
        return QtypeName;
    }

    public void setQtypeName(String qtypeName) {
        QtypeName = qtypeName;
    }

    public String getQlevelName() {
        return QlevelName;
    }

    public void setQlevelName(String qlevelName) {
        QlevelName = qlevelName;
    }

    public String getExamtypename() {
        return examtypename;
    }

    public void setExamtypename(String examtypename) {
        this.examtypename = examtypename;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getSublevelName() {
        return SublevelName;
    }

    public void setSublevelName(String sublevelName) {
        SublevelName = sublevelName;
    }

    public String getSubchapterName() {
        return SubchapterName;
    }

    public void setSubchapterName(String subchapterName) {
        SubchapterName = subchapterName;
    }

    public String getFevorite() {return fevorite;}

    public void setFevorite(String fevorite) {this.fevorite = fevorite;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Question);
        dest.writeString(Solution);
        dest.writeString(QuestionVersionId);
        dest.writeString(Data);
        dest.writeString(comp);
        dest.writeString(CompId);
        dest.writeString(Score);
        dest.writeString(PublishedDate);
        dest.writeString(status);
        dest.writeString(QlanguageType);
        dest.writeString(QuestionType);
        dest.writeString(ClassName);
        dest.writeString(Created);
        dest.writeString(QtypeName);
        dest.writeString(QlevelName);
        dest.writeString(examtypename);
        dest.writeString(CategoryName);
        dest.writeString(SubjectName);
        dest.writeString(SublevelName);
        dest.writeString(SubchapterName);
        dest.writeString(fevorite);
    }
}
