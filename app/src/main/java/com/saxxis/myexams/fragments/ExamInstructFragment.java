package com.saxxis.myexams.fragments;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.activities.exampapers.ExamInstructsActivity;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.model.Questions;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamInstructFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamInstructFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";

    // TODO: Rename and change types of parameters
    private Questions question;
    private String mParam2;
    private String cAns;
    private String wAns;

    RadioGroup rgAnsGrp;
    RadioButton radioButtonOne;
    RadioButton radioButtonTwo;
    RadioButton radioButtonThree;
    RadioButton radioButtonFour;
    ArrayList<RadioButton> listOfRadioButtons = new ArrayList<RadioButton>();

    TextView markForReview,saveAndNext,clear_response,skip;

    public int fragmentIndex;
    public String statisticsInfoId, ticketId;

    public ExamInstructFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param quizData Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExamInstructFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamInstructFragment newInstance(Questions quizData, String param2,String cAns,String wrongAns,int index, String statisticsInfoId, String ticketId) {
        ExamInstructFragment fragment = new ExamInstructFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, quizData);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3,cAns);
        args.putString(ARG_PARAM4,wrongAns);
        args.putInt(ARG_PARAM5,index);
        args.putString(ARG_PARAM6,statisticsInfoId);
        args.putString(ARG_PARAM7,ticketId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            cAns = getArguments().getString(ARG_PARAM3);
            wAns = getArguments().getString(ARG_PARAM4);
            fragmentIndex = getArguments().getInt(ARG_PARAM5);
            statisticsInfoId = getArguments().getString(ARG_PARAM6);
            ticketId = getArguments().getString(ARG_PARAM7);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exam_instruct, container, false);
        WebView question_name = (WebView) rootView.findViewById(R.id.question_name);
        WebView passageText = (WebView) rootView.findViewById(R.id.passageText);
        rgAnsGrp = (RadioGroup)rootView.findViewById(R.id.rg_ansgp);

//        radioButtonOne = (RadioButton)rootView.findViewById(R.id.rb_ans_1);
//        radioButtonTwo = (RadioButton)rootView.findViewById(R.id.rb_ans_2);
//        radioButtonThree = (RadioButton)rootView.findViewById(R.id.rb_ans_3);
//        radioButtonFour = (RadioButton)rootView.findViewById(R.id.rb_ans_4);

        TextView txtQuestionNumber = (TextView)rootView.findViewById(R.id.question_number);
        TextView txtCorrScore = (TextView)rootView.findViewById(R.id.corrscore);
        TextView txtWrongScore = (TextView)rootView.findViewById(R.id.wscore);
        txtQuestionNumber.setText(mParam2);
        txtCorrScore.setText(cAns);
        txtWrongScore.setText(wAns);

        /*
        * buttontextviews*/
        markForReview = (TextView)rootView.findViewById(R.id.markForReview);
        saveAndNext = (TextView)rootView.findViewById(R.id.saveAndNext);
        clear_response = (TextView)rootView.findViewById(R.id.clear);
        skip = (TextView)rootView.findViewById(R.id.skip);

        Questions mdata = question;//getArguments().getParcelable("txtone");

        mdata.setAnswerTick("1");

        if(mdata.getDescription() != "null" && mdata.getDescription().length() > 0) {
            passageText.setVisibility(View.VISIBLE);
            passageText.loadDataWithBaseURL(AppConstants.SERVER_URL,mdata.getDescription(),"text/html","UTF-8",null);
            passageText.getSettings().setJavaScriptEnabled(true);
        }
        question_name.setBackgroundColor(Color.parseColor("#d3f5ea"));
        String questionTxt = mdata.getQuestion();
        //System.out.println(questionTxt);
        /*String modifiedQuestionTxt = "";
        if(questionTxt.contains(".png") && questionTxt.contains("{")) {
            System.out.println(questionTxt);
            String[] questionArr = questionTxt.split("\\{");
            String imgTxt="";
            for(int index=0; index < questionArr.length; index++) {
                System.out.println(questionArr[index]);
                if(questionArr[index].contains(".png")) {
                    imgTxt = "http://myexamspace.com/"+questionArr[index].substring(0, questionArr[index].indexOf("}"));
                    modifiedQuestionTxt += imgTxt;
                    System.out.println(imgTxt+":::::imgTxt");
                } else {
                    modifiedQuestionTxt += questionArr[index];
                }
            }
            System.out.println(modifiedQuestionTxt+":::::modifiedQuestionTxt");
        } else {
            modifiedQuestionTxt = questionTxt;
        }*/
        question_name.loadDataWithBaseURL(AppConstants.SERVER_URL,questionTxt,"text/html","UTF-8",null);
        question_name.getSettings().setJavaScriptEnabled(true);

        for (int i = 1; i < mdata.getAnswers().length; i++) {
            String ansStr = mdata.getAnswers()[i];
            String answerAttr = AppHelper.fromHtml(ansStr.substring(0, ansStr.indexOf(">"))).toString();
            String isCorrectAns = (answerAttr.indexOf("correct=") > -1) ? "true" : "false";
            String answerId = answerAttr.split("\"")[1];
            RadioButton rdbtn = new RadioButton(getActivity());
            rdbtn.setId(i);
            rdbtn.setTag(answerId+","+isCorrectAns);
            System.out.println(ansStr.substring(ansStr.indexOf(">")+1));
            if(ansStr.contains("<img")) {
                WebView webView = new WebView(getActivity());
                webView.loadData(ansStr.substring(ansStr.indexOf(">")+1), null, null);
                webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                rdbtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                LinearLayout ll = new LinearLayout(getActivity());
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(rdbtn);
                ll.addView(webView);
                rgAnsGrp.addView(ll);
                //rgAnsGrp.addView(webView);
            } else {
                rdbtn.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3("<p>"+ansStr.substring(ansStr.indexOf(">")+1)+"</p>")));
                rdbtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                rgAnsGrp.addView(rdbtn);
            }


            //Log.e("answerid", rdbtn.getTag().toString());
            listOfRadioButtons.add((RadioButton)rdbtn);
        }
