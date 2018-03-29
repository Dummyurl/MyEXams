package com.saxxis.myexams.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saxxis.myexams.model.ExamDescList;

import java.util.ArrayList;

/**
 * Created by android2 on 6/5/2017.
 */

public class ExamDescParser {

    public ArrayList<ExamDescList> data;

    public ExamDescParser(){
        data=new ArrayList<ExamDescList>();
    }

    public static  ExamDescParser getInstance(String response){
        Gson gson= new GsonBuilder().create();
        ExamDescParser parsedata=gson.fromJson(response,ExamDescParser.class);
        return  parsedata;
    }

    public ArrayList<ExamDescList> getData(){
        return data;
    }
}
