package com.saxxis.myexams.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.adapters.ExamsAdapter;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.utils.ItemClickSupport;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.ExamTypes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamsListActivity extends AppCompatActivity {

    @BindView(R.id.recv_exams)
    RecyclerView recv_exams;

    @BindView(R.id.exams_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.exams_toolbar)
    Toolbar examtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        ButterKnife.bind(this);

        setSupportActionBar(examtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recv_exams.setHasFixedSize(true);
        recv_exams.setLayoutManager(new GridLayoutManager(this,3));
        recv_exams.addItemDecoration(new RecvDecors(this,R.dimen.job_listing_main_offset));

//        final int exam_icons[]={R.drawable.upsc, R.drawable.ssc, R.drawable.bank, R.drawable.insurance,
//                R.drawable.railway, R.drawable.entrance, R.drawable.eligibility,
//                R.drawable.states, R.drawable.entrance,R.drawable.upsc, R.drawable.ssc, R.drawable.bank,
//                R.drawable.insurance,R.drawable.railway, R.drawable.entrance, R.drawable.eligibility,
//                R.drawable.states, R.drawable.entrance};

//        int examnames[]= {R.string.upsc,R.string.ssc,R.string.bank,R.string.insurance,R.string.railway,R.string.entrance,R.string.eligibility,R.string.states,R.string.others};

        AppHelper.showDialog(ExamsListActivity.this,"Loading Please Wait");
        StringRequest stringRequest=new StringRequest(AppConstants.EXAMS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                AppHelper.hideDialog();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("ok")){
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        final ArrayList<ExamTypes> examitems = new ArrayList<ExamTypes>();
                        ExamTypes examTypes=null;
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                                examTypes = new ExamTypes(AppConstants.SERVER_URL+"/"+object.getString("image").replaceAll(" ","%20"),object.getString("title"),object.getString("id"));
                                examitems.add(examTypes);
                                recv_exams.setAdapter(new ExamsAdapter(ExamsListActivity.this,examitems));
                                ItemClickSupport.addTo(recv_exams).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                    @Override
                                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                        Intent intent=new Intent(ExamsListActivity.this,SubjectsSubListActivity.class);
                                        intent.putExtra("subjectid",examitems.get(position).getCategoryId());
                                        intent.putExtra("subjectname",examitems.get(position).getCategoryName());
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }
                                });
                        }
                    }else {
                        new AlertDialog.Builder(ExamsListActivity.this)
                                .setCancelable(false)
                                .setMessage("Error Occurred Try Again...")
                                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                       finish();
                                    }
                                })
                                .create();
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
}
