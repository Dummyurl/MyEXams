package com.saxxis.myexamspace.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrendingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendingFragment extends Fragment {

    @BindView(R.id.trending_activeexamswebview)
    WebView wv_trend;
    @BindView(R.id.trending_activenotification)
    WebView wv_trendNotification;
    @BindView(R.id.trending_newswebview)
    WebView wv_trendNewsWebView;
    @BindView(R.id.trending_trendnowbottom)
    WebView trendNowDescription;

    private AdView mAdView,footer_adView,belowfooterAbyssAdCiew;

    public TrendingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TrendingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrendingFragment newInstance() {
        TrendingFragment fragment = new TrendingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        ButterKnife.bind(this,view);

        mAdView = (AdView)view.findViewById(R.id.adbannerView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        footer_adView=(AdView)view.findViewById(R.id.adbannerView_footer);
        AdRequest footeradRequest = new AdRequest.Builder()
                .build();
        footer_adView.loadAd(footeradRequest);
        belowfooterAbyssAdCiew=(AdView)view.findViewById(R.id.adbannerView_footer_abbyss);
        AdRequest belowFooteradRequest = new AdRequest.Builder()
                .build();
        belowfooterAbyssAdCiew.loadAd(belowFooteradRequest);

        StringRequest stringRequest = new StringRequest(AppConstants.TRENDING_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")){

                                JSONArray jsonArray=jsonObject.getJSONArray("data");
                                String allContent="";

                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject dataobj=jsonArray.getJSONObject(i);
//                                    String title=dataobj.getString("title");
                                    if (i==0){
                                        String activeexams=dataobj.getString("content").substring(0,dataobj.getString("content").indexOf("Active Notifications"));
                                        wv_trend.loadDataWithBaseURL("http://myexamspace.com",activeexams.replace("Trending Now","").replace("Active Exams (exam date)",""),"text/html","utf-8",null);
                                        wv_trend.setBackgroundColor(Color.parseColor("#C4C4C4"));
                                        wv_trend.getSettings().setJavaScriptEnabled(true);
                                        String activeNotification=dataobj.getString("content").substring(dataobj.getString("content").indexOf("Active Notifications"),dataobj.getString("content").indexOf("Trending NEWS")).replace("Active Notifications (last date)","");
                                        wv_trendNotification.loadDataWithBaseURL("http://myexamspace.com",activeNotification.replace("Active Notifications (last date)",""),"text/html","utf-8",null);
                                        wv_trendNotification.setBackgroundColor(Color.parseColor("#C4C4C4"));
                                        wv_trendNotification.getSettings().setJavaScriptEnabled(true);
                                        String trendingnews=dataobj.getString("content").substring(dataobj.getString("content").indexOf("Trending NEWS"));
                                        wv_trendNewsWebView.loadDataWithBaseURL("http://myexamspace.com",trendingnews.replace("Trending NEWS",""),"text/html","utf-8",null);
                                        wv_trendNewsWebView.getSettings().setJavaScriptEnabled(true);
                                        wv_trendNewsWebView.setBackgroundColor(Color.parseColor("#C4C4C4"));
                                    }
                                    if (i!=0){
                                        allContent = dataobj.getString("content") ;
                                    }
                               }
                               trendNowDescription.loadDataWithBaseURL("http://myexamspace.com",allContent.replace("Trending Now",""),"text/html","utf-8",null);
                               trendNowDescription.getSettings().setJavaScriptEnabled(true);
                               trendNowDescription.setBackgroundColor(Color.parseColor("#C4C4C4"));
                            }
                        }catch (JSONException jse){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);

        return view;
    }


        @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        if (footer_adView != null) {
            footer_adView.pause();
        }
        if (belowfooterAbyssAdCiew != null) {
            belowfooterAbyssAdCiew.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        if (footer_adView != null) {
            footer_adView.resume();
        }
        if (belowfooterAbyssAdCiew != null) {
            belowfooterAbyssAdCiew.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        if (footer_adView != null) {
            footer_adView.destroy();
        }
        if (belowfooterAbyssAdCiew != null) {
            belowfooterAbyssAdCiew.destroy();
        }
        super.onDestroy();
    }
}
