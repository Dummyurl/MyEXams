package com.saxxis.myexams.model;

/**
 * Created by android2 on 6/14/2017.
 */

public class QuizzListList {

    private String SubjectId;
    private String SubjectName;
    private String QuizId;
    private String QuizName;
    private String QuestionCount;
    private String ExamLanguage;
    private String TestExamType;
    private String TotalExamMarks;
    private String TotalExamTime;
    private String version;

    public QuizzListList(String SubjectId,String SubjectName,String QuizId,
        String QuizName,String QuestionCount,String ExamLanguage,
        String TestExamType,String TotalExamMarks,String TotalExamTime,String version){

        this.SubjectId=SubjectId;
        this.SubjectName=SubjectName;
        this.QuizId=QuizId;
        this.QuizName=QuizName;
        this.QuestionCount=QuestionCount;
        this.ExamLanguage=ExamLanguage;
        this.TestExamType=TestExamType;
        this.TotalExamMarks=TotalExamMarks;
        this.TotalExamTime=TotalExamTime;
        this.version=version;

    }

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getQuizId() {
        return QuizId;
    }

    public void setQuizId(String quizId) {
        QuizId = quizId;
    }

    public String getQuizName() {
        return QuizName;
    }

    public void setQuizName(String quizName) {
        QuizName = quizName;
    }

    public String getQuestionCount() {
        return QuestionCount;
    }

    public void setQuestionCount(String questionCount) {
        QuestionCount = questionCount;
    }

    public String getExamLanguage() {
        return ExamLanguage;
    }

    public void setExamLanguage(String examLanguage) {
        ExamLanguage = examLanguage;
    }

    public String getTestExamType() {
        return TestExamType;
    }

    public void setTestExamType(String testExamType) {
        TestExamType = testExamType;
    }

    public String getTotalExamMarks() {
        return TotalExamMarks;
    }

    public void setTotalExamMarks(String totalExamMarks) {
        TotalExamMarks = totalExamMarks;
    }

    public String getTotalExamTime() {
        return TotalExamTime;
    }

    public void setTotalExamTime(String totalExamTime) {
        TotalExamTime = totalExamTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
