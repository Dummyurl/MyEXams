package com.saxxis.myexams.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saxxis.myexams.model.NotifiDataResult;

import java.util.ArrayList;

/**
 * Created by android2 on 6/7/2017.
 */

public class NotificationParser {

    public ArrayList<NotifiDataResult> data;

    public NotificationParser(){
        data=new ArrayList<NotifiDataResult>();
    }

    public static NotificationParser getmInstance(String response){
        Gson gson=new GsonBuilder().create();
        NotificationParser notificationParser=gson.fromJson(response,NotificationParser.class);
        return notificationParser;
    }

    public ArrayList<NotifiDataResult> getData(){
        return data;
    }



}
