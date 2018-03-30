package com.saxxis.myexams.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.saxxis.myexams.R;
import com.saxxis.myexams.activities.EducaitonDetailedActivity;
import com.saxxis.myexams.activities.MustReadDetailsActivity;
import com.saxxis.myexams.adapters.ItemSelectAdapter;
import com.saxxis.myexams.adapters.MustReadAdapter;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.utils.ItemClickSupport;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.MustRead;
import com.saxxis.myexams.model.QuizzFilterItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chaitanyat on 29-01-2018.
 */

public class MustReadFragment extends Fragment {


    @BindView(R.id.recv_educationews)
    RecyclerView recvEducationNews;
    ArrayList<MustRead> educationNewsArraylist;

    @BindView(R.id.progressbar_educationnews)
    ProgressBar educationProgressbar;

    @BindView(R.id.select_latestupdatecategory)
    TextView txtLatestUpdatesCategory;
    @BindView(R.id.select_mustreadcategory) TextView txtMustReadCategory;



    //Bottom sheet items
    BottomSheetBehavior behavior;
    RecyclerView recyclerViewFilter;
    private ItemSelectAdapter mBottomAdapter;
    ArrayList<QuizzFilterItems> quizzFilteritems = new ArrayList<>();

    MustReadAdapter educationnewsadapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int startIndex = 0, endIndex=10;


    public MustReadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LatestUpdateLangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MustReadFragment newInstance(String param1, String param2) {
        MustReadFragment fragment = new MustReadFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest_update_lang, container, false);
        ButterKnife.bind(this,view);

        AdView mAdView = (AdView)view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        recvEducationNews.setHasFixedSize(true);
        recvEducationNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        recvEducationNews.addItemDecoration(new RecvDecors(getActivity(),R.dimen.job_listing_main_offset));

        txtLatestUpdatesCategory.setVisibility(View.GONE);
        txtMustReadCategory.setVisibility(View.VISIBLE);

        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        recyclerViewFilter = (RecyclerView)bottomSheet.findViewById(R.id.recyclerViewFilterCategory);
        recyclerViewFilter.setHasFixedSize(true);
        recyclerViewFilter.setLayoutManager(new LinearLayoutManager(getActivity()));

        educationNewsArraylist = new ArrayList<MustRead>();
        educationnewsadapter = new MustReadAdapter(recvEducationNews, educationNewsArraylist, getActivity());
        recvEducationNews.setAdapter(educationnewsadapter);
        getLatestUpdates(mParam1);
        getFilterData();

