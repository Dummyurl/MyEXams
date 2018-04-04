package com.saxxis.myexamspace.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android2 on 6/13/2017.
 */

public class SubjectsSubCategories implements Parcelable {

    private String id;
    private String title;
    private String link;
    private String image;
    private String description;

    public SubjectsSubCategories(String id, String title,String link,String image,String description){
        this.id=id;
        this.title=title;
        this.link=link;
        this.image=image;
        this.description=description;
    }

    protected SubjectsSubCategories(Parcel in) {
        id = in.readString();
        title = in.readString();
        link = in.readString();
        image = in.readString();
        description = in.readString();
    }

    public static final Creator<SubjectsSubCategories> CREATOR = new Creator<SubjectsSubCategories>() {
        @Override
        public SubjectsSubCategories createFromParcel(Parcel in) {
            return new SubjectsSubCategories(in);
        }

        @Override
        public SubjectsSubCategories[] newArray(int size) {
            return new SubjectsSubCategories[size];
        }
    };

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(image);
        dest.writeString(description);
    }
}