//        radioButtonOne.setText(AppHelper.fromHtml(mdata.getAnswers()[1].substring(mdata.getAnswers()[1].indexOf(">")+1)));
//        radioButtonTwo.setText(AppHelper.fromHtml(mdata.getAnswers()[2].substring(mdata.getAnswers()[2].indexOf(">")+1)));
//        radioButtonThree.setText(AppHelper.fromHtml(mdata.getAnswers()[3].substring(mdata.getAnswers()[3].indexOf(">")+1)));
//        radioButtonFour.setText(AppHelper.fromHtml(mdata.getAnswers()[4].substring(mdata.getAnswers()[4].indexOf(">")+1)));

        markForReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getParentFragment() != null) {
                    ((SectionExamInstructFragment)getParentFragment()).markTabForReview(fragmentIndex);
                } else {
                    ((ExamInstructsActivity)getActivity()).markTabForReview(fragmentIndex);
                }
                markForReview.setEnabled(false);
            }
        });
//    /*
//      *below is for next fragment select in viewpager
//      */
//  if(quizzviewpager.getCurrentItem()<qiData.size()-1){
//      tabLayout.getTabAt(quizzviewpager.getCurrentItem()+1).select();
//  }

//    /*
//    * below is for precious fragment select
//     */
////
//    if(quizzviewpager.getCurrentItem()!=0){
//        tabLayout.getTabAt(quizzviewpager.getCurrentItem()-1).select();
//    }
        saveAndNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuestion();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getParentFragment() != null) {
                    ((SectionExamInstructFragment)getParentFragment()).showNextQuestion(fragmentIndex);
                } else {
                    ((ExamInstructsActivity)getActivity()).showNextQuestion(fragmentIndex);
                }
            }
        });

        clear_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<listOfRadioButtons.size();i++) {
                    RadioButton rb = listOfRadioButtons.get(i);
                    rb.setChecked(false);
                }
//                radioButtonOne.setChecked(false);
//                radioButtonTwo.setChecked(false);
//                radioButtonThree.setChecked(false);
//                radioButtonFour.setChecked(false);
            }
        });
        GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {

                final int SWIPE_MIN_DISTANCE = 120;
                final int SWIPE_MAX_OFF_PATH = 250;
                final int SWIPE_THRESHOLD_VELOCITY = 200;
                try {
                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                        return false;
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        Log.i("ScrollLog", "Right to Left");
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        Log.i("ScrollLog", "Left to Right");
                    }
                } catch (Exception e) {
                    // nothing
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        };
        final GestureDetector gesture = new GestureDetector(getActivity(), simpleOnGestureListener);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        return rootView;
    }

    private void submitQuestion() {
        int rdAnswerId = rgAnsGrp.getCheckedRadioButtonId();
        if(rdAnswerId == -1) {
            AppHelper.showToast("Please select an answer.");
        } else {
            RadioButton rdCheckedAns = (RadioButton)getActivity().findViewById(rdAnswerId);
            String requestStr = question.getQuestionId()+
                    "&staticInfoId="+ statisticsInfoId+
                    "&ticketid="+ ticketId+
                    "&useranswer="+rdCheckedAns.getTag().toString().split(",")[0]+
                    "&cscore="+cAns+
                    "&wscore="+wAns+
                    "&correctanswer="+ rdCheckedAns.getTag().toString().split(",")[1];
            String URL = AppConstants.SUBMITANSWER+requestStr;
            Log.e("Response", URL);
            AppHelper.showDialog(getActivity(),"Loading Please Wait...");
            StringRequest stringRequest = new StringRequest(URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response);
                            AppHelper.hideDialog();
                            if(getParentFragment() != null) {
                                ((SectionExamInstructFragment)getParentFragment()).showNextQuestion(fragmentIndex);
                            } else {
                                ((ExamInstructsActivity)getActivity()).showNextQuestion(fragmentIndex);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
        }
    }
    /*private void submitTotalExam() {
        AppHelper.showDialog(getActivity(),"Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(AppConstants.TOTAL_EXAM_SUBMIT+"&userid=",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        getActivity().finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }*/

}
