package com.saxxis.myexams.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.adapters.AcademicSubListAdapter;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.utils.ItemClickSupport;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.AcademicSubList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcademicSubListingActivity extends AppCompatActivity {


    @BindView(R.id.recv_academic_sublist)
    RecyclerView recvAcademicSubDetails;

    @BindView(R.id.subjectsublist)
    Toolbar toolbar_sublist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_sub_listing);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar_sublist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("subjecttitle"));
//        getSupportActionBar().setHomeButtonEnabled(true);


        String subjectlink=getIntent().getExtras().getString("subjectlink");

        recvAcademicSubDetails.setHasFixedSize(true);
        recvAcademicSubDetails.setLayoutManager(new LinearLayoutManager(AcademicSubListingActivity.this));
        recvAcademicSubDetails.addItemDecoration(new RecvDecors(this,R.dimen.job_listing_main_offset));

        Log.e("response",AppConstants.ACADEMIC_SUB_LIST_URL + subjectlink);
        AppHelper.showDialog(AcademicSubListingActivity.this,"Loading Please Wait...");
        StringRequest stringRequest=new StringRequest(AppConstants.ACADEMIC_SUB_LIST_URL + subjectlink,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")){
                                final ArrayList<AcademicSubList> mdata=new ArrayList<>();
                                JSONArray jsonArray=jsonObject.getJSONArray("data");

                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject dataobject=jsonArray.getJSONObject(i);
                                    mdata.add(new AcademicSubList(dataobject.getString("SubjectId")
                                    ,dataobject.getString("SubjectName"),dataobject.getString("Uploadlogo"),dataobject.getString("Description")));
                                }
                                recvAcademicSubDetails.setAdapter(new AcademicSubListAdapter(AcademicSubListingActivity.this,mdata));
                                ItemClickSupport.addTo(recvAcademicSubDetails).setOnItemClickListener(
                                        new ItemClickSupport.OnItemClickListener() {
                                            @Override
                                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                                Intent intent=new Intent(AcademicSubListingActivity.this,ExamsDescriptionActivity.class);
                                                intent.putExtra("subexamsubid",mdata.get(position).getSubjectId());
                                                intent.putExtra("examtitle",mdata.get(position).getSubjectName());
                                                startActivity(intent);
                                            }
                                        }
                                );
                            }
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
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
