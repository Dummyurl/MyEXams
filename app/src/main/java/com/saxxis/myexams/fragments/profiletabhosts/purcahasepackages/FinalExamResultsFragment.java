package com.saxxis.myexams.fragments.profiletabhosts.purcahasepackages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexams.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinalExamResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalExamResultsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    @BindView(R.id.total_questions)TextView txttotalQuestions;
    @BindView(R.id.attempt_exams)TextView txtattemptExams;
    @BindView(R.id.unattempt_exams)TextView txtunattemptExams;

    public FinalExamResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinalExamResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinalExamResultsFragment newInstance(String param1, String param2, String param3) {
        FinalExamResultsFragment fragment = new FinalExamResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_exam_results, container, false);
        ButterKnife.bind(this,view);
        txttotalQuestions.setText(mParam1=="null"?"0":mParam1);
        txtattemptExams.setText(mParam2=="null"?"0":mParam2);
        txtunattemptExams.setText(mParam3=="null"?"0":mParam3);
        return view;
    }
}
