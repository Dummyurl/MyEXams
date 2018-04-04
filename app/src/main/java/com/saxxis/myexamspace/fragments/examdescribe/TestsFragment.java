package com.saxxis.myexamspace.fragments.examdescribe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.adapters.QuizzesListAdapter;
import com.saxxis.myexamspace.helper.utils.RecvDecors;
import com.saxxis.myexamspace.model.QuizzListList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.recv_quizzTests)
    RecyclerView recv_TestsFrag;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TestsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestsFragment newInstance(String param1, String param2) {
        TestsFragment fragment = new TestsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_tests, container, false);
        ButterKnife.bind(this,rootView);

        recv_TestsFrag.setHasFixedSize(true);
        recv_TestsFrag.setLayoutManager(new LinearLayoutManager(getActivity()));
        recv_TestsFrag.addItemDecoration(new RecvDecors(getActivity(), R.dimen.job_listing_main_offset));
        try {
            JSONArray jsonObject = new JSONArray(mParam1);
            ArrayList<QuizzListList> quizmodel=new ArrayList<>();
            for (int i=0;i<jsonObject.length();i++){
                JSONObject quizObject=jsonObject.getJSONObject(i);
                quizmodel.add(new QuizzListList(quizObject.getString("SubjectId"),
                        quizObject.getString("SubjectName"),
                        quizObject.getString("QuizId"),
                        quizObject.getString("QuizName"),
                        quizObject.getString("QuestionCount"),
                        quizObject.getString("ExamLanguage"),
                        quizObject.getString("TestExamType"),
                        quizObject.getString("TotalExamMarks"),
                        quizObject.getString("TotalExamTime"),
                        quizObject.getString("version")));

            }
            recv_TestsFrag.setAdapter(new QuizzesListAdapter(getActivity(), quizmodel));
        }catch (JSONException jse){
        }
        return rootView;
    }

}
