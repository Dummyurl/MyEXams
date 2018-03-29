package com.saxxis.myexams.fragments.myresult;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.model.MyResultsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultViewTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultViewTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private MyResultsList ticketid;
    private String param2;

    @BindView(R.id.txt_noof_questions)
    TextView txt_noof_questions;
    @BindView(R.id.txt_noof_attempted)
    TextView txt_noof_attempted;
    @BindView(R.id.txt_noof_unattempted)
    TextView txt_noof_unattempted;
    @BindView(R.id.txt_noof_correct_attempted)
    TextView txt_noof_correct_attempted;
    @BindView(R.id.txt_noof_wrong_attempted)
    TextView txt_noof_wrong_attempted;
    @BindView(R.id.txt_noof_notvisdited)
    TextView txt_noof_notvisdited;
    @BindView(R.id.txt_finalmarks)
    TextView txt_finalmarks;
    @BindView(R.id.txt_finalstatus)
    TextView txt_finalstatus;
    @BindView(R.id.txt_startdate)TextView txtStartDate;
    @BindView(R.id.txt_stopdate)TextView txtStopDate;
    @BindView(R.id.txt_totaltime)TextView txtTotalTimeTaken;


    public ResultViewTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ticketid Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultViewTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultViewTabFragment newInstance(MyResultsList ticketid, String param2) {
        ResultViewTabFragment fragment = new ResultViewTabFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, ticketid);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticketid = getArguments().getParcelable(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_view_tab, container, false);
        ButterKnife.bind(this,view);
Log.e("Request URL", AppConstants.MY_EXAM_RESULT_VIEW + ticketid.getTicketId());
        StringRequest stringRequest=new StringRequest(AppConstants.MY_EXAM_RESULT_VIEW + ticketid.getTicketId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray dataArray=jsonObject.getJSONArray("questions");

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataJsnObject =dataArray.getJSONObject(i);

                                txt_noof_questions.setText(jsonObject.getString("Totalquestions"));
                                txt_noof_attempted.setText(jsonObject.getString("attempt"));
                                txt_noof_unattempted.setText(jsonObject.getString("unattempt"));
                                txt_noof_correct_attempted.setText(jsonObject.getString("correctattempt"));
                                txt_noof_wrong_attempted.setText(jsonObject.getString("wrongattempt"));
                                txt_noof_notvisdited.setText(jsonObject.getString("Notvisited"));

                                txt_finalmarks.setText(dataJsnObject.getString("UserScore")+"/"+dataJsnObject.getString("MaxScore"));
                                if(dataJsnObject.getString("Passed").equals("0")){
                                    txt_finalstatus.setText("Fail");
                                    txt_finalstatus.setTextColor(Color.RED);
                                }

                                if(dataJsnObject.getString("Passed").equals("1")){
                                    txt_finalstatus.setText("Passsed");
                                    txt_finalstatus.setTextColor(Color.GREEN);
                                }

                                txtStartDate.setText(AppHelper.getddMMyyyy(ticketid.getStartDate()));
                                txtStopDate.setText(AppHelper.getddMMyyyy(ticketid.getEndDate()));

                                try {
                                    Date begindate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ticketid.getStartDate());
                                    Date completedate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ticketid.getEndDate());
                                    if (begindate.before(completedate)){
                                        long diffSec = completedate.getTime()-begindate.getTime();
                                        long diffSeconds = diffSec / 1000 % 60;
                                        long diffMinutes = diffSec / (60 * 1000) % 60;
                                        long diffHours = diffSec / (60 * 60 * 1000);
                                        txtTotalTimeTaken.setText(diffHours+":"+diffMinutes+":"+diffSeconds);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
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

}
