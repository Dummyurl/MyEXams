package com.saxxis.myexams.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.model.MyPackOrderListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsActivity extends AppCompatActivity {

    MyPackOrderListData myOrderDatails;

    @BindView(R.id.txt_status)TextView txtstatus;
    @BindView(R.id.txt_price)TextView  txtprice;
    @BindView(R.id.txt_tranxid)TextView  txttranxid;
    @BindView(R.id.txt_trnxdate)TextView  txttrnxdate;
    @BindView(R.id.txt_packagename)TextView  txtpackagename;
    @BindView(R.id.txt_catgryname)TextView  txtcatgryname;
    @BindView(R.id.txt_orderitemprice)TextView  txtorderitemprice;
    @BindView(R.id.txt_quantity)TextView  txtquantity;
    @BindView(R.id.txt_examcount)TextView  txtexamcount;
    @BindView(R.id.txt_totexam_count)TextView  txttotexam_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        Bundle extras =getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_orderdetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (extras!=null){
           myOrderDatails = extras.getParcelable("orderdetails");
            StringRequest stringRequest = new StringRequest(AppConstants.MY_PURCHASE_PACKAGE_RESULTS+myOrderDatails.getOrder_num(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.e("response",response);
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getString("status").equals("ok")){
                                    JSONArray dataArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataObject = dataArray.getJSONObject(i);

                                        String name = dataObject.getString("PackageName");
                                        String totalExamCount = dataObject.getString("QuizNum");
                                        String packagePrice = dataObject.getString("PackagePrice");
                                        String packageQuantity = dataObject.getString("PackageQuantity");
                                        String CategoryName =dataObject.getString("CategoryName");

                                        txtstatus.setText(myOrderDatails.getOrder_status());
                                        txtprice.setText(packagePrice);
                                        txttranxid.setText("");
                                        txttrnxdate.setText("");
                                        txtpackagename.setText(name);
                                        txtcatgryname.setText(CategoryName);
                                        txtorderitemprice.setText(packagePrice);
                                        txtquantity.setText(packageQuantity);
                                        txtexamcount.setText(totalExamCount);
                                        txttotexam_count.setText(totalExamCount);
                                    }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
