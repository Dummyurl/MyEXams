package com.saxxis.myexamspace.model;

/**
 * Created by android2 on 6/7/2017.
 */

public class NotifiDataResult {

    private String job_id;
    private String posting_date;
    private String closing_date;
    private String cur_date;
    private String title;
    private String company;
    private String category;
    private String qualification;


    public NotifiDataResult(String job_id,String posting_date,String closing_date,
                            String cur_date,String title,String company,
                            String category,String qualification){

        this.job_id=job_id;
        this.posting_date=posting_date;
        this.closing_date=closing_date;
        this.cur_date=cur_date;
        this.title=title;
        this.company=company;
        this.category=category;
        this.qualification=qualification;
    }


    public String getPosting_date() {
        return posting_date;
    }

    public void setPosting_date(String posting_date) {
        this.posting_date = posting_date;
    }

    public String getClosing_date() {
        return closing_date;
    }

    public void setClosing_date(String closing_date) {
        this.closing_date = closing_date;
    }

    public String getCur_date() {
        return cur_date;
    }

    public void setCur_date(String cur_date) {
        this.cur_date = cur_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
