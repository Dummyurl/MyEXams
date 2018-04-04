package com.saxxis.myexamspace.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.helper.AppHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity {

//    @BindView(R.id.recv_categories)
//    RecyclerView recvCategories;

    @BindView(R.id.id_fil_class)
    TextView txtClassName;
    @BindView(R.id.id_fil_subject)
    TextView txtSubjectName;
    @BindView(R.id.id_fil_test_series)
    TextView txtTestSeries;
    @BindView(R.id.id_fil_sub_chapter)
    TextView txtSubChapter;

    @BindView(R.id.inst_one)
    TextView txtInstone;
    @BindView(R.id.inst_two)
    TextView txtInstTwo;
    @BindView(R.id.inst_three)
    TextView txtInstThree;
    @BindView(R.id.inst_four)
    TextView txtInstFour;

    @BindView(R.id.filter_toolbar)
    Toolbar filterToolbar;

    String classID="";
    String subjectID="";
    String testseriesID="";
    String mocktestID="";


    int extraint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        setSupportActionBar(filterToolbar);
        filterToolbar.setTitle("Search Your Favourite");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            extraint = extras.getInt("pack");
            if (extraint==1){
                txtTestSeries.setVisibility(View.VISIBLE);
                txtSubChapter.setVisibility(View.VISIBLE);

                txtInstThree.setVisibility(View.VISIBLE);
                txtInstFour.setVisibility(View.VISIBLE);

            }

            if (extraint==2){
                txtTestSeries.setVisibility(View.GONE);
                txtSubChapter.setVisibility(View.GONE);

                txtInstThree.setVisibility(View.GONE);
                txtInstFour.setVisibility(View.GONE);
            }

        }


//        recvCategories.setHasFixedSize(true);
//        recvCategories.setLayoutManager(new LinearLayoutManager(this));
//        recvCategories.addItemDecoration(new RecvDecors(this,R.dimen.job_listing_main_offset));

