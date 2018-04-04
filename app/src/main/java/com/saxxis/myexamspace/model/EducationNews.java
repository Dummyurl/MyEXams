package com.saxxis.myexamspace.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saxxis25 on 9/19/2017.
 */

public class EducationNews implements Parcelable {


    public String id;
    public String title;
    public String introtext;
    public String publish_up;

    public EducationNews(String id, String title, String introtext, String publish_up) {
        this.id = id;
        this.title = title;
        this.introtext = introtext;
        this.publish_up = publish_up;
    }

    protected EducationNews(Parcel in) {
        id = in.readString();
        title = in.readString();
        introtext = in.readString();
        publish_up = in.readString();
    }

    public static final Creator<EducationNews> CREATOR = new Creator<EducationNews>() {
        @Override
        public EducationNews createFromParcel(Parcel in) {
            return new EducationNews(in);
        }

        @Override
        public EducationNews[] newArray(int size) {
            return new EducationNews[size];
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

    public String getIntrotext() {
        return introtext;
    }

    public void setIntrotext(String introtext) {
        this.introtext = introtext;
    }

    public String getPublish_up() {
        return publish_up;
    }

    public void setPublish_up(String publish_up) {
        this.publish_up = publish_up;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(introtext);
        dest.writeString(publish_up);
    }


    //implements Parcelable {
//
//
//    private String job_id;
//    private String posting_date;
//    private String closing_date;
//    private String cur_date;
//    private String title;
//
//
//    private String job_description;
//    private String company;
//    private String category;
//    private String qualification;
//
//    public EducationNews(String job_id,String posting_date,String closing_date,String cur_date,
//                         String title,String job_description,String company,String category,String qualification){
//        this.job_id=job_id;
//        this.posting_date=posting_date;
//        this.closing_date=closing_date;
//        this.cur_date=cur_date;
//        this.title=title;
//        this.job_description=job_description;
//        this.company=company;
//        this.category=category;
//        this.qualification=qualification;
//
//    }
//
//    protected EducationNews(Parcel in) {
//        job_id = in.readString();
//        posting_date = in.readString();
//        closing_date = in.readString();
//        cur_date = in.readString();
//        title = in.readString();
//        job_description = in.readString();
//        company = in.readString();
//        category = in.readString();
//        qualification = in.readString();
//    }
//
//    public static final Creator<EducationNews> CREATOR = new Creator<EducationNews>() {
//        @Override
//        public EducationNews createFromParcel(Parcel in) {
//            return new EducationNews(in);
//        }
//
//        @Override
//        public EducationNews[] newArray(int size) {
//            return new EducationNews[size];
//        }
//    };
//
//    public String getJob_description() {
//        return job_description;
//    }
//
//    public void setJob_description(String job_description) {
//        this.job_description = job_description;
//    }
//
//    public String getJob_id() {
//        return job_id;
//    }
//
//    public void setJob_id(String job_id) {
//        this.job_id = job_id;
//    }
//
//    public String getPosting_date() {
//        return posting_date;
//    }
//
//    public void setPosting_date(String posting_date) {
//        this.posting_date = posting_date;
//    }
//
//    public String getClosing_date() {
//        return closing_date;
//    }
//
//    public void setClosing_date(String closing_date) {
//        this.closing_date = closing_date;
//    }
//
//    public String getCur_date() {
//        return cur_date;
//    }
//
//    public void setCur_date(String cur_date) {
//        this.cur_date = cur_date;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getCompany() {
//        return company;
//    }
//
//    public void setCompany(String company) {
//        this.company = company;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getQualification() {
//        return qualification;
//    }
//
//    public void setQualification(String qualification) {
//        this.qualification = qualification;
//    }
//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(job_id);
//        dest.writeString(posting_date);
//        dest.writeString(closing_date);
//        dest.writeString(cur_date);
//        dest.writeString(title);
//        dest.writeString(job_description);
//        dest.writeString(company);
//        dest.writeString(category);
//        dest.writeString(qualification);
//    }
}
