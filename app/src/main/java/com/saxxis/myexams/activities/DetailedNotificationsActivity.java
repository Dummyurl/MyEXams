package com.saxxis.myexams.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.model.CurrentaffairsList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedNotificationsActivity extends AppCompatActivity {

    @BindView(R.id.detl_notifi_title)
    TextView txtTitle;

    @BindView(R.id.detl_notifi_description)
    WebView txtDescription;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private AdView mAdView;

    /*Analytics tracker*/
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_notifications);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView)findViewById(R.id.adbannerView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);



        getSupportActionBar().setTitle(getIntent().getExtras().getString("detailtitle"));
//      getSupportActionBar().setHomeButtonEnabled(true);

        CurrentaffairsList mData = getIntent().getExtras().getParcelable("detaildata");
        txtTitle.setText(AppHelper.fromHtml(mData.getTitle()));
        txtDescription.getSettings().setDefaultTextEncodingName("utf-8");
        txtDescription.getSettings().setJavaScriptEnabled(true);
        txtDescription.getSettings().setMinimumLogicalFontSize(16);
        txtDescription.getSettings().setAllowContentAccess(true);
        //  Log.d("response",mData.getDescription().toString());

        /*
        * analytics tracker */
        mTracker = MyExamsApp.getMyInstance().getDefaultTracker();
        mTracker.setScreenName("Active Notifications");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        txtDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,mData.getDescription(),"text/html; charset=utf-8","UTF-8",null);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_homeitem,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
//            startActivity(new Intent(DetailedNotificationsActivity.this,NotificationFilter.class));
            finish();
            return true;
        }

        if (id==R.id.action_home){
            startActivity(new Intent(DetailedNotificationsActivity.this,MainActivity.class));
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
