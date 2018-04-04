package com.saxxis.myexamspace.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saxxis25 on 11/7/2017.
 */

public class PackageData implements Parcelable{

    private String OrderNum;
    private String PackageId;
    private String PackageName;
    private String QuizNum;
    private String PackagePrice;
    private String QuizCategoryId;
    private String PackageQuantity;
    private String PackageType;
    private String quiz_category;
    private String quiz_subject;
    private String quiz_sublevel;
    private String quiz_subchapter;
    private String CategoryName;
    private String SubjectName;
    private String SublevelName;
    private String SubchapterName;
    private String TotalQuizzes;

    public PackageData(String orderNum, String packageId, String packageName, String quizNum, String packagePrice,
                       String quizCategoryId, String packageQuantity, String packageType, String quiz_category, String quiz_subject,
                       String quiz_sublevel, String quiz_subchapter, String categoryName, String subjectName, String sublevelName,
                       String subchapterName, String totalQuizzes) {
        this.OrderNum = orderNum;
        this.PackageId = packageId;
        this.PackageName = packageName;
        this.QuizNum = quizNum;
        this.PackagePrice = packagePrice;
        this.QuizCategoryId = quizCategoryId;
        this.PackageQuantity = packageQuantity;
        this.PackageType = packageType;
        this.quiz_category = quiz_category;
        this.quiz_subject = quiz_subject;
        this.quiz_sublevel = quiz_sublevel;
        this.quiz_subchapter = quiz_subchapter;
        this.CategoryName = categoryName;
        this.SubjectName = subjectName;
        this.SublevelName = sublevelName;
        this.SubchapterName = subchapterName;
        this.TotalQuizzes = totalQuizzes;
    }

    protected PackageData(Parcel in) {
        OrderNum = in.readString();
        PackageId = in.readString();
        PackageName = in.readString();
        QuizNum = in.readString();
        PackagePrice = in.readString();
        QuizCategoryId = in.readString();
        PackageQuantity = in.readString();
        PackageType = in.readString();
        quiz_category = in.readString();
        quiz_subject = in.readString();
        quiz_sublevel = in.readString();
        quiz_subchapter = in.readString();
        CategoryName = in.readString();
        SubjectName = in.readString();
        SublevelName = in.readString();
        SubchapterName = in.readString();
        TotalQuizzes = in.readString();
    }

    public static final Creator<PackageData> CREATOR = new Creator<PackageData>() {
        @Override
        public PackageData createFromParcel(Parcel in) {
            return new PackageData(in);
        }

        @Override
        public PackageData[] newArray(int size) {
            return new PackageData[size];
        }
    };

    public String getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(String orderNum) {
        OrderNum = orderNum;
    }

    public String getPackageId() {
        return PackageId;
    }

    public void setPackageId(String packageId) {
        PackageId = packageId;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getQuizNum() {
        return QuizNum;
    }

    public void setQuizNum(String quizNum) {
        QuizNum = quizNum;
    }

    public String getPackagePrice() {
        return PackagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        PackagePrice = packagePrice;
    }

    public String getQuizCategoryId() {
        return QuizCategoryId;
    }

    public void setQuizCategoryId(String quizCategoryId) {
        QuizCategoryId = quizCategoryId;
    }

    public String getPackageQuantity() {
        return PackageQuantity;
    }

    public void setPackageQuantity(String packageQuantity) {
        PackageQuantity = packageQuantity;
    }

    public String getPackageType() {
        return PackageType;
    }

    public void setPackageType(String packageType) {
        PackageType = packageType;
    }

    public String getQuiz_category() {
        return quiz_category;
    }

    public void setQuiz_category(String quiz_category) {
        this.quiz_category = quiz_category;
    }

    public String getQuiz_subject() {
        return quiz_subject;
    }

    public void setQuiz_subject(String quiz_subject) {
        this.quiz_subject = quiz_subject;
    }

    public String getQuiz_sublevel() {
        return quiz_sublevel;
    }

    public void setQuiz_sublevel(String quiz_sublevel) {
        this.quiz_sublevel = quiz_sublevel;
    }

    public String getQuiz_subchapter() {
        return quiz_subchapter;
    }

    public void setQuiz_subchapter(String quiz_subchapter) {
        this.quiz_subchapter = quiz_subchapter;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getSublevelName() {
        return SublevelName;
    }

    public void setSublevelName(String sublevelName) {
        SublevelName = sublevelName;
    }

    public String getSubchapterName() {
        return SubchapterName;
    }

    public void setSubchapterName(String subchapterName) {
        SubchapterName = subchapterName;
    }

    public String getTotalQuizzes() {
        return TotalQuizzes;
    }

    public void setTotalQuizzes(String totalQuizzes) {
        TotalQuizzes = totalQuizzes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(OrderNum);
        dest.writeString(PackageId);
        dest.writeString(PackageName);
        dest.writeString(QuizNum);
        dest.writeString(PackagePrice);
        dest.writeString(QuizCategoryId);
        dest.writeString(PackageQuantity);
        dest.writeString(PackageType);
        dest.writeString(quiz_category);
        dest.writeString(quiz_subject);
        dest.writeString(quiz_sublevel);
        dest.writeString(quiz_subchapter);
        dest.writeString(CategoryName);
        dest.writeString(SubjectName);
        dest.writeString(SublevelName);
        dest.writeString(SubchapterName);
        dest.writeString(TotalQuizzes);
    }
}
