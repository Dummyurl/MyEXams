package com.saxxis.myexams.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.fragments.examdescribe.TestsFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MyPurchasedExamsActivity extends AppCompatActivity {

    LinearLayout purchasedexams;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar_purchase;
    TextView titletext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchased_exams);
        toolbar_purchase=(Toolbar)findViewById(R.id.toolbar_purchase);
        purchasedexams = (LinearLayout)findViewById(R.id.purchased_exmas_list);
        titletext = (TextView)findViewById(R.id.titletext);

        setSupportActionBar(toolbar_purchase);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            titletext.setText(extras.getString("subjecttitle"));
            Log.e("response",extras.getString("subjecturl"));
            StringRequest stringRequest = new StringRequest(extras.getString("subjecturl"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.e("response",s);
                            try {
                                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.purchased_exmas_list,
                                        TestsFragment.newInstance(new JSONObject(s).getJSONArray("data").toString(), "")).commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
             super.onBackPressed();
             return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
