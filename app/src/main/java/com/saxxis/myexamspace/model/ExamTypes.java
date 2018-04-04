package com.saxxis.myexamspace.model;

/**
 * Created by android2 on 5/23/2017.
 */

public class ExamTypes {

    private String image_src;
    private String CategoryName;
    private String CategoryId;

    public ExamTypes(String image_src,String CategoryName,String CategoryId){
        this.image_src=image_src;
        this.CategoryName=CategoryName;
        this.CategoryId=CategoryId;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
