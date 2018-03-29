package com.saxxis.myexams.activities.examsresults;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.fragments.myresult.ExplanationFragment;
import com.saxxis.myexams.fragments.myresult.MyResultAnalysisFragment;
import com.saxxis.myexams.fragments.myresult.ResultViewTabFragment;
import com.saxxis.myexams.helper.utils.CustomViewPager;
import com.saxxis.myexams.model.MyResultsList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyResultsActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cusviewpager_myresult)
    CustomViewPager cusviewpager_myresult;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.product_details_title)
    TextView toolbarTitle;

    TabLayout tabLayout;


    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_results);
        ButterKnife.bind(this);
        Bundle extrass = getIntent().getExtras();
        setSupportActionBar(toolbar);
        toolbarTitle.setText(extrass.getString("Quizname"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout)findViewById(R.id.tab_myexamresult);
        MyResultsList resultlist = getIntent().getExtras().getParcelable("ticketId");

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(ResultViewTabFragment.newInstance(resultlist,""),"Result");
        mSectionsPagerAdapter.addFragment(MyResultAnalysisFragment.newInstance(resultlist.getTicketId(),""),"Analysis");
        mSectionsPagerAdapter.addFragment(ExplanationFragment.newInstance(resultlist.getTicketId(),""),"Explanation");

        cusviewpager_myresult.setAdapter(mSectionsPagerAdapter);
//        cusviewpager_myresult.setOffscreenPageLimit(0);

        tabLayout.setupWithViewPager(cusviewpager_myresult);
        cusviewpager_myresult.setPagingEnabled(false);

        if (extrass!=null){
            String tabselection=extrass.getString("tabselection");
            if (tabselection.equals("viewresult")){
                tabLayout.getTabAt(0).select();
            }
            if (tabselection.equals("analysis")){
                tabLayout.getTabAt(1).select();
            }
            if (tabselection.equals("explanation")){
                tabLayout.getTabAt(2).select();
            }
        }
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
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
