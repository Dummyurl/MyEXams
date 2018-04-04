package com.saxxis.myexamspace.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.fragments.CurrentAffairsLangFragment;

import java.util.ArrayList;
import java.util.List;

import static com.saxxis.myexamspace.R.id.container;

public class CurrentAffairsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_current_affairs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        mViewPager = (ViewPager) findViewById(container);
        mSectionsPagerAdapter.addFragment(CurrentAffairsLangFragment.newInstance(AppConstants.CURRENT_AFFAIRS+"engaffairs","English"),"English");
        mSectionsPagerAdapter.addFragment(CurrentAffairsLangFragment.newInstance(AppConstants.CURRENT_AFFAIRS+"affairs","Telugu"),"Telugu");
        mSectionsPagerAdapter.addFragment(CurrentAffairsLangFragment.newInstance(AppConstants.CURRENT_AFFAIRS+"hindiaffairs","Hindi"),"Hindi");
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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
    * A placeholder fragment containing a simple view.
    *//*
    public static class PlaceholderFragment extends Fragment {

        */
/**
         * The fragment argument representing the section number for this
         * fragment.
         *//*

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {

        }

        */
/**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            String url="";
            switch (sectionNumber){
                case 1:
                    url=AppConstants.CURRENT_AFFAIRS+"engaffairs";
                    break;
                case 2:
                    url=AppConstants.CURRENT_AFFAIRS+"affairs";
                    break;
                case 3:
                url=AppConstants.CURRENT_AFFAIRS+"hindiaffairs";
                    break;
            }
            args.putString("url",url);
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        @BindView(R.id.rv_currentaffairs)
        RecyclerView recyclerView;

        ProgressDialog mDialog;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_current_affairs, container, false);
            ButterKnife.bind(this,rootView);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new RecvDecors(getActivity(),R.dimen.job_listing_main_offset));

            replaceFragment(getArguments().getString("url"));
            return rootView;
        }

        private void replaceFragment(String url) {

            Log.e("response",url);
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage("Loading Please Wait...");
            mDialog.setIndeterminate(true);
            mDialog.setCancelable(true);
            mDialog.show();

            StringRequest stringRequest = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                mDialog.dismiss();

                                Log.d("response",response);
                                JSONObject jsonObject=new JSONObject(response);
                                JSONArray jsonArray=jsonObject.getJSONArray("data");
                                final ArrayList<CurrentaffairsList> currAffairsArraylist=new ArrayList<CurrentaffairsList>();
                                CurrentaffairsList objCurrentaffairsList;
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject nameobject=jsonArray.getJSONObject(i);
                                    String name=nameobject.getString("title");
                                    String id=nameobject.getString("id");
                                    String description=nameobject.getString("introtext");
                                    String createddate=nameobject.getString("created");

                                    objCurrentaffairsList=new CurrentaffairsList(name,description,id,createddate);
                                    currAffairsArraylist.add(objCurrentaffairsList);
//                                  String id=nameobject.getString("parent_id");

                                }

                                recyclerView.setAdapter(new CurrentAffairsAdapter(getActivity(),currAffairsArraylist));
                                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                    @Override
                                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                        Intent intent=new Intent(getActivity(),CAffDetailActivity.class);
                                        intent.putExtra("detaildata",currAffairsArraylist.get(position));
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }
                                });

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
*/

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
