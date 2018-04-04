package com.saxxis.myexamspace.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TermsAndConditionsActivity extends AppCompatActivity {

    Toolbar toolbarTermscond;
    WebView txtTermsAndConditions;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        toolbarTermscond = (Toolbar)findViewById(R.id.toolbar_termscond);
        setSupportActionBar(toolbarTermscond);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTermsAndConditions = (WebView)findViewById(R.id.txt_termsandconfditionsd);
        progressBar = (ProgressBar)findViewById(R.id.progressbarterms);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(AppConstants.TERMS_AND_CONDITIONS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.e("response",response);
                    String status=jsonObject.getString("status");
                    if (status.equals("ok")){
                        progressBar.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject contentObj = jsonArray.getJSONObject(0);
                        txtTermsAndConditions.loadData(contentObj.getString("content"),"text/html","utf-8");
                        txtTermsAndConditions.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                        txtTermsAndConditions.getSettings().setJavaScriptEnabled(true);

                    }
//                    else if (status.equals("ko")){
//                        AppHelper.Snackbar(LoginActivity.this,coordinatorLayout,jsonObject.getString("error_description")
//                                ,R.color.darkgrey,R.color.white);
//                    }else {
//                        Toast.makeText(LoginActivity.this,""+response,Toast.LENGTH_SHORT).show();
//                    }
                }
                    catch (JSONException e) {
                            e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);

                }
            });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
