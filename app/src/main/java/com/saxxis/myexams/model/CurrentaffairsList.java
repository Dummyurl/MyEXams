package com.saxxis.myexams.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android2 on 6/1/2017.
 */

public class CurrentaffairsList implements Parcelable{

    private String title;
    private String description;
    private String topicId;
    private String created;

    public CurrentaffairsList(String title,String description,String topicId,String created){
        this.title=title;
        this.description=description;
        this.topicId=topicId;
        this.created=created;
    }

    public CurrentaffairsList(){}
    protected CurrentaffairsList(Parcel in) {
        title = in.readString();
        description = in.readString();
        topicId = in.readString();
        created = in.readString();
    }

    public static final Creator<CurrentaffairsList> CREATOR = new Creator<CurrentaffairsList>() {
        @Override
        public CurrentaffairsList createFromParcel(Parcel in) {
            return new CurrentaffairsList(in);
        }

        @Override
        public CurrentaffairsList[] newArray(int size) {
            return new CurrentaffairsList[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }


    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(topicId);
        dest.writeString(created);
    }
}
