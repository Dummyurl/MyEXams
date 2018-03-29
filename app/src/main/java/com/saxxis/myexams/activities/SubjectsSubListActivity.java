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
import com.saxxis.myexams.adapters.SubjectSubListAdapter;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.utils.ItemClickSupport;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.SubjectsSubCategories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectsSubListActivity extends AppCompatActivity {

    @BindView(R.id.recv_subjectssublist)
    RecyclerView rvSubjectList;

    @BindView(R.id.toolbarsublist)
    Toolbar toolbarsublist;

    String subjectID;
    String subjectname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_sub_list);
        ButterKnife.bind(this);

        Bundle extras=getIntent().getExtras();
        if (extras!=null){
            subjectID=extras.getString("subjectid");
            subjectname=extras.getString("subjectname");

        }


        setSupportActionBar(toolbarsublist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(subjectname);

        rvSubjectList.setHasFixedSize(true);
        rvSubjectList.setLayoutManager(new LinearLayoutManager(this));
        rvSubjectList.addItemDecoration(new RecvDecors(this,R.dimen.job_listing_main_offset));

        AppHelper.showDialog(SubjectsSubListActivity.this,"Loading Please Wait...");
        Log.e("response", AppConstants.SUBJECTS_SUB_URL+subjectID);
        StringRequest stringRequest=new StringRequest(AppConstants.SUBJECTS_SUB_URL+subjectID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        AppHelper.hideDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                final ArrayList<SubjectsSubCategories> mdata = new ArrayList<>();

                                if (jsonObject.getString("status").equals("ok")){
                                    JSONArray dataarray=jsonObject.getJSONArray("data");
                                    for (int i=0;i<dataarray.length();i++){
                                      JSONObject objdataget=dataarray.getJSONObject(i);
                                      mdata.add(new SubjectsSubCategories(objdataget.getString("id"),
                                              objdataget.getString("title"),
                                              objdataget.getString("link"),
                                              objdataget.getString("image"),
                                              objdataget.getString("description")));
                                    }

                                   rvSubjectList.setAdapter(new SubjectSubListAdapter(SubjectsSubListActivity.this, mdata));
                                   ItemClickSupport.addTo(rvSubjectList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                        @Override
                                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                            gotoSubCategoryCheck(mdata.get(position).getId().toString(),mdata.get(position));
//                                            Intent intent=new Intent(SubjectsSubListActivity.this,ExamsDescriptionActivity.class);
//                                            intent.putExtra("subexamsubid",mdata.get(position).getLink());
//                                            startActivity(intent);
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

    private void gotoSubCategoryCheck(String dataId,SubjectsSubCategories subcatlistdata) {

        final SubjectsSubCategories dataheading=subcatlistdata;

        AppHelper.showDialog(SubjectsSubListActivity.this,"Loading Please Wait...");
        Log.e("response", AppConstants.SUBJECTS_SUB_URL+dataId);
        StringRequest stringRequest=new StringRequest(AppConstants.SUBJECTS_SUB_URL+dataId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("response", response);
                        AppHelper.hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final  ArrayList<SubjectsSubCategories> mdatalist = new ArrayList<>();

                            if (jsonObject.getString("status").equals("ok")) {
                                JSONArray dataarray = jsonObject.getJSONArray("data");

                                if (dataarray.length() > 0) {

                                    for (int i=0;i<dataarray.length();i++){
                                        JSONObject objdataget=dataarray.getJSONObject(i);
                                        mdatalist.add(new SubjectsSubCategories(objdataget.getString("id"),
                                                objdataget.getString("title"),
                                                objdataget.getString("link"),
                                                objdataget.getString("image"),
                                                objdataget.getString("description")));
                                    }

                                    rvSubjectList.setAdapter(new SubjectSubListAdapter(SubjectsSubListActivity.this, mdatalist));
                                    ItemClickSupport.addTo(rvSubjectList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                        @Override
                                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                            Intent intent=new Intent(SubjectsSubListActivity.this,ExamsDescriptionActivity.class);
                                            intent.putExtra("subexamsubid",mdatalist.get(position).getLink());
                                            intent.putExtra("examtitle",mdatalist.get(position).getTitle());
                                            startActivity(intent);
                                        }
                                    });
                                }else {
                                            Intent intent=new Intent(SubjectsSubListActivity.this,ExamsDescriptionActivity.class);
                                            intent.putExtra("subexamsubid",dataheading.getLink());
                                            intent.putExtra("examtitle",dataheading.getTitle());
                                            startActivity(intent);
                                }
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

