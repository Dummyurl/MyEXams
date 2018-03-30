package com.saxxis.myexams.activities;

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
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.model.MustRead;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MustReadDetailsActivity extends AppCompatActivity {

    @BindView(R.id.detl_latestupdates_title)
    TextView txtTitle;

    @BindView(R.id.detl_latestupdates_description)
    WebView txtDescription;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    MustRead mData;


    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educaiton_detailed);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("detailtitle"));
//      getSupportActionBar().setHomeButtonEnabled(true);

        mAdView = (AdView)findViewById(R.id.adbannerView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        mData = getIntent().getExtras().getParcelable("detaildata");
        txtTitle.setText(AppHelper.fromHtml(mData.getTitle()));
        txtDescription.getSettings().setDefaultTextEncodingName("utf-8");
        txtDescription.getSettings().setJavaScriptEnabled(true);
        txtDescription.getSettings().setMinimumLogicalFontSize(16);
        txtDescription.getSettings().setAllowContentAccess(true);
        //  Log.d("response",mData.getDescription().toString());

        txtDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,mData.getIntrotext(),"text/html; charset=utf-8","UTF-8",null);

        //getLatestUpdateData();
        //txtDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,mData.getIntrotext(),"text/html; charset=utf-8","UTF-8",null);
    }

    void getLatestUpdateData() {
        String url = AppConstants.LATEST_UPDATES_DETAILS;// + mData.getId();
        Log.e("LatestUpdateDetails",url);
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        //txtDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,response,"text/html; charset=utf-8","UTF-8",null);
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String introText = "";
                            if(jsonObject.has("introtext")) {
                                introText = jsonObject.getString("introtext");
                            }
                            txtDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,introText,"text/html; charset=utf-8","UTF-8",null);
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
            if (comefrom.equals("home")) {
                Intent intent = new Intent(MustReadDetailsActivity.this, CAInEnglishActivity.class);
                intent.putExtra("tabSelection","2");
                startActivity(intent);
                finish();
            }
            if (comefrom.equals("host")){
                finish();
            }
            return true;
        }

        if (id==R.id.action_home){
            startActivity(new Intent(MustReadDetailsActivity.this,MainActivity.class));
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
