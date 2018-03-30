package com.saxxis.myexams.fragments.myresult.subexplanation;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.UserPrefs;
import com.saxxis.myexams.model.Questions;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplanationQuestionAnsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplanationQuestionAnsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Questions mParam1;
    private String mParam2;

    private UserPrefs userPrefs;

    AdView adbannermyresult;
    public ExplanationQuestionAnsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExplanationQuestionAnsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExplanationQuestionAnsFragment newInstance(Questions param1, String param2) {
        ExplanationQuestionAnsFragment fragment = new ExplanationQuestionAnsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explanation_question_ans, container, false);
        userPrefs = new UserPrefs(getActivity());

        adbannermyresult = (AdView)view.findViewById(R.id.adbannermyresult);
        AdRequest adRequest = new AdRequest.Builder().build();
        adbannermyresult.loadAd(adRequest);

        final ImageView borderFavourite = (ImageView) view.findViewById(R.id.add_favourite);
        final ImageView reportQuestion = (ImageView) view.findViewById(R.id.report_question);
        WebView question_name = (WebView) view.findViewById(R.id.question_name);
        WebView passageText = (WebView) view.findViewById(R.id.passageText);
        RadioGroup rgAnsGrp = (RadioGroup)view.findViewById(R.id.rg_ansgp);

//        RadioButton radioButtonOne = (RadioButton)view.findViewById(R.id.rb_ans_1);
//        RadioButton radioButtonTwo = (RadioButton)view.findViewById(R.id.rb_ans_2);
//        RadioButton radioButtonThree = (RadioButton)view.findViewById(R.id.rb_ans_3);
//        RadioButton radioButtonFour = (RadioButton)view.findViewById(R.id.rb_ans_4);

        WebView explanation_describe = (WebView)view.findViewById(R.id.explanation_describe);

        final Questions mdata = mParam1;
        if(mdata.getDescription() != "null" && mdata.getDescription().length() > 0) {
            passageText.setVisibility(View.VISIBLE);
            passageText.loadDataWithBaseURL(AppConstants.SERVER_URL,mdata.getDescription(),"text/html","UTF-8",null);
            passageText.getSettings().setJavaScriptEnabled(true);
        }
        question_name.setBackgroundColor(Color.parseColor("#d3f5ea"));
        question_name.loadDataWithBaseURL(AppConstants.SERVER_URL,StringEscapeUtils.unescapeHtml3(mdata.getQuestion()),"text/html","UTF-8",null);

        RadioButton[] alltextViews = new RadioButton[mdata.getAnswers().length];
        for (int i = 1; i < mdata.getAnswers().length; i++) {
            String ansStr = mdata.getAnswers()[i];
            String answerAttr = AppHelper.fromHtml(ansStr.substring(0, ansStr.indexOf(">"))).toString();
            String isCorrectAns = (answerAttr.indexOf("correct=") > -1) ? "true" : "false";
            String answerId = answerAttr.split("\"")[1];
            RadioButton rdbtn = new RadioButton(getActivity());
            rdbtn.setId(i);
            rdbtn.setTag(answerId+","+isCorrectAns);
            rdbtn.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(ansStr.substring(ansStr.indexOf(">")+1))));
            rdbtn.setButtonDrawable(R.drawable.custom_radio_icon);
            rdbtn.setPadding(25,10,22,10);
            rgAnsGrp.addView(rdbtn);
            alltextViews[i-1] = rdbtn;
        }