        ItemClickSupport.addTo(recvEducationNews).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent=new Intent(getActivity(),MustReadDetailsActivity.class);
                intent.putExtra("detaildata",educationNewsArraylist.get(position));
                intent.putExtra("detailtitle","Latest Updates in "+mParam2);
                intent.putExtra("comefrom","host");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return view;
    }
    private void getLatestUpdates(final String url) {
        String requestUrl = url + "&start=0&end=10";
        educationProgressbar.setVisibility(View.VISIBLE);
        Log.e("URL",requestUrl);
        StringRequest stringRequest = new StringRequest(requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        educationProgressbar.setVisibility(View.GONE);
                        Log.e("response",response);
                        startIndex += 10;

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            //final int total = Integer.parseInt(jsonObject.getString("total"));

                            MustRead objEducationNews;
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject nameobject=jsonArray.getJSONObject(i);
                                String introtext = null;
                                if(nameobject.has("introtext")){
                                    introtext = nameobject.getString("introtext");
                                }

                                objEducationNews = new MustRead(nameobject.getString("title"),
                                        introtext,nameobject.getString("publish_up"));
                                educationNewsArraylist.add(objEducationNews);
                            }

                            educationnewsadapter.notifyDataSetChanged();
                            /*educationnewsadapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                                @Override
                                public void onLoadMore() {
                                    if(educationNewsArraylist.size() < total) {
                                        educationNewsArraylist.add(null);
                                        educationnewsadapter.notifyItemInserted(educationNewsArraylist.size() - 1);
                                        getDataLatestUpdates(url);
                                    }
                                }
                            });*/
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
    private void getDataLatestUpdates(String url) {
        String requestUrl = url + "&start="+startIndex+"&end="+endIndex;
        Log.e("URL",requestUrl);
        StringRequest stringRequest = new StringRequest(requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        startIndex += 10;

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");

                            if(educationNewsArraylist.size() > 0) {
                                educationNewsArraylist.remove(educationNewsArraylist.size() - 1);
                                educationnewsadapter.notifyItemRemoved(educationNewsArraylist.size());
                            }

                            MustRead objEducationNews;
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject nameobject=jsonArray.getJSONObject(i);

                                String introtext = null;
                                if(nameobject.has("introtext")){
                                    introtext = nameobject.getString("introtext");
                                }

                                objEducationNews = new MustRead(nameobject.getString("title"),
                                        introtext,nameobject.getString("publish_up"));
                                educationNewsArraylist.add(objEducationNews);
                            }

                            educationnewsadapter.notifyDataSetChanged();
                            educationnewsadapter.setLoaded();

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

    private void getFilterData() {
        String url=null;
        if (mParam2=="English"){
            url=AppConstants.CA_MUST_READ_CATEGORY+300;
        }if (mParam2=="Telugu"){
            url=AppConstants.CA_MUST_READ_CATEGORY+301;
        }if (mParam2=="Hindi"){
            url=AppConstants.CA_MUST_READ_CATEGORY+302;
        }
        Log.d("response",url);
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response",response);
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            final ArrayList<QuizzFilterItems> arrayList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject dataObject=jsonArray.getJSONObject(i);
                                arrayList.add(new QuizzFilterItems(dataObject.optString("id"),dataObject.getString("title")));
                            }
                            mBottomAdapter = new ItemSelectAdapter(getActivity(),arrayList);
                            recyclerViewFilter.setAdapter(mBottomAdapter);
                            ItemClickSupport.addTo(recyclerViewFilter).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                @Override
                                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                    txtLatestUpdatesCategory.setText(arrayList.get(position).getExamtypename());
                                    //getSelectedCategoryDataUpdated(AppConstants.CA_MUST_READ+arrayList.get(position).getExamtypeid());
                                    startIndex = 0;
                                    educationNewsArraylist.clear();
                                    getLatestUpdates(AppConstants.CA_MUST_READ+arrayList.get(position).getExamtypeid());
                                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                }
                            });
                        }catch (JSONException e) {
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

    private void getSelectedCategoryDataUpdated(String mParam1) {
        educationProgressbar.setVisibility(View.VISIBLE);
        Log.e("response",mParam1);
        StringRequest stringRequest = new StringRequest(mParam1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        educationProgressbar.setVisibility(View.GONE);
                        Log.e("response",response);
                        educationProgressbar.setVisibility(View.GONE);
                        try {
                            if (educationNewsArraylist != null){
                                educationNewsArraylist.clear();
                            }
//                            Log.d("response",response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
//                            ArrayList<EducationNews> currAffairsArraylist = EducatonNewsParser.getInstance(response).getData();
                            educationNewsArraylist = new ArrayList<MustRead>();
                            MustRead objEducationNews;
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject nameobject=jsonArray.getJSONObject(i);

                                objEducationNews = new MustRead(nameobject.getString("title"),
                                        nameobject.getString("introtext"),nameobject.getString("publish_up"));
                                educationNewsArraylist.add(objEducationNews);
                            }

                            educationnewsadapter = new MustReadAdapter(recvEducationNews,educationNewsArraylist,getActivity());
                            recvEducationNews.setAdapter(educationnewsadapter);
                            ItemClickSupport.addTo(recvEducationNews).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                @Override
                                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                    Intent intent=new Intent(getActivity(),EducaitonDetailedActivity.class);
                                    intent.putExtra("detaildata",educationNewsArraylist.get(position));
                                    intent.putExtra("detailtitle",mParam2+" Latest Updates");
                                    intent.putExtra("comefrom","host");
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                }
                            });
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

    @OnClick(R.id.select_mustreadcategory)
    void onFabSelection(){
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        if (behavior.getState()== BottomSheetBehavior.STATE_EXPANDED){
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if (behavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}
