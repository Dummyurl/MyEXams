package com.saxxis.myexams.model;

/**
 * Created by android2 on 6/2/2017.
 */

public class SlidersHome {

    private String id;
    private String title;
    private String image;
    private String params;

    public SlidersHome(String id,String title,String image,String params){
        this.id=id;
        this.title=title;
        this.image=image;
        this.params=params;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