//        Log.d("response",AppConstants.FILTER_PACKAGE);
//        StringRequest stringRequest=new StringRequest(AppConstants.FILTER_PACKAGE,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("response", response);
//                            try {
//                                JSONObject objResponse = new JSONObject(response);
//                                if (objResponse.getString("status").equals("ok")) {
//                                    JSONArray jsonArray=objResponse.getJSONArray("data");
//                                    final ArrayList<Category> category=new ArrayList<>();
//                                    for (int i=0;i<jsonArray.length();i++){
//                                        JSONObject jsonObject=jsonArray.getJSONObject(i);
//                                        category.add(new Category(jsonObject.getString("CategoryId"),jsonObject.getString("CategoryName")));
//                                    }
//
//                                    recvCategories.setAdapter(new CategoryAdapter(FilterActivity.this,category));
//                                    ItemClickSupport.addTo(recvCategories).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                                            Intent i = new Intent();
//                                            i.putExtra("categoryID",category.get(position).getCategoryId());
//                                            setResult(RESULT_OK,i);
//                                            finish();
//                                        }
//                                    });
//                                 }
//
//                                }
//                                catch (JSONException je) {
//
//                                }
//                        }
//                    },new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    });
//
//            MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.id_fil_class)
    void getClassName(){

        AppHelper.showDialog(FilterActivity.this,"Loading Please Wait...");
        Log.e("response",AppConstants.CATEGORY_FILTER_PACKAGE+"catagory");

        StringRequest stringRequest=new StringRequest(AppConstants.CATEGORY_FILTER_PACKAGE+"catagory",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray objArray = jsonObject.getJSONArray("data");

                            final String[] idArray = new String[objArray.length()];
                            final String[] nameArray=new String[objArray.length()];
                            for (int i = 0; i < objArray.length(); i++) {
                                JSONObject dataObject =objArray.getJSONObject(i);
                                nameArray[i]=dataObject.optString("CategoryName");
                                idArray[i]=dataObject.optString("CategoryId");
                            }

                            AppHelper.hideDialog();
                            new AlertDialog.Builder(FilterActivity.this)
                                    .setTitle("Select Class")
                                    .setItems(nameArray, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    classID = idArray[which];
                                                    subjectID="";
                                                    testseriesID="";
                                                    mocktestID="";
                                                    txtClassName.setText(nameArray[which]);
                                                    txtSubjectName.setText("Select Subject");
                                                    txtTestSeries.setText("Select Test Series");
                                                    txtSubChapter.setText("Select Sub Chapter");
                                                }
                                            }
                                    )
                                    .create().show();

                        }catch (JSONException je){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }


    @OnClick(R.id.id_fil_subject)
    void getSubjectName(){

        AppHelper.showDialog(FilterActivity.this,"Loading Please Wait...");
        String url = AppConstants.CATEGORY_FILTER_PACKAGE+"getsubjects&categoryid=";
        if (classID!=""){
            url = AppConstants.CATEGORY_FILTER_PACKAGE+"getsubjects&categoryid="+classID;
        }

        Log.e("response",url);
        StringRequest stringRequest=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        Log.d("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray objArray = jsonObject.getJSONArray("data");

                            final String[] idSubjectarr = new String[objArray.length()];
                            final String[] nameSubjectArr=new String[objArray.length()];


                            for (int i = 0; i < objArray.length(); i++) {
                                JSONObject dataObject =objArray.getJSONObject(i);
                                nameSubjectArr[i]=dataObject.optString("SubjectName");
                                idSubjectarr[i]=dataObject.optString("SubjectId");
                            }

                            new AlertDialog.Builder(FilterActivity.this)
                                    .setTitle("Select Subjest")
                                    .setItems(nameSubjectArr, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    subjectID=idSubjectarr[which];
                                                    testseriesID="";
                                                    mocktestID="";
                                                    txtSubjectName.setText(nameSubjectArr[which]);
                                                    txtTestSeries.setText("Select Test Series");
                                                    txtSubChapter.setText("Select Sub Chapter");
                                                }
                                            })
                                    .create().show();

                        }catch (JSONException je){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }


    @OnClick(R.id.id_fil_test_series)
    void getTestSeries(){
        AppHelper.showDialog(FilterActivity.this,"Loading PLease Wait...");
        StringRequest stringRequest=new StringRequest(AppConstants.CATEGORY_FILTER_PACKAGE+"sublevel&subjectid="+subjectID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        AppHelper.hideDialog();
                        Log.d("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray objArray = jsonObject.getJSONArray("data");

                            if (objArray.length()>0) {
                                final String[] idTestSeriesarr = new String[objArray.length()];
                                final String[] nameTestSeriesArr = new String[objArray.length()];

                                for (int i = 0; i < objArray.length(); i++) {
                                    JSONObject dataObject = objArray.getJSONObject(i);
                                    nameTestSeriesArr[i] = dataObject.optString("SublevelName");
                                    idTestSeriesarr[i] = dataObject.optString("SublevelId");
                                }
                                new AlertDialog.Builder(FilterActivity.this)
                                        .setTitle("Select Test Series")
                                        .setItems(nameTestSeriesArr, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                testseriesID = idTestSeriesarr[which];
                                                mocktestID = "";
                                                txtTestSeries.setText(nameTestSeriesArr[which]);
                                                txtSubChapter.setText("Select Sub Chapter");
                                            }
                                        })
                                        .create().show();
                            }
                            if (objArray.length()==0){
                                new AlertDialog.Builder(FilterActivity.this)
                                        .setMessage("No Data Found For this Subject")
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create().show();
                            }
                        }catch (JSONException je){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }

    @OnClick(R.id.id_fil_sub_chapter)
    void getSubLevel(){
        AppHelper.showDialog(FilterActivity.this,"Loading PLease Wait...");
        StringRequest stringRequest=new StringRequest(AppConstants.CATEGORY_FILTER_PACKAGE+"subchapter&chpterid="+testseriesID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        Log.d("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray objArray = jsonObject.getJSONArray("data");

                            if (objArray.length()>0){
                                final String[] idSubChapterarr = new String[objArray.length()];
                                final String[] nameSubChapterArr=new String[objArray.length()];
                                for (int i = 0; i < objArray.length(); i++) {
                                    JSONObject dataObject =objArray.getJSONObject(i);
                                    nameSubChapterArr[i]=dataObject.optString("SubchapterName");
                                    idSubChapterarr[i]=dataObject.optString("SubchapterId");
                                }
                                new AlertDialog.Builder(FilterActivity.this)
                                        .setTitle("Select Mock Tests")
                                        .setItems(nameSubChapterArr, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mocktestID =idSubChapterarr[which];
                                                txtSubChapter.setText(nameSubChapterArr[which]);
                                            }
                                        })
                                        .create().show();
                            }
                            if (objArray.length()==0){
                                new AlertDialog.Builder(FilterActivity.this)
                                        .setMessage("No Data Found For this Subject")
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create().show();

                            }

                        }catch (JSONException je){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }

    @OnClick(R.id.filtersearch)
    void searchItems(){

        String url = null;
        if (extraint==1){
            url=AppConstants.SEARCH_FILTER_PACKAGE+classID+"&subid="+subjectID+"&sublevel="+testseriesID+"&chapter="+mocktestID;
        }
        if (extraint==2){
            url=AppConstants.SEARCH_COMBO_FILTER_PACKAGE+"&catid="+classID+"&subid="+subjectID;
        }

        Log.d("response",url);
        Intent i = new Intent();
        i.putExtra("searchurl",url);
        setResult(444,i);
        finish();
    }

}
