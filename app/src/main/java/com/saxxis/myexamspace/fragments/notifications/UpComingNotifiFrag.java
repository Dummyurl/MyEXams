package com.saxxis.myexamspace.fragments.notifications;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.activities.DetailedNotificationsActivity;
import com.saxxis.myexamspace.activities.filters.NotificationFilter;
import com.saxxis.myexamspace.adapters.UpComingNotificasListAdapter;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.helper.utils.ItemClickSupport;
import com.saxxis.myexamspace.helper.utils.RecvDecors;
import com.saxxis.myexamspace.model.CurrentaffairsList;
import com.saxxis.myexamspace.model.NotifiDataResult;
import com.saxxis.myexamspace.parsers.NotificationParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpComingNotifiFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpComingNotifiFrag extends Fragment {

    @BindView(R.id.ipcoming_progress)
    ProgressBar upcomingprogress;

    @BindView(R.id.recv_upcomnotif)
    RecyclerView recvUpcoming;

    @BindView(R.id.upcoming_filter)
    ImageView imageUpcomingFilter;

    @BindView(R.id.nodataupcoming)
    TextView nodataUpcoming;

    @BindView(R.id.adbannerView)
    AdView adViewBanner;

    ArrayList<NotifiDataResult> notifcadata;

    public UpComingNotifiFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UpComingNotifiFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static UpComingNotifiFrag newInstance() {
        UpComingNotifiFrag fragment = new UpComingNotifiFrag();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_up_coming_notifi, container, false);
        ButterKnife.bind(this,rootView);


        recvUpcoming.setHasFixedSize(true);
        recvUpcoming.setLayoutManager(new LinearLayoutManager(getActivity()));
        recvUpcoming.addItemDecoration(new RecvDecors(getActivity(),R.dimen.job_listing_main_offset));


        AdRequest belowFooteradRequest = new AdRequest.Builder()
                .build();
        adViewBanner.loadAd(belowFooteradRequest);

        setrecvview(AppConstants.UPCOMING_NOTIFICATIONS_URL);
        return rootView;
    }
    @Override
    public void onPause() {
        if (adViewBanner != null) {
            adViewBanner.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adViewBanner != null) {
            adViewBanner.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adViewBanner != null) {
            adViewBanner.destroy();
        }
        super.onDestroy();
    }

    private void setrecvview(String url) {
        Log.d("response",url);
        upcomingprogress.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        upcomingprogress.setVisibility(View.GONE);
                        try {
                            JSONObject objResponse = new JSONObject(response);

                            if (objResponse.getString("status").equals("ok")){
                                // JSONArray objdataarr=objResponse.getJSONArray("data");
                                notifcadata = NotificationParser.getmInstance(response).getData();
                                if (notifcadata != null && notifcadata.size()==0){
                                    nodataUpcoming.setVisibility(View.VISIBLE);
                                    recvUpcoming.setVisibility(View.GONE);
                                }
                                if (notifcadata != null && notifcadata.size()>0) {
                                    nodataUpcoming.setVisibility(View.GONE);
                                    recvUpcoming.setVisibility(View.VISIBLE);

                                    recvUpcoming.setAdapter(new UpComingNotificasListAdapter(getActivity(), notifcadata));
                                    ItemClickSupport.addTo(recvUpcoming).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                        @Override
                                        public void onItemClicked(RecyclerView recyclerView, final int position, View v) {

                                            final ProgressDialog proDialog = new ProgressDialog(getActivity());
                                            proDialog.setMessage("Loading Please Wait...");
                                            ;
                                            proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                            proDialog.setIndeterminate(true);
                                            proDialog.show();
                                            StringRequest stringRequest = new StringRequest(AppConstants.DETAILED_NOTIFICATIONS + notifcadata.get(position).getJob_id().trim(),
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            proDialog.dismiss();

                                                            CurrentaffairsList mData;
                                                            try {
                                                                JSONObject objresponse = new JSONObject(response);
                                                                if (objresponse.getString("status").equals("ok")) {

                                                                    JSONArray jsonArray = objresponse.getJSONArray("data");
                                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                                        JSONObject objOne = jsonArray.getJSONObject(i);
                                                                        mData = new CurrentaffairsList(notifcadata.get(position).getTitle(), objOne.getString("job_description"), "", "");
                                                                        Intent intent = new Intent(getActivity(), DetailedNotificationsActivity.class);
                                                                        intent.putExtra("detaildata", mData);
                                                                        intent.putExtra("detailtitle", "Notifications");
                                                                        startActivity(intent);
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
                                        }
                                    });
                                }
                            }
                        }catch (JSONException je){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }

    @OnClick(R.id.upcoming_filter)
    void upcomingFilter(){
        Intent intent = new Intent(getActivity(), NotificationFilter.class);
        intent.putExtra("notfcat",2);
        startActivityForResult(intent,144);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==144){
            if (resultCode==555){
                Bundle bundle=data.getExtras();
                String searchurl=bundle.getString("searchurl");
                Log.e("response",searchurl);
                setrecvview(searchurl);
            }
        }
    }
}
