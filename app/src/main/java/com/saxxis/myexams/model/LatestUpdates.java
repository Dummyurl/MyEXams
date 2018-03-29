package com.saxxis.myexams.model;

/**
 * Created by android2 on 6/5/2017.
 */

public class LatestUpdates {

    private String titleId;

    private String currentAffByte;

    private String introtext;


    public LatestUpdates(String titleId, String currentAffByte, String introtext){
        this.setCurrentAffByte(currentAffByte);
        this.setTitleId(titleId);
        this.setIntrotext(introtext);
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getCurrentAffByte() {
        return currentAffByte;
    }

    public void setCurrentAffByte(String currentAffByte) {
        this.currentAffByte = currentAffByte;
    }

    public String getIntrotext() {
        return introtext;
    }

    public void setIntrotext(String introtext) {
        this.introtext = introtext;
    }



}
