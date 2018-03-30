package com.saxxis.myexams.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.saxxis.myexams.R;
import com.saxxis.myexams.activities.exampapers.ExamInstructsActivity;
import com.saxxis.myexams.helper.utils.CustomViewPager;
import com.saxxis.myexams.model.Questions;
import com.saxxis.myexams.model.QuizSections;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SectionExamInstructFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionExamInstructFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    @BindView(R.id.tab_section_title)
    TabLayout tabSectionLayout;
    @BindView(R.id.exp_sec_exam_container)
    CustomViewPager custViewPager;

    SectionPagerAdapter adapter;

    // TODO: Rename and change types of parameters
    private ArrayList<Questions> mParam1;
    private QuizSections mParam2;
    String StatisticsInfoId, ticketId;

    private int tabIndex = 0;
    private int currentTabIndex = 0;
    private int sectionIndex = 0;
    private boolean isPageSelected = false;

    public SectionExamInstructFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SectionExamInstructFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SectionExamInstructFragment newInstance(ArrayList<Questions> param1, QuizSections param2, String StatisticsInfoId, String ticketId, int sectionIndex) {
        SectionExamInstructFragment fragment = new SectionExamInstructFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, StatisticsInfoId);
        args.putString(ARG_PARAM4, ticketId);
        args.putInt(ARG_PARAM5, sectionIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getParcelable(ARG_PARAM2);
            StatisticsInfoId = getArguments().getString(ARG_PARAM3);
            ticketId = getArguments().getString(ARG_PARAM4);
            sectionIndex = getArguments().getInt(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section_exam_instruct, container, false);
        ButterKnife.bind(this,view);

        int startIndex = Integer.parseInt(mParam2.getQstart());
        int stopIndex = Integer.parseInt(mParam2.getQcount());

        adapter = new SectionPagerAdapter(getChildFragmentManager());
        for (int i = startIndex; i < startIndex + stopIndex; i++) {
            int i2 = i + 1;
            //tabIndex = (i == startIndex + stopIndex - 1) ? 0 : tabIndex + 1;
            tabIndex = tabIndex + 1;
            adapter.addFragment(ExamInstructFragment.newInstance(mParam1.get(i), "Question "+i2+" of "+mParam1.size(),mParam2.getCscore(),mParam2.getWscore(),tabIndex, StatisticsInfoId, ticketId), i2 + "");
        }

        tabSectionLayout.setupWithViewPager(custViewPager);
        custViewPager.setAdapter(adapter);
        custViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //System.out.println(position+"===============================onPageScrolled======================================="+positionOffset+":::"+currentTabIndex);
                if(positionOffset == 0) {
                    currentTabIndex = position;
                }
            }

            @Override
            public void onPageSelected(int position) {
                //System.out.println("===============================onPageSelected======================================="+":::"+currentTabIndex);
                currentTabIndex = position;
                isPageSelected = true;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //System.out.println("===============================onPageScrollStateChanged======================================="+state+":::"+currentTabIndex);
                if(state == 0 && (tabIndex - 1) == currentTabIndex) {
                    if(!isPageSelected) {
                        ((ExamInstructsActivity)getActivity()).showNextQuestion(sectionIndex+1);
                    } else {
                        isPageSelected = false;
                    }

                }
            }
        });
        return view;
    }
    public void showNextQuestion(int index) {
        custViewPager.setCurrentItem(index);
    }
    public void markTabForReview(int index) {
        TabLayout.Tab currentTab = tabSectionLayout.getTabAt(index-1);
        LinearLayout customView = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.exam_tab_mark, null);

        currentTab.setCustomView(customView);
        currentTab.setText(currentTab.getText());
        //currentTab.setIcon(R.drawable.correct_ans);
//        TextView tv = new TextView(getContext());
//        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        tv.setBackgroundResource(R.drawable.exam_mark_tab);
//        tv.setText(Integer.toString(index));
        //tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot, 0);
//        currentTab.setCustomView(tv);
//        currentTab.setIcon(R.drawable.correct_ans);
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
