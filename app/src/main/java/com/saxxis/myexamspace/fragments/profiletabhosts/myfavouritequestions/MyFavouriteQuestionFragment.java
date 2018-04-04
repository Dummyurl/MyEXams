package com.saxxis.myexamspace.fragments.profiletabhosts.myfavouritequestions;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.model.MyFavourites;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFavouriteQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFavouriteQuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private MyFavourites mParam1;
    private String questioncount;


    public MyFavouriteQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param questioncount Parameter 2.
     * @return A new instance of fragment MyFavouriteQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFavouriteQuestionFragment newInstance(MyFavourites param1, String questioncount) {
        MyFavouriteQuestionFragment fragment = new MyFavouriteQuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, questioncount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
            questioncount = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_favourite_question, container, false);

        WebView question_name = (WebView) view.findViewById(R.id.question_name);
        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.rg_ansgp);
        final RadioButton radioButtonOne = (RadioButton)view.findViewById(R.id.rb_ans_1);
        final RadioButton radioButtonTwo = (RadioButton)view.findViewById(R.id.rb_ans_2);
        final RadioButton radioButtonThree = (RadioButton)view.findViewById(R.id.rb_ans_3);
        final RadioButton radioButtonFour = (RadioButton)view.findViewById(R.id.rb_ans_4);
        WebView explanation_describe =(WebView)view.findViewById(R.id.explanation_describe);

        final RadioButton[] allRadioButtons = {radioButtonOne,radioButtonTwo,radioButtonThree,radioButtonFour};

        MyFavourites mdata = mParam1;
        question_name.setBackgroundColor(Color.parseColor("#d3f5ea"));
        question_name.loadDataWithBaseURL(AppConstants.SERVER_URL,StringEscapeUtils.unescapeHtml3(mdata.getQuestion()),"text/html","UTF-8",null);

        String[]  optionsarray = mdata.getData()!="null" ? mdata.getData().split("<answer id=") : new String[]{"","", "", "", "" , ""};

        radioButtonOne.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(optionsarray[1].substring(optionsarray[1].indexOf(">")+1))));
        radioButtonTwo.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(optionsarray[2].substring(optionsarray[2].indexOf(">")+1))));
        radioButtonThree.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(optionsarray[3].substring(optionsarray[3].indexOf(">")+1))));
        radioButtonFour.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(optionsarray[4].substring(optionsarray[4].indexOf(">")+1))));

        if (optionsarray.length>0) {
            for (int i = 1; i < optionsarray.length-1; i++) {
//            allRadioButtons[i-1].setButtonDrawable(android.R.drawable.screen_background_light_transparent);
                if (optionsarray[i].contains("correct") && optionsarray[i].contains("=\"true\"")) {
                    allRadioButtons[i - 1].setButtonDrawable(R.drawable.correct_ans);
                    allRadioButtons[i - 1].setPadding(22, 10, 22, 10);
                }
            }
        }

        if (mdata.getSolution().equals("")){
            explanation_describe.setVisibility(View.GONE);
        }

        if (!mdata.getSolution().equals("")){
            explanation_describe.setVisibility(View.VISIBLE);
            explanation_describe.loadDataWithBaseURL(AppConstants.SERVER_URL,StringEscapeUtils.unescapeHtml3(mdata.getSolution()),"text/html","UTF-8",null);
        }
        return view;
    }
}
