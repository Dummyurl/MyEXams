package com.saxxis.myexams.fragments.myresult.subexplanation;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saxxis.myexams.R;
import com.saxxis.myexams.helper.utils.CustomViewPager;
import com.saxxis.myexams.model.Questions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SectionsMyExplanationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionsMyExplanationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "startindex";
    private static final String ARG_PARAM3 = "stopindex";

    // TODO: Rename and change types of parameters
    private  ArrayList<Questions> quesansData;
    private Integer startIndex;
    private Integer stopIndex;


    SectionPagerAdapter adapter;

    @BindView(R.id.tab_sectionnumber)TabLayout tabSectionLayout;
    @BindView(R.id.exp_sec_container)CustomViewPager custViewPager;

    public SectionsMyExplanationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param quesansData Parameter 1.
     * @param startinde Parameter 2.
     * @param stopindex Parameter 3.
     * @return A new instance of fragment SectionsMyExplanationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SectionsMyExplanationFragment newInstance(ArrayList<Questions> quesansData, String startinde,String stopindex) {
        SectionsMyExplanationFragment fragment = new SectionsMyExplanationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, quesansData);
        args.putInt(ARG_PARAM2, Integer.parseInt(startinde));
        args.putInt(ARG_PARAM3, Integer.parseInt(stopindex));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quesansData = getArguments().getParcelableArrayList(ARG_PARAM1);
            startIndex = getArguments().getInt(ARG_PARAM2);
            stopIndex = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sections_my_explanation, container, false);
        ButterKnife.bind(this,view);

        adapter = new SectionPagerAdapter(getChildFragmentManager());
        for (int i = startIndex; i < startIndex + stopIndex; i++) {
            adapter.addFragment(ExplanationQuestionAnsFragment.newInstance(quesansData.get(i), ""), i + 1 + "");
        }

        tabSectionLayout.setupWithViewPager(custViewPager);
        custViewPager.setAdapter(adapter);
        return view;
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionPagerAdapter(FragmentManager fm){
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
