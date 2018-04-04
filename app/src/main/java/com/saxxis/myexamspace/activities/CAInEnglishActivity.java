package com.saxxis.myexamspace.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.fragments.CurrentAffairsLangFragment;
import com.saxxis.myexamspace.fragments.MustReadFragment;
import com.saxxis.myexamspace.fragments.latestupdates.LatestUpdateLangFragment;

import java.util.ArrayList;
import java.util.List;

public class CAInEnglishActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /*Analytics tracker*/
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cain_english);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extrass = getIntent().getExtras();
        /*
        * analytics tracker
        * */
        mTracker = MyExamsApp.getMyInstance().getDefaultTracker();
        mTracker.setScreenName("Current Affairs");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter.addFragment(CurrentAffairsLangFragment.newInstance(AppConstants.CURRENT_AFFAIRS+"engaffairs","English"),"Articles");
        mSectionsPagerAdapter.addFragment(LatestUpdateLangFragment.newInstance(AppConstants.EducationNews+"&categoryid=222","English"),"Latest Updates");
        mSectionsPagerAdapter.addFragment(MustReadFragment.newInstance(AppConstants.CA_MUST_READ+"300","English"),"Must Read");
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if (extrass!=null){
            String tabselection=extrass.getString("tabSelection");
            if (tabselection.equals("1")){
                tabLayout.getTabAt(0).select();
            }
            if (tabselection.equals("2")){
                tabLayout.getTabAt(1).select();
            }
            if (tabselection.equals("3")){
                tabLayout.getTabAt(2).select();
            }
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
//            switch (position) {
//                case 0:
//                    return "Latest";
//                case 1:
//                    return "Upcoming";
//            }
//            return null;
        }
    }
}
