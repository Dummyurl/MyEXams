package com.saxxis.myexamspace.app;

import android.app.Application;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.saxxis.myexamspace.R;

/**
 * Created by android2 on 5/31/2017.
 */

public class MyExamsApp  extends Application {

    static MyExamsApp myInstance;
    private RequestQueue requestQueue;

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;


    private void setmInstance(MyExamsApp myExamsApp) {
        MyExamsApp.myInstance=myExamsApp;
    }

    public static synchronized MyExamsApp getMyInstance(){
        return myInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setmInstance(this);
        requestQueue= Volley.newRequestQueue(this);
        sAnalytics = GoogleAnalytics.getInstance(this);
    }

//    public RequestQueue getRequestQueue() {
//        return requestQueue;
//    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
}
