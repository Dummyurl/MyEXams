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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.UserPrefs;
import com.saxxis.myexams.model.CurrentAffairsQuizz;
import com.saxxis.myexams.model.Questions;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubQuizSegmentQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubQuizSegmentQuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private UserPrefs userPrefs;

    LinearLayout explanation_layout;
    ImageView borderFavourite, reportQuestion;

    // TODO: Rename and change types of parameters
    private CurrentAffairsQuizz quixxData;
    private String mParam2;

    ArrayList<RadioButton> listOfRadioButtons = new ArrayList<RadioButton>();

    public SubQuizSegmentQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param quixxData Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubQuizSegmentQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubQuizSegmentQuestionFragment newInstance(CurrentAffairsQuizz quixxData, String param2) {
        SubQuizSegmentQuestionFragment fragment = new SubQuizSegmentQuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, quixxData);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quixxData = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_explanation, container, false);
        userPrefs = new UserPrefs(getActivity());

        TextView questuinIndicator =(TextView)view.findViewById(R.id.question_indication);
        TextView questionPublishDate = (TextView)view.findViewById(R.id.question_publish_data);
        questuinIndicator.setText(mParam2);

        borderFavourite = (ImageView) view.findViewById(R.id.add_favourite);
        reportQuestion = (ImageView) view.findViewById(R.id.report_question);
        WebView question_name = (WebView) view.findViewById(R.id.question_name);
        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.rg_ansgp);
        /*final RadioButton radioButtonOne = (RadioButton)view.findViewById(R.id.rb_ans_1);
        final RadioButton radioButtonTwo = (RadioButton)view.findViewById(R.id.rb_ans_2);
        final RadioButton radioButtonThree = (RadioButton)view.findViewById(R.id.rb_ans_3);
        final RadioButton radioButtonFour = (RadioButton)view.findViewById(R.id.rb_ans_4);*/
//        TextViewWithImages myradioButton = (TextViewWithImages)view.findViewById(R.id.myradiobutton);

        explanation_layout = (LinearLayout)view.findViewById(R.id.explanation_layout);
        WebView explainDescription=(WebView)view.findViewById(R.id.explanation_describe);

        //final RadioButton[] allRadioButtons = {radioButtonOne,radioButtonTwo,radioButtonThree,radioButtonFour};

        final CurrentAffairsQuizz mdata = quixxData;
        question_name.getSettings().setJavaScriptEnabled(true);
        question_name.getSettings().setDefaultTextEncodingName("utf-8");
        question_name.getSettings().setMinimumLogicalFontSize(16);
        question_name.getSettings().setAllowContentAccess(true);
        questionPublishDate.setText(AppHelper.spanDateFormater(mdata.getPublishedDate()));

        question_name.setBackgroundColor(Color.parseColor("#d3f5ea"));
        question_name.loadDataWithBaseURL(AppConstants.SERVER_URL,Pattern.compile("&nbsp;").matcher(StringEscapeUtils.unescapeHtml3(mdata.getQuestion())).replaceAll(""),"text/html","UTF-8",null);
        final String[] options = mdata.getData().split("<answer id=");

        if (!mdata.getSolution().equals("")){
            explainDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,StringEscapeUtils.unescapeHtml3(mdata.getSolution()),"text/html","UTF-8",null);

        }

        if (mdata.getSolution().equals("")){
            for (int k = 0; k < options.length; k++) {
                if (options[k].contains("correct") && options[k].contains("=\"true\"")) {
                    String amphibean =AppHelper.fromHtml(options[k].substring(options[k].indexOf(">")+1))+"";
                    Log.e("response",amphibean);
//                    explainDescription.setText(AppHelper.fromHtml(options[k].substring(options[k].indexOf(">")+1)+""));
//                    explainDescription.setText(StringEscapeUtils.unescapeHtml3(amphibean));
                    explainDescription.loadDataWithBaseURL(AppConstants.SERVER_URL,StringEscapeUtils.unescapeHtml3(amphibean),"text/html","UTF-8",null);
                }
            }
        }

