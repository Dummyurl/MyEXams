package com.saxxis.myexamspace.model;

/**
 * Created by saxxis25 on 7/31/2017.
 */

public class TimeLineModel {

    private String title;
    private String timelinedate;
    private String links;

    public TimeLineModel(String title,String timelinedate,String links){
        this.title=title;
        this.timelinedate=timelinedate;
        this.links=links;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimelinedate() {
        return timelinedate;
    }

    public void setTimelinedate(String timelinedate) {
        this.timelinedate = timelinedate;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }
}
