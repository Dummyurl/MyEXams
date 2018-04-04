package com.saxxis.myexamspace.model;

/**
 * Created by android2 on 6/5/2017.
 */

public class LatestNotifications {

    private String id;
    private String title;
    private String jobid;
    private String company;

    public LatestNotifications(String id,String title,String company,String jobid){
        this.setId(id);
        this.setJobid(jobid);
        this.setTitle(title);
        this.setCompany(company);
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

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
