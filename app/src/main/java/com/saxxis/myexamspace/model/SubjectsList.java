package com.saxxis.myexamspace.model;

/**
 * Created by android2 on 6/12/2017.
 */

public class SubjectsList {

    private String id;
    private String title;
    private String subjectImage;

    public SubjectsList(String id,String title,String subjectImage){
        this.id=id;
        this.title=title;
        this.subjectImage=subjectImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubjectImage() {
        return subjectImage;
    }

    public void setSubjectImage(String subjectImage) {
        this.subjectImage = subjectImage;
    }
}