//        String stroptone=options[1].substring(options[1].indexOf(">")+1);
//        String stropttwo=options[2].substring(options[2].indexOf(">")+1);
//        String stroptthree = options[3].substring(options[3].indexOf(">")+1);
//        String stroptfour=options[4].substring(options[4].indexOf(">")+1);

//        if (stroptone.contains(".png}")){
////            myradioButton.setText(AppConstants.SERVER_URL+stroptone.replaceAll(" ","%20").replace("{","").replace("}","").split("</answer>")[0]);
//            radioButtonOne.setCompoundDrawablesWithIntrinsicBounds(Drawable.createFromPath(AppConstants.SERVER_URL+stroptone.replaceAll(" ","%20").replace("{","").replace("}","").split("</answer>")[0]),null,null,null);
//            radioButtonTwo.setCompoundDrawablesWithIntrinsicBounds(Drawable.createFromPath(AppConstants.SERVER_URL+stropttwo.replaceAll(" ","%20").replace("{","").replace("}","").split("</answer>")[0]),null,null,null);
//            radioButtonThree.setCompoundDrawablesWithIntrinsicBounds(Drawable.createFromPath(AppConstants.SERVER_URL+stroptthree.replaceAll(" ","%20").replace("{","").replace("}","").split("</answer>")[0]),null,null,null);
//            radioButtonFour.setCompoundDrawablesWithIntrinsicBounds(Drawable.createFromPath(AppConstants.SERVER_URL+stroptfour.replaceAll(" ","%20").replace("{","").replace("}","").split("</answer>")[0]),null,null,null);
//
//            Log.e("response",AppConstants.SERVER_URL+stroptone.replaceAll(" ","%20").replace("{","").replace("}",""));
////            radioButtonOne.setText(AppConstants.SERVER_URL+stroptone.replaceAll(" ","%20").replace("{","").replace("}","").split("</answer>")[0]);
////            radioButtonTwo.setText(AppConstants.SERVER_URL+stropttwo.replaceAll(" ","%20").replace("{","").replace("}","").split("</answer>")[0]);
////            radioButtonThree.setText(AppConstants.SERVER_URL+stroptthree.replaceAll(" ","%20").replace("{","").replace("}","").split("</answer>")[0]);
////            radioButtonFour.setText(AppConstants.SERVER_URL+stroptfour.replaceAll(" ","%20").replace("{","").replace("}","").split("</answer>")[0]);
//        }
//        if (!stroptone.contains(".png}")){
        for (int i = 1; i < options.length; i++) {
            String ansStr = options[i];
            String stropt=ansStr.substring(ansStr.indexOf(">")+1);
            String answerAttr = AppHelper.fromHtml(ansStr.substring(0, ansStr.indexOf(">"))).toString();
            String isCorrectAns = (answerAttr.indexOf("correct=") > -1) ? "true" : "false";
            String answerId = answerAttr.split("\"")[1];
            RadioButton rdbtn = new RadioButton(getActivity());
            rdbtn.setId(i);
            //rdbtn.setTag(answerId+","+isCorrectAns);
            rdbtn.setTag(ansStr);
            rdbtn.setText(StringEscapeUtils.unescapeHtml3(Pattern.compile("&nbsp;").matcher(AppHelper.fromHtml(stropt)).replaceAll("")));
            //rdbtn.setText(AppHelper.fromHtml(StringEscapeUtils.unescapeHtml3(ansStr.substring(ansStr.indexOf(">")+1))));
            rdbtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rdbtn.setButtonDrawable(R.drawable.custom_radio_icon);
            rdbtn.setPadding(25,10,22,10);
            radioGroup.addView(rdbtn);
            listOfRadioButtons.add((RadioButton)rdbtn);
        }
