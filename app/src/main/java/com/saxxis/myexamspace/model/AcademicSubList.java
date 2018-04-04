package com.saxxis.myexamspace.model;

/**
 * Created by android2 on 6/13/2017.
 */

public class AcademicSubList {

    private String SubjectId;
    private String SubjectName;
    private String Uploadlogo;
    private String Description;

    public AcademicSubList(String SubjectId,String SubjectName,String Uploadlogo,
        String Description){

        this.SubjectId=SubjectId;
        this.SubjectName=SubjectName;
        this.Uploadlogo=Uploadlogo;
        this.Description=Description;

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

    public String getUploadlogo() {
        return Uploadlogo;
    }

    public void setUploadlogo(String uploadlogo) {
        Uploadlogo = uploadlogo;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
