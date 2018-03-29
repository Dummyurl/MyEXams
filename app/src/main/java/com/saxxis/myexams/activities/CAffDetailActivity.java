package com.saxxis.myexams.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.saxxis.myexams.R;
import com.saxxis.myexams.fragments.CurrentAffaDetailedFragment;
import com.saxxis.myexams.helper.utils.CustomViewPager;
import com.saxxis.myexams.model.CurrentaffairsList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CAffDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.custom_pager_details)
    CustomViewPager customPagerDetails;

    CDetailsPagerAdapter adapter;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caff_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        String arl=getIntent().getExtras().getString("detailtitle");
//        ArrayList<CurrentaffairsList> alldata = getIntent().getExtras().getParcelableArrayList("alldetails");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(arl);
        getSupportActionBar().setTitle(arl);
        mAdView = (AdView)findViewById(R.id.adbannerView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        CurrentaffairsList mData = getIntent().getExtras().getParcelable("detaildata");
        adapter = new CDetailsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CurrentAffaDetailedFragment.newInstance(mData.getTitle(),mData.getDescription()),"");

//        for (int i = 0; i < alldata.size(); i++) {
//            adapter.addFragment(CurrentAffaDetailedFragment.newInstance(alldata.get(i).getTitle(),alldata.get(i).getDescription()),"");
//        }
        customPagerDetails.setAdapter(adapter);
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
            String comefrom = getIntent().getExtras().getString("comefrom");
            if (comefrom.equals("home")) {
                Intent intent = new Intent(CAffDetailActivity.this, CAInEnglishActivity.class);
                intent.putExtra("tabSelection","1");
                startActivity(intent);
                finish();
            }
            if (comefrom.equals("host")) {
                finish();
            }
            return true;
        }

        if (id==R.id.action_home){
            startActivity(new Intent(CAffDetailActivity.this,MainActivity.class));
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class CDetailsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public CDetailsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
