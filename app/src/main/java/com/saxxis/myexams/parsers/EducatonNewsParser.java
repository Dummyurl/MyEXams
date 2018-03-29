package com.saxxis.myexams.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saxxis.myexams.model.EducationNews;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 9/19/2017.
 */

public class EducatonNewsParser {

    public ArrayList<EducationNews> data;

    public EducatonNewsParser(){
        data = new ArrayList<EducationNews>();
    }

    public static EducatonNewsParser getInstance(String response){
        Gson gson= new GsonBuilder().create();
        EducatonNewsParser parsedata=gson.fromJson(response,EducatonNewsParser.class);
        return  parsedata;
    }

    public ArrayList<EducationNews> getData(){
        return data;
    }

}
