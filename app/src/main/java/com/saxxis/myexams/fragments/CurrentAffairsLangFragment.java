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
import com.saxxis.myexams.activities.CAffDetailActivity;
import com.saxxis.myexams.adapters.CurrentAffairsAdapter;
import com.saxxis.myexams.adapters.ItemSelectAdapter;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.utils.ItemClickSupport;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.interfaces.OnLoadMoreListener;
import com.saxxis.myexams.model.CurrentaffairsList;
import com.saxxis.myexams.model.QuizzFilterItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentAffairsLangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentAffairsLangFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.rv_currentaffairs)
    RecyclerView recyclerViewlistofAffairs;
    ArrayList<CurrentaffairsList> currAffairsArraylist;

    @BindView(R.id.listload)
    ProgressBar progressBar;

    @BindView(R.id.select_latestupdatecategory)
    TextView txtSelectCategory;

    private AdView mAdView;

    //Bottom sheet items
    BottomSheetBehavior behavior;
    RecyclerView recyclerViewFilter;
    private ItemSelectAdapter mBottomAdapter;
    ArrayList<QuizzFilterItems> quizzFilteritems = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;

    int startIndex = 0, endIndex = 4;
    CurrentAffairsAdapter currentAffAdapter;

    public CurrentAffairsLangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter for url.
     * @param param2 parameter for language to get category.
     * @return A new instance of fragment CurrentAffairsLangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentAffairsLangFragment newInstance(String param1, String param2) {
        CurrentAffairsLangFragment fragment = new CurrentAffairsLangFragment();
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
        View cvoew = inflater.inflate(R.layout.fragment_current_affairs_lang, container, false);
        ButterKnife.bind(this, cvoew);

        mAdView = (AdView) cvoew.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        recyclerViewlistofAffairs.setHasFixedSize(true);
        recyclerViewlistofAffairs.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewlistofAffairs.addItemDecoration(new RecvDecors(getActivity(), R.dimen.job_listing_main_offset));
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewlistofAffairs.setLayoutManager(linearLayoutManager);
        currAffairsArraylist = new ArrayList<CurrentaffairsList>();
        currentAffAdapter = new CurrentAffairsAdapter(recyclerViewlistofAffairs, currAffairsArraylist, getActivity());
        recyclerViewlistofAffairs.setAdapter(currentAffAdapter);
        getArticles(mParam1);
        getLatestUpDateCategory();

        View bottomSheet = cvoew.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        recyclerViewFilter = (RecyclerView) bottomSheet.findViewById(R.id.recyclerViewCategory);
        recyclerViewFilter.setHasFixedSize(true);
        recyclerViewFilter.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemClickSupport.addTo(recyclerViewlistofAffairs).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), CAffDetailActivity.class);
                intent.putExtra("detaildata", currAffairsArraylist.get(position));
                intent.putExtra("detailtitle", mParam2 + " Current Affairs");
                intent.putExtra("comefrom", "host");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return cvoew;
    }

    private void getArticles(final String url) {
        String requestUrl = url + "&start=0&end=4";
        Log.e("response", requestUrl);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            Log.d("response", response);

                            startIndex += 5;

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            final int total = Integer.parseInt(jsonObject.getString("total"));

                            CurrentaffairsList objCurrentaffairsList;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject nameobject = jsonArray.getJSONObject(i);
                                String name = nameobject.getString("title");
                                String id = "";
                                if (nameobject.has("id")) {
                                    id = nameobject.getString("id");
                                }
                                String description = nameobject.getString("introtext");
                                String createddate = nameobject.getString("created");

                                objCurrentaffairsList = new CurrentaffairsList(name, description, id, createddate);
                                currAffairsArraylist.add(objCurrentaffairsList);
//                                  String id=nameobject.getString("parent_id");
                            }

                            //recyclerViewlistofAffairs.setAdapter(new CurrentAffairsAdapter(recyclerViewlistofAffairs,currAffairsArraylist,getActivity()));
                            currentAffAdapter.notifyDataSetChanged();
                            currentAffAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                                @Override
                                public void onLoadMore() {
                                    if(currAffairsArraylist.size() < total) {
                                        currAffairsArraylist.add(null);
                                        currentAffAdapter.notifyItemInserted(currAffairsArraylist.size() - 1);
                                        replaceFragment(url);
                                    }
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

    private void getLatestUpDateCategory() {
        String url = null;
        if (mParam2 == "English") {
            url = AppConstants.LATEST_UPDATES_CATEGORY_CURRENTAFFAIRS + 84;
        }
        if (mParam2 == "Telugu") {
            url = AppConstants.LATEST_UPDATES_CATEGORY_CURRENTAFFAIRS + 85;
        }
        if (mParam2 == "Hindi") {
            url = AppConstants.LATEST_UPDATES_CATEGORY_CURRENTAFFAIRS + 86;
        }

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response", response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            final ArrayList<QuizzFilterItems> arrayList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject dataObject = jsonArray.getJSONObject(i);
                                arrayList.add(new QuizzFilterItems(dataObject.optString("id"), dataObject.getString("title")));
                            }
                            mBottomAdapter = new ItemSelectAdapter(getActivity(), arrayList);
                            recyclerViewFilter.setAdapter(mBottomAdapter);
                            ItemClickSupport.addTo(recyclerViewFilter).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                @Override
                                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                    txtSelectCategory.setText(arrayList.get(position).getExamtypename());
                                    startIndex = 0;
                                    currAffairsArraylist.clear();
                                    getArticles(mParam1 + "&catid=" + arrayList.get(position).getExamtypeid());
                                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

    private void replaceAnotherFragment(String anotherfragment) {
        Log.e("anotherfragment:::", anotherfragment);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(anotherfragment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            if (currAffairsArraylist != null) {
                                currAffairsArraylist.clear();
                            }
                            Log.d("response", response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            currAffairsArraylist = new ArrayList<CurrentaffairsList>();
                            CurrentaffairsList objCurrentaffairsList;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject nameobject = jsonArray.getJSONObject(i);
                                String name = nameobject.getString("title");
                                String description = nameobject.getString("introtext");

                                objCurrentaffairsList = new CurrentaffairsList(name, description, "", nameobject.getString("created"));
                                currAffairsArraylist.add(objCurrentaffairsList);
//                                  String id=nameobject.getString("parent_id");
                            }

                            recyclerViewlistofAffairs.setAdapter(new CurrentAffairsAdapter(recyclerViewlistofAffairs, currAffairsArraylist, getActivity()));
                            ItemClickSupport.addTo(recyclerViewlistofAffairs).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                @Override
                                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                    Intent intent = new Intent(getActivity(), CAffDetailActivity.class);
                                    intent.putExtra("detaildata", currAffairsArraylist.get(position));
//                                    intent.putExtra("alldetails",currAffairsArraylist);
                                    intent.putExtra("detailtitle", mParam2 + " Current Affairs");
                                    intent.putExtra("comefrom", "host");
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

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void replaceFragment(String url) {
        String requestUrl = url + "&start=" + startIndex + "&end=" + endIndex;
        Log.e("response", requestUrl);
        //progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response", response);
                            startIndex += 5;

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (currAffairsArraylist.size() > 0) {
                                currAffairsArraylist.remove(currAffairsArraylist.size() - 1);
                                currentAffAdapter.notifyItemRemoved(currAffairsArraylist.size());
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject nameobject = jsonArray.getJSONObject(i);
                                String name = nameobject.getString("title");
                                String id = "";
                                if (nameobject.has("id")) {
                                    id = nameobject.getString("id");
                                }
                                String description = nameobject.getString("introtext");
                                String createddate = nameobject.getString("created");

                                CurrentaffairsList objCurrentaffairsList = new CurrentaffairsList(name, description, id, createddate);
                                currAffairsArraylist.add(objCurrentaffairsList);
//                                  String id=nameobject.getString("parent_id");
                            }

                            //recyclerViewlistofAffairs.setAdapter(new CurrentAffairsAdapter(getActivity(),currAffairsArraylist));
                            //currentAffAdapter.notifyDataSetChanged();
                            currentAffAdapter.notifyDataSetChanged();
                            currentAffAdapter.setLoaded();

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


    @OnClick(R.id.select_latestupdatecategory)
    void onFabSelection() {
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}