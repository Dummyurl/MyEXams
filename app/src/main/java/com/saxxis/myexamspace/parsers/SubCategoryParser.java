package com.saxxis.myexamspace.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saxxis.myexamspace.model.SubCatItems;

import java.util.ArrayList;

/**
 * Created by android2 on 6/1/2017.
 */

public class SubCategoryParser {
    ArrayList<SubCatItems> data;

    public SubCategoryParser(){
        data = new ArrayList<SubCatItems>();
    }

    public static SubCategoryParser parseBusListingJSON(String response) {
        Gson gson = new GsonBuilder().create();
        SubCategoryParser dataresponse = gson.fromJson(response, SubCategoryParser.class);
        return dataresponse;
    }

    public ArrayList<SubCatItems> getListingdata() {
        return data;
    }

}
