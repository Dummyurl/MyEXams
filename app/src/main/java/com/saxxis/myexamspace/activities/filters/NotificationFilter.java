package com.saxxis.myexamspace.activities.filters;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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

public class NotificationFilter extends AppCompatActivity {

    @BindView(R.id.id_filnotf_catogry)
    TextView txtCategory;

    @BindView(R.id.id_filnotf_qualification)
    TextView txtQualifications;

    @BindView(R.id.notify_filtersearch)
    TextView txtSearch;

    @BindView(R.id.notffilter_toolbar)
    Toolbar filterToolbar;

    String categoryId="0";
    String qualificationId="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_filter);
        ButterKnife.bind(this);

        setSupportActionBar(filterToolbar);
        filterToolbar.setTitle("Select and Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.id_filnotf_catogry)
    void getCategoryData(){
        AppHelper.showDialog(NotificationFilter.this,"Loading PLease Wait...");
        StringRequest stringRequest = new StringRequest(AppConstants.FILTER_NOTIFICATIONS_CAT+"department",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        Log.d("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray objArray = jsonObject.getJSONArray("data");

                            if (objArray.length()>0){
                                final String[] idDepartmentarr = new String[objArray.length()];
                                final String[] nameDepartmentArr=new String[objArray.length()];

                                for (int i = 0; i < objArray.length(); i++) {
                                    JSONObject dataObject =objArray.getJSONObject(i);
                                    nameDepartmentArr[i]=dataObject.optString("department");
                                    idDepartmentarr[i]=dataObject.optString("id");
                                }

                                new AlertDialog.Builder(NotificationFilter.this)
                                        .setTitle("Select Mock Tests")
                                        .setItems(nameDepartmentArr, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                categoryId =idDepartmentarr[which];
                                                txtCategory.setText(nameDepartmentArr[which]);
                                            }
                                        })
                                        .create().show();
                            }

                            if (objArray.length() == 0){
                                new AlertDialog.Builder(NotificationFilter.this)
                                        .setMessage("No Data Found")
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




    @OnClick(R.id.id_filnotf_qualification)
    void getQualification(){
        AppHelper.showDialog(NotificationFilter.this,"Loading PLease Wait...");
        StringRequest stringRequest = new StringRequest(AppConstants.FILTER_NOTIFICATIONS_CAT+"qualification",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        Log.d("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray objArray = jsonObject.getJSONArray("data");

                            if (objArray.length()>0){
                                final String[] idQualificationarr= new String[objArray.length()];
                                final String[] nameQualificationArr=new String[objArray.length()];

                                for (int i = 0; i < objArray.length(); i++) {
                                    JSONObject dataObject =objArray.getJSONObject(i);
                                    nameQualificationArr[i]=dataObject.optString("location");
                                    idQualificationarr[i]=dataObject.optString("id");
                                }

                                new AlertDialog.Builder(NotificationFilter.this)
                                        .setTitle("Select Mock Tests")
                                        .setItems(nameQualificationArr, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                qualificationId =idQualificationarr[which];
                                                txtQualifications.setText(nameQualificationArr[which]);
                                            }
                                        })
                                        .create().show();
                            }

                            if (objArray.length() == 0){
                                new AlertDialog.Builder(NotificationFilter.this)
                                        .setMessage("No Data Found")
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

    @OnClick(R.id.notify_filtersearch)
    void getFilterSearch(){
        String url=AppConstants.SEARCH_FILTER_NOTIFICATIONS+categoryId+"&qualid="+qualificationId;
        Log.d("response",url);
        Intent i = new Intent();
        i.putExtra("searchurl",url);
        setResult(555,i);
        finish();
    }

}