//        radioButtonOne.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(mdata.getAnswers()[1].substring(mdata.getAnswers()[1].indexOf(">")+1))));
//        radioButtonTwo.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(mdata.getAnswers()[2].substring(mdata.getAnswers()[2].indexOf(">")+1))));
//        radioButtonThree.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(mdata.getAnswers()[3].substring(mdata.getAnswers()[3].indexOf(">")+1))));
//        radioButtonFour.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(mdata.getAnswers()[4].substring(mdata.getAnswers()[4].indexOf(">")+1))));

        for (int i = 1 ; i < mdata.getAnswers().length ; i++) {
            if(mdata.getUserdata()!="null"){
                int startsubindex =mdata.getUserdata().indexOf("id=")+3;
                if (mdata.getAnswers()[i].contains(mdata.getUserdata().substring(startsubindex,mdata.getUserdata().indexOf("\" />")))){
                    alltextViews[i-1].setButtonDrawable(R.drawable.wrong_ans);
                    alltextViews[i-1].setPadding(25,10,22,10);
                }
            }

            if (mdata.getAnswers()[i].contains("correct")&&mdata.getAnswers()[i].contains("=\"true\"")){
                alltextViews[i-1].setButtonDrawable(R.drawable.correct_ans);
                alltextViews[i-1].setPaddingRelative(25,10,22,10);
                explanation_describe.setBackgroundColor(Color.WHITE);
                if (mdata.getSolution().equals("null") || mdata.getSolution().equals("")){
                    explanation_describe.loadDataWithBaseURL(AppConstants.SERVER_URL,StringEscapeUtils.unescapeHtml3(alltextViews[i-1].getText().toString()),"text/html","UTF-8",null);
                } else {
                    explanation_describe.loadDataWithBaseURL(AppConstants.SERVER_URL,StringEscapeUtils.unescapeHtml3(mdata.getSolution()),"text/html","UTF-8",null);
                }
            }

//            if (mdata.getAnswers()[i].contains("correct")&&mdata.getAnswers()[i].contains("=\"false\"")){
//                alltextViews[i].setBackgroundColor(Color.RED);
//            }
        }

        if (!mdata.getFevorite().equals("null")){
            borderFavourite.setImageResource(R.drawable.ic_favorite);
        }

        borderFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borderFavourite.setEnabled(false);
                if (!mdata.getFevorite().equals(mdata.getQuestionVersionId())){
                    Log.e("response",AppConstants.ADD_FAVOURITE + mdata.getQuestionIndex() + "&userid=" + userPrefs.getUserId());
                    StringRequest stringRequest = new StringRequest(AppConstants.ADD_FAVOURITE + mdata.getQuestionVersionId() + "&userid=" + userPrefs.getUserId(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("response",response);
                                    mdata.setFevorite(mdata.getQuestionVersionId());
                                    borderFavourite.setImageResource(R.drawable.ic_favorite);
                                    try{
                                        final JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("status").equals("ok")){
                                            Toast.makeText(getActivity(), jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (JSONException jse){

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });

                    MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
                } else {
                    Log.e("response",AppConstants.REMOVE_FACOURITE + mdata.getQuestionIndex() + "&userid=" + userPrefs.getUserId());
                    StringRequest stringRequest = new StringRequest(AppConstants.REMOVE_FACOURITE + mdata.getQuestionVersionId() + "&userid=" + userPrefs.getUserId(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("response",response);
                                    mdata.setFevorite("");
                                    borderFavourite.setImageResource(R.drawable.border_myfavourite);
                                    try{
                                        final JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("status").equals("ok")){
                                            Toast.makeText(getActivity(), jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (JSONException jse){

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
        });

        reportQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                final EditText edittext = new EditText(getActivity());
                edittext.setPadding(20, 20, 20, 20);
                alert.setMessage("Description");
                alert.setTitle("Report");

                alert.setView(edittext);

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String descriptionStr = edittext.getText().toString();
                        sendAddReportRequest(descriptionStr, mdata);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });
        return view;
    }

    void sendAddReportRequest(final String desc, Questions mdata) {
        StringRequest stringRequest = null;
        String url = AppConstants.ADD_REPORT + mdata.getQuestionVersionId() + "&userid=" + userPrefs.getUserId();
        Log.e("response", url);
        AppHelper.showDialog(getActivity(), "Loading Please Wait...");
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.print(response);
                Log.e("response", response);
                AppHelper.hideDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("ok")) {
                        AppHelper.showToast(jsonObject.getString("data"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppHelper.hideDialog();
                Toast.makeText(getActivity(), "Error Please Try Again... ", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                param.put("message", desc);

                System.out.println(param.toString());
                return param;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }
    @Override
    public void onPause() {
        if (adbannermyresult != null) {
            adbannermyresult.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adbannermyresult != null) {
            adbannermyresult.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adbannermyresult != null) {
            adbannermyresult.destroy();
        }
        super.onDestroy();
    }
}
