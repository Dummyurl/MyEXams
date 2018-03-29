package com.saxxis.myexams.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.adapters.NotificationsListAdapter;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.fragments.notifications.LatestNotificationsFrag;
import com.saxxis.myexams.fragments.notifications.UpComingNotifiFrag;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.NotifiDataResult;
import com.saxxis.myexams.parsers.NotificationParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.saxxis.myexams.R.id.container;

public class NotificationActivity extends AppCompatActivity {


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(container);
        setupViewPager(mViewPager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(LatestNotificationsFrag.newInstance(), "Active");
        adapter.addFragment(UpComingNotifiFrag.newInstance(), "Upcoming");
        viewPager.setAdapter(adapter);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static final String ARG_URL_CLASFI_NOTIFICAT="urlforlatrupcom";

        ArrayList<NotifiDataResult> notifcadata;;

        @BindView(R.id.recv_notifications)
        RecyclerView objRecvNotifi;


        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER , sectionNumber);
            if (sectionNumber==1){
                args.putString(ARG_URL_CLASFI_NOTIFICAT,AppConstants.NOTIFICATIONS_URL);
            }
            if (sectionNumber==2){
                args.putString(ARG_URL_CLASFI_NOTIFICAT,AppConstants.UPCOMING_NOTIFICATIONS_URL);
            }
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            ButterKnife.bind(this,rootView);

            objRecvNotifi.setHasFixedSize(true);
            objRecvNotifi.setLayoutManager(new LinearLayoutManager(getActivity()));
            objRecvNotifi.addItemDecoration(new RecvDecors(getActivity(),R.dimen.job_listing_main_offset));

//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            setrecvview(getArguments().getString(ARG_URL_CLASFI_NOTIFICAT));

            return rootView;
        }

        private void setrecvview(String url) {
            Log.d("response",url);

           final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                   progressDialog.setMessage("Loading Please Wait..."); ;
                   progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                   progressDialog.setIndeterminate(true);
                   progressDialog.show();

        StringRequest stringRequest=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    progressDialog.dismiss();
                        try {
                            JSONObject objResponse = new JSONObject(response);

                                if (objResponse.getString("status").equals("ok")){
                                    notifcadata= NotificationParser.getmInstance(response).getData();
                                    objRecvNotifi.setAdapter(new NotificationsListAdapter(getActivity(),notifcadata));
                                }else {
                                    new AlertDialog.Builder(getActivity())
                                            .setMessage("No Data Found in this Category")
                                            .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    getActivity().finish();
                                                }
                                            })
                                            .setCancelable(false)
                                            .create();
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
}
