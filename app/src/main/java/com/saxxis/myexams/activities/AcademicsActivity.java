package com.saxxis.myexams.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.adapters.AcademicAdapter;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.utils.ItemClickSupport;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.ExamIntAcadem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcademicsActivity extends AppCompatActivity {

    @BindView(R.id.recv_academics)
    RecyclerView recyclerView_academics;

    @BindView(R.id.toolbar_academics)
    Toolbar toolbar_aca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar_aca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView_academics.setHasFixedSize(true);
        recyclerView_academics.setLayoutManager(new GridLayoutManager(AcademicsActivity.this,3));
        recyclerView_academics.addItemDecoration(new RecvDecors(this,R.dimen.job_listing_main_offset));

       StringRequest stringRequest = new StringRequest(AppConstants.ACADEMIC_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")){
                                JSONArray jsonArray=jsonObject.getJSONArray("data");

                                final ArrayList<ExamIntAcadem> examitems = new ArrayList<ExamIntAcadem>();
                                ExamIntAcadem examTypes=null;
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject objData=jsonArray.getJSONObject(i);
                                    examTypes = new ExamIntAcadem(AppConstants.SERVER_URL+objData.getString("image"),objData.getString("title"),objData.getString("id"),objData.getString("link"));
                                    examitems.add(examTypes);
                                }

                                recyclerView_academics.setAdapter(new AcademicAdapter(AcademicsActivity.this,examitems));
                                ItemClickSupport.addTo(recyclerView_academics).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                    @Override
                                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                        Intent intent=new Intent(AcademicsActivity.this,AcademicSubListingActivity.class);
                                        intent.putExtra("subjectlink",examitems.get(position).getLink());
                                        intent.putExtra("subjecttitle",examitems.get(position).getTitle());
                                        startActivity(intent);
                                    }
                                });
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
    }
}
