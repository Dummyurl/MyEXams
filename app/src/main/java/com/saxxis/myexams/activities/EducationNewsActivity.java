package com.saxxis.myexams.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.fragments.latestupdates.LatestUpdateLangFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EducationNewsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_educationnews)
    Toolbar educationToolbar;

    @BindView(R.id.latestupdatescontainer)
    ViewPager latestViewPager;

    @BindView(R.id.latestupdates_tabs)
    TabLayout tabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private AdView mAdView;
    /*Analytics tracker*/
    private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        ButterKnife.bind(this);
        setSupportActionBar(educationToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAdView = (AdView)findViewById(R.id.adbannerlatestupdate);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        /*
        * analytics tracker */
        mTracker = MyExamsApp.getMyInstance().getDefaultTracker();
        mTracker.setScreenName("Latest Updates");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(LatestUpdateLangFragment.newInstance(AppConstants.EducationNews+"&categoryid=222","English"),"English");
        mSectionsPagerAdapter.addFragment(LatestUpdateLangFragment.newInstance(AppConstants.EducationNews+"&categoryid=229","Telugu"),"Telugu");
        mSectionsPagerAdapter.addFragment(LatestUpdateLangFragment.newInstance(AppConstants.EducationNews+"&categoryid=230","Hindi"),"Hindi");

        latestViewPager.setAdapter(mSectionsPagerAdapter);
        latestViewPager.setOffscreenPageLimit(0);

        tabLayout.setupWithViewPager(latestViewPager);

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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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

