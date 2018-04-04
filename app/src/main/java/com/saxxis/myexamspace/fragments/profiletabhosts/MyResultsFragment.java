package com.saxxis.myexamspace.fragments.profiletabhosts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.adapters.MyResultsAdapter;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.helper.UserPrefs;
import com.saxxis.myexamspace.model.MyResultsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyResultsFragment extends Fragment {


    @BindView(R.id.recv_myresults)
    RecyclerView recvMyResults;

    private UserPrefs muserPrefs;

    @BindView(R.id.nodata_text)
    TextView nodatatext;

    public MyResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_my_results, container, false);
        ButterKnife.bind(this,rootview);
        muserPrefs = new UserPrefs(getActivity());

        recvMyResults.setHasFixedSize(true);
        recvMyResults.setLayoutManager(new LinearLayoutManager(getActivity()));

        StringRequest stringRequest = new StringRequest(AppConstants.MY_EXAM_RESULTS+muserPrefs.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")){
                                JSONArray dataArray=jsonObject.getJSONArray("data");
                                ArrayList<MyResultsList> mlist=new ArrayList<>();
                                if (dataArray.length()!=0) {
                                    nodatatext.setVisibility(View.GONE);
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject objData = dataArray.getJSONObject(i);
                                        mlist.add(new MyResultsList(objData.getString("StatisticsInfoId"), objData.getString("QuizId"), objData.getString("UserId"), objData.getString("Status"), objData.getString("TicketId"),
                                                objData.getString("StartDate"), objData.getString("EndDate"), objData.getString("PassedScore"), objData.getString("UserScore"), objData.getString("MaxScore"),
                                                objData.getString("Passed"), objData.getString("CreatedDate"), objData.getString("QuestionCount"), objData.getString("TotalTime"), objData.getString("ResultEmailed"),
                                                objData.getString("Quizname")));
                                    }

                                    recvMyResults.setAdapter(new MyResultsAdapter(getActivity(), mlist, recvMyResults));
                                }
                                if (dataArray.length()==0) {
                                    nodatatext.setVisibility(View.VISIBLE);
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

        return rootview;
    }
}
