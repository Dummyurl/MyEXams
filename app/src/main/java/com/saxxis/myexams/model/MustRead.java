package com.saxxis.myexams.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chaitanyat on 29-01-2018.
 */

public class MustRead implements Parcelable{
    public String title;
    public String introtext;
    public String created;

    public MustRead(String title, String introtext, String created) {
        this.title = title;
        this.introtext = introtext;
        this.created = created;
    }

    protected MustRead(Parcel in) {
        title = in.readString();
        introtext = in.readString();
        created = in.readString();
    }

    public static final Creator<MustRead> CREATOR = new Creator<MustRead>() {
        @Override
        public MustRead createFromParcel(Parcel in) {
            return new MustRead(in);
        }

        @Override
        public MustRead[] newArray(int size) {
            return new MustRead[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(introtext);
        dest.writeString(created);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntrotext() {
        return introtext;
    }

    public void setIntrotext(String introtext) {
        this.introtext = introtext;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
