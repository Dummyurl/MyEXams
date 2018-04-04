package com.saxxis.myexamspace.model;

/**
 * Created by android2 on 6/6/2017.
 */

public class ExamIntAcadem {

    private String iconint;
    private String title;
    private String id;
    private String link;

    public ExamIntAcadem(String iconint,String title,String id,String link){
        this.setIconint(iconint);
        this.setTitle(title);
        this.setId(id);
        this.setLink(link);
    }

    public String getIconint() {
        return iconint;
    }

    public void setIconint(String iconint) {
        this.iconint = iconint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
