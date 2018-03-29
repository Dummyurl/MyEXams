package com.saxxis.myexams.fragments.examdescribe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saxxis.myexams.R;
import com.saxxis.myexams.adapters.TimeLineAdapters;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.TimeLineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeLineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeLineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recv_quizztimeline)
    RecyclerView recv_TimeLine;


    public TimeLineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeLineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeLineFragment newInstance(String param1, String param2) {
        TimeLineFragment fragment = new TimeLineFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_time_line, container, false);
        ButterKnife.bind(this,rootView);

        recv_TimeLine.setHasFixedSize(true);
        recv_TimeLine.setLayoutManager(new LinearLayoutManager(getActivity()));
        recv_TimeLine.addItemDecoration(new RecvDecors(getActivity(), R.dimen.job_listing_main_offset));

        try {
            JSONObject timeLineObject = new JSONObject(mParam1);
            JSONArray titleArray = timeLineObject.getJSONArray("title");
            JSONArray timeLineDates = timeLineObject.getJSONArray("timelinedate");
            JSONArray links=timeLineObject.getJSONArray("links");

            if (titleArray.length() == timeLineDates.length()){
                final ArrayList<TimeLineModel> timelinelist= new ArrayList<>();
                for (int i = 0; i < titleArray.length(); i++) {
                    timelinelist.add(new TimeLineModel(
                            titleArray.getString(i),
                            timeLineDates.getString(i),
                            links.getString(i)));
                }

                recv_TimeLine.setAdapter(new TimeLineAdapters(getActivity(),timelinelist));

//                ItemClickSupport.addTo(recv_TimeLine).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        String linkText = timelinelist.get(position).getLinks();
//
//                    }
//                });

            }
        }catch (JSONException jse){

        }
        return rootView;
    }

}
