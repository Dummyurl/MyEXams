package com.saxxis.myexams.fragments.examdescribe;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.fragments.examdescribe.syllabus.SubSyllabusFragment;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.utils.CustomViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SyllabusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SyllabusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.syllabus_view)
    WebView webview;

    @BindView(R.id.syllabustab)
    LinearLayout syllabusTAbLayout;

    TabLayout tabLayout;
    CustomViewPager mViewPager;
    SectionsPagerAdapter section_adapter;


    public SyllabusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SyllabusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SyllabusFragment newInstance(String param1, String param2) {
        SyllabusFragment fragment = new SyllabusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_syllabus, container, false);
        ButterKnife.bind(this,rootView);
        mViewPager = (CustomViewPager) rootView.findViewById(R.id.syllabus_container);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_syllabuslayout);

        if(!mParam1.contains("{tab ")){
            syllabusTAbLayout.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);

            webview.getSettings().setDefaultTextEncodingName("utf-8");
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setMinimumLogicalFontSize(19);
            webview.getSettings().setAllowContentAccess(true);
            webview.loadDataWithBaseURL(AppConstants.SERVER_URL, mParam1, "text/html; charset=utf-8", "UTF-8", null);
        }

        if(mParam1.contains("{tab ")){
            tabLayout.setVisibility(View.VISIBLE);
            syllabusTAbLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            webview.setVisibility(View.GONE);
            tabLayout.setupWithViewPager(mViewPager);
            mViewPager.setPagingEnabled(false);
            section_adapter =new SectionsPagerAdapter(getChildFragmentManager());
            try {
                JSONObject param2Objext = new JSONObject(mParam2);
                int count =param2Objext.getInt("count");
                for (int i = 1; i <= count; i++) {
                    JSONObject itemObject = param2Objext.getJSONObject(String.valueOf(i));
                    section_adapter.addFragment(new SubSyllabusFragment().newInstance(itemObject.getString("content"),""),
                            AppHelper.fromHtml(itemObject.getString("title")).toString());
                }
                mViewPager.setAdapter(section_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rootView;
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

        public void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
