package com.saxxis.myexamspace.model;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 10/13/2017.
 */

public class QuizzFilterItems {
    private String examtypename;
    private String examtypeid;

    public static ArrayList<String> selectedId=new ArrayList<>();

    public QuizzFilterItems(String examtypeid,String examtypename) {
        this.examtypeid = examtypeid;
        this.examtypename = examtypename;
    }

    public String getExamtypename() {
        return examtypename;
    }

    public void setExamtypename(String examtypename) {
        this.examtypename = examtypename;
    }

    public String getExamtypeid() {
        return examtypeid;
    }

    public void setExamtypeid(String examtypeid) {
        this.examtypeid = examtypeid;
    }

    public ArrayList<String> getSelectedId() {
        return selectedId;
    }



}
