package com.saxxis.myexams.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.adapters.SubjectsAdapter;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.utils.ItemClickSupport;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.SubjectsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectsActivity extends AppCompatActivity {

    @BindView(R.id.recv_subjects)
    RecyclerView recvSubjects;

    @BindView(R.id.toolbar_subject)
    Toolbar tlbarSubjectg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        ButterKnife.bind(this);

        setSupportActionBar(tlbarSubjectg);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recvSubjects.setHasFixedSize(true);
        recvSubjects.setLayoutManager(new GridLayoutManager(this,3));
        recvSubjects.addItemDecoration(new RecvDecors(this,R.dimen.job_listing_main_offset));

//        final int exam_icons[]={R.drawable.entrance, R.drawable.entrance, R.drawable.entrance, R.drawable.entrance,
//                R.drawable.entrance, R.drawable.entrance, R.drawable.entrance,
//                R.drawable.entrance, R.drawable.entrance,R.drawable.entrance, R.drawable.entrance, R.drawable.entrance,
//                R.drawable.entrance,R.drawable.entrance, R.drawable.entrance, R.drawable.entrance,
//                R.drawable.entrance, R.drawable.entrance};

        AppHelper.showDialog(SubjectsActivity.this,"Loading Please Wait...");
        StringRequest stringRequest=new StringRequest(AppConstants.SUBJECTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        AppHelper.hideDialog();

                        final ArrayList<SubjectsList> mdata=new ArrayList<SubjectsList>();
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")){
                                JSONArray jsonArray=jsonObject.getJSONArray("data");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject dataobject=jsonArray.getJSONObject(i);
                                    String id=dataobject.getString("id");
                                    String title=dataobject.getString("title");
                                    String image =AppConstants.SERVER_URL+"/"+dataobject.getString("image");
                                    mdata.add(new SubjectsList(id,title,image));
                                }
                                recvSubjects.setAdapter(new SubjectsAdapter(SubjectsActivity.this,mdata));

                                ItemClickSupport.addTo(recvSubjects).setOnItemClickListener(
                                        new ItemClickSupport.OnItemClickListener() {
                                    @Override
                                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                        Intent intent=new Intent(SubjectsActivity.this,SubjectsSubListActivity.class);
                                        intent.putExtra("subjectid",mdata.get(position).getId());
                                        intent.putExtra("subjectname",mdata.get(position).getTitle());
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
