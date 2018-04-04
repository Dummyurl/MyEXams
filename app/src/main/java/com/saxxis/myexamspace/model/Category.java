package com.saxxis.myexamspace.model;

/**
 * Created by saxxis25 on 9/19/2017.
 */

public class Category {

    private String CategoryId;
    private String CategoryName;

    public Category(String CategoryId, String CategoryName){
        this.CategoryId=CategoryId;
        this.CategoryName = CategoryName;

    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }


}
