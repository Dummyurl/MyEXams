package com.saxxis.myexamspace.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.model.LatestUpdates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderImageDetailsActivity extends AppCompatActivity {

    @BindView(R.id.detl_latestupdates_title)
    TextView txtTitle;

    @BindView(R.id.detl_latestupdates_description)
    WebView txtDescription;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    LatestUpdates mData;
    String sliderId;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educaiton_detailed);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Slider Details");
//      getSupportActionBar().setHomeButtonEnabled(true);

        mAdView = (AdView)findViewById(R.id.adbannerView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        sliderId = getIntent().getExtras().getString("sliderId");
        //mData = getIntent().getExtras().getParcelable("detaildata");
        //txtTitle.setText(AppHelper.fromHtml(mData.getCurrentAffByte()));
        txtDescription.getSettings().setDefaultTextEncodingName("utf-8");
        txtDescription.getSettings().setJavaScriptEnabled(true);
        txtDescription.getSettings().setMinimumLogicalFontSize(16);
        txtDescription.getSettings().setAllowContentAccess(true);
        //  Log.d("response",mData.getDescription().toString());

        //txtDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,mData.getIntrotext(),"text/html; charset=utf-8","UTF-8",null);

        getLatestUpdateData();
        //txtDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,mData.getIntrotext(),"text/html; charset=utf-8","UTF-8",null);
    }

    void getLatestUpdateData() {
        String url = AppConstants.SLIDER_DETAILS + sliderId;
        Log.e("LatestUpdateDetails",url);
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        //txtDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,response,"text/html; charset=utf-8","UTF-8",null);
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray engupdatesArray=jsonObject.getJSONArray("data");
                            JSONObject objLatstCurAff=engupdatesArray.getJSONObject(0);
                            String currentAffByte=objLatstCurAff.getString("title");
                            String titleId=objLatstCurAff.getString("publish_up");
                            String introtext =objLatstCurAff.getString("introtext");

                            LatestUpdates data = new LatestUpdates(titleId,currentAffByte,introtext);
                            txtTitle.setText(AppHelper.fromHtml(data.getCurrentAffByte()));
                            txtDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,data.getIntrotext(),"text/html; charset=utf-8","UTF-8",null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_homeitem,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            String comefrom = getIntent().getExtras().getString("comefrom");
            /*if (comefrom.equals("home")) {
                Intent intent = new Intent(SliderImageDetailsActivity.this, CAInEnglishActivity.class);
                intent.putExtra("tabSelection","2");
                startActivity(intent);
                finish();
            }
            if (comefrom.equals("host")){
                finish();
            }*/
            finish();
            return true;
        }

        if (id==R.id.action_home){
            startActivity(new Intent(SliderImageDetailsActivity.this,MainActivity.class));
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