//            radioButtonOne.setText(StringEscapeUtils.unescapeHtml3(Pattern.compile("&nbsp;").matcher(AppHelper.fromHtml(stroptone)).replaceAll("")));
//            radioButtonTwo.setText(StringEscapeUtils.unescapeHtml3(Pattern.compile("&nbsp;").matcher(AppHelper.fromHtml(stropttwo)).replaceAll("")));
//            radioButtonThree.setText(StringEscapeUtils.unescapeHtml3(Pattern.compile("&nbsp;").matcher(AppHelper.fromHtml(stroptthree)).replaceAll("")));
//            radioButtonFour.setText(StringEscapeUtils.unescapeHtml3(Pattern.compile("&nbsp;").matcher(AppHelper.fromHtml(stroptfour)).replaceAll("")));
//        }

        for (int j = 0; j < listOfRadioButtons.size(); j++) {
            final int finalJ = j;

            listOfRadioButtons.get(j).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listOfRadioButtons.get(finalJ).setButtonDrawable(R.drawable.wrong_ans);
                    //listOfRadioButtons.get(finalJ).setPadding(20,10,20,10);
//                    allRadioButtons[finalJ].setButtonDrawable(android.R.drawable.screen_background_light_transparent);
                    for (int i = 0; i < listOfRadioButtons.size(); i++) {
                        String optionStr = listOfRadioButtons.get(i).getTag().toString();
                        if (optionStr.contains("correct") && optionStr.contains("=\"true\"")) {
                            listOfRadioButtons.get(i).setButtonDrawable(R.drawable.correct_ans);
                            //listOfRadioButtons.get(i).setPadding(20,10,20,10);
//                            allRadioButtons[i - 1].setButtonDrawable(android.R.drawable.screen_background_light_transparent);
                        }

                        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_top);
                        explanation_layout.startAnimation(slide);
                        explanation_layout.setVisibility(View.VISIBLE);

                        //listOfRadioButtons.get(i).setEnabled(false);
//                        radioButtonThree.setEnabled(false);
//                        radioButtonTwo.setEnabled(false);
//                        radioButtonFour.setEnabled(false);
                    }
                }
            });
        }
/*

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.ll);
        RadioGroup rronegroup = new RadioGroup(getApplicationContext());
        rronegroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        for (int j = 1; j <= 4; j++) {
            LinearLayout lnr = new LinearLayout(getActivity());
            RadioButton optionRadioButton = new RadioButton(getActivity());
            LinearLayout.LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            optionRadioButton.setLayoutParams(p);
            optionRadioButton.setPadding(5,5,5,5);

            lnr.addView(optionRadioButton);

            WebView optionWebView = new WebView(getApplicationContext());
            optionWebView.loadDataWithBaseURL(AppConstants.SERVER_URL, StringEscapeUtils.unescapeHtml3(options[j].substring(options[j].indexOf(">")+1)), "text/html", "UTF-8", null);
            p = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            optionWebView.setLayoutParams(p);
            lnr.addView(optionWebView);
            rronegroup.addView(lnr);
        }
        ll.addView(rronegroup);
*/

        if (!mdata.getFevorite().equals("null")){
            borderFavourite.setImageResource(R.drawable.ic_favorite);
        }

        borderFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borderFavourite.setEnabled(false);
                if (!mdata.getFevorite().equals(mdata.getQuestionVersionId())){
                    Log.e("response",AppConstants.ADD_FAVOURITE + mdata.getQuestionVersionId() + "&userid=" + userPrefs.getUserId());
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
                    Log.e("response",AppConstants.REMOVE_FACOURITE + mdata.getQuestionVersionId() + "&userid=" + userPrefs.getUserId());
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
                                    //Toast.makeText(getActivity(), "Removed In MyFavourites", Toast.LENGTH_SHORT).show();
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
                        sendAddReportRequest(descriptionStr, mdata.getQuestionVersionId());
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
    void sendAddReportRequest(final String desc, String questionId) {
        StringRequest stringRequest = null;
        String url = AppConstants.ADD_REPORT + questionId + "&userid=" + userPrefs.getUserId();
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
}
