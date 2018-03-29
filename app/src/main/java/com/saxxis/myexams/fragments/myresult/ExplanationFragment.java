package com.saxxis.myexams.fragments.myresult;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.fragments.myresult.subexplanation.ExplanationQuestionAnsFragment;
import com.saxxis.myexams.fragments.myresult.subexplanation.SectionsMyExplanationFragment;
import com.saxxis.myexams.helper.UserPrefs;
import com.saxxis.myexams.helper.utils.CustomViewPager;
import com.saxxis.myexams.model.Questions;
import com.saxxis.myexams.model.QuizSections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplanationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplanationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String ticketId;
    private String mParam2;


    @BindView(R.id.tab_explayout)
    TabLayout tabLayout;

    @BindView(R.id.tab_sections)
    TabLayout tabSections;

    SectionPagerAdapter adapter;

    @BindView(R.id.exp_container)
    CustomViewPager mViewPager;




    private UserPrefs userPrefs;

    public static Bundle argsquestionno = new Bundle();

    public ExplanationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExplanationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExplanationFragment newInstance(String param1, String param2) {
        ExplanationFragment fragment = new ExplanationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPrefs = new UserPrefs(getActivity());
        if (getArguments() != null) {
            ticketId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explanation, container, false);
        ButterKnife.bind(this,view);

//        AppHelper.showDialog(getActivity(),"Loading Please Wait...");
        Log.e("response",AppConstants.EXPLANATION_EXAM_RESULT+ticketId+"&userid="+userPrefs.getUserId());
        StringRequest stringRequest = new StringRequest(AppConstants.EXPLANATION_EXAM_RESULT+ticketId+"&userid="+userPrefs.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        AppHelper.hideDialog();
                        Log.e("response",response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")) {
                                JSONObject dataArray = jsonObject.getJSONObject("data");
                                JSONArray quizInfo =  dataArray.getJSONArray("quizinfo");
                                JSONArray sectionsArrayObj = dataArray.optJSONArray("sections");
                                ArrayList<Questions> quesansData = new ArrayList<>();

                                for (int i = 0; i < quizInfo.length(); i++) {
                                    JSONObject dataObject  = quizInfo.getJSONObject(i);
                                    String QuestionId = null;
                                    if(dataObject.has("StatisticsId")) QuestionId=dataObject.getString("StatisticsId");
                                    String question = dataObject.getString("Question");
                                    String QuestionIndex = dataObject.getString("QuestionIndex");
                                    String Solution =  dataObject.getString("Solution");
                                    String fevorite = dataObject.getString("fevorite");
                                    String description = "";
                                    if(dataObject.has("Description")) {
                                        description = dataObject.getString("Description");
                                    }
                                    String QuestionVersionId = dataObject.getString("QuestionVersionId");
                                    String[] answers = dataObject.getString("options").split("<answer id=");
                                    String Userdata = dataObject.getString("Userdata");
                                    quesansData.add(new Questions(QuestionId,question,QuestionIndex,Solution,QuestionVersionId,fevorite,description,answers, Userdata));
                                }
                                adapter = new SectionPagerAdapter(getChildFragmentManager());
                                tabLayout.setupWithViewPager(mViewPager);

                                if (sectionsArrayObj!=null){
                                    ArrayList<QuizSections> sectionsData = new ArrayList<>();

                                    for (int i = 0; i < sectionsArrayObj.length(); i++) {
                                        JSONObject sectionObject =  sectionsArrayObj.getJSONObject(i);
                                        sectionsData.add(new QuizSections(sectionObject.getString("id"),
                                                sectionObject.getString("quizid"),
                                                sectionObject.getString("time"),
                                                sectionObject.getString("marks"),
                                                sectionObject.getString("qcount"),
                                                sectionObject.getString("qstart"),
                                                sectionObject.getString("title"),
                                                sectionObject.getString("qtime"),
                                                sectionObject.getString("cscore"),
                                                sectionObject.getString("wscore"),
                                                sectionObject.getString("cutoffmarks")));
                                    }
                                    for (int i = 0; i < sectionsData.size(); i++) {
                                        mViewPager.setPagingEnabled(false);
                                        //  quesansData.subList(Integer.parseInt(sectionsData.get(i).getQstart()),Integer.parseInt(sectionsData.get(i).getQcount()));

                                        adapter.addFragment(SectionsMyExplanationFragment.newInstance(
                                                quesansData,sectionsData.get(i).getQstart(),
                                                sectionsData.get(i).getQcount()),
                                                sectionsData.get(i).getTitle());
                                    }
                                }

                                if (sectionsArrayObj==null) {
                                    for (int i = 0; i < quesansData.size(); i++) {
                                        adapter.addFragment(ExplanationQuestionAnsFragment.newInstance(quesansData.get(i), ""), i + 1 + "");
                                    }
                                }
//                              mQuestionSectPagerAdapter = new QuestionSectPagerAdapter(getChildFragmentManager(),quesansData);
                                mViewPager.setAdapter(adapter);
                            }
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
