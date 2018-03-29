package com.saxxis.myexams.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saxxis.myexams.model.PackagesItems;

import java.util.ArrayList;

/**
 * Created by android2 on 6/8/2017.
 */

public class PackagesParser {

    ArrayList<PackagesItems> data;

    public PackagesParser(){
        data=new ArrayList<PackagesItems>();
    }

    public static  PackagesParser getInstance(String response){
        Gson gson= new GsonBuilder().create();
        PackagesParser parsedata=gson.fromJson(response,PackagesParser.class);
        return  parsedata;
    }

    public ArrayList<PackagesItems> getData(){
        return data;
    }

}
