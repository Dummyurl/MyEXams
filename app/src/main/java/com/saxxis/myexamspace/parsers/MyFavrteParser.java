package com.saxxis.myexamspace.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saxxis.myexamspace.model.MyFavourites;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 11/7/2017.
 */

public class MyFavrteParser {

    public ArrayList<MyFavourites> mData;

    public MyFavrteParser(){mData = new ArrayList<MyFavourites>();}

    public static MyFavrteParser getInstance(String response){
        Gson gson= new GsonBuilder().create();
        MyFavrteParser parsedata=gson.fromJson(response,MyFavrteParser.class);
        return  parsedata;
    }
    public ArrayList<MyFavourites> getData(){return mData;}
}
