package com.saxxis.myexams.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android2 on 6/1/2017.
 */

public class SubCatItems implements Parcelable {

    private String SubjectId;
    private String SubjectName;
    private String Description;

    public SubCatItems(String SubjectId,String SubjectName,String Description){
        this.SubjectId=SubjectId;
        this.SubjectName=SubjectName;
        this.Description=Description;
    }

    protected SubCatItems(Parcel in) {
        SubjectId = in.readString();
        SubjectName = in.readString();
        Description = in.readString();
    }

    public static final Creator<SubCatItems> CREATOR = new Creator<SubCatItems>() {
        @Override
        public SubCatItems createFromParcel(Parcel in) {
            return new SubCatItems(in);
        }

        @Override
        public SubCatItems[] newArray(int size) {
            return new SubCatItems[size];
        }
    };

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SubjectId);
        dest.writeString(SubjectName);
        dest.writeString(Description);
    }
}
