package com.saxxis.myexamspace.fragments.packages;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.activities.FilterActivity;
import com.saxxis.myexamspace.adapters.PackagesAdapter;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.helper.utils.RecvDecors;
import com.saxxis.myexamspace.model.PackagesItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SinglePackage extends Fragment {

    @BindView(R.id.spinner_single_filter)
    ImageView spinnerFilter;

    @BindView(R.id.recv_single_packages)
    RecyclerView recv_pack;

    @BindView(R.id.single_progress)
    ProgressBar singleprogress;

    @BindView(R.id.nodatafoundsingle)
    TextView noDataFoundSingle;

    ArrayList<PackagesItems> data;

    String classId="";
    String examId="";
    String testSeriesId="";
    String mockTestId="";

    public SinglePackage() {
        // Required empty public constructor
    }

    public static SinglePackage  getInstance(){
        return new SinglePackage();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_single_package, container, false);
        ButterKnife.bind(this,rootView);

        data = new ArrayList<>();

        recv_pack.setHasFixedSize(true);
        recv_pack.setLayoutManager(new LinearLayoutManager(getActivity()));
        recv_pack.addItemDecoration(new RecvDecors(getActivity(),R.dimen.job_listing_main_offset));

        setrecvview(AppConstants.PACKAGES_URL);
        return rootView;
    }

    private void selectFilter(String url, final String id, final String name,final int position) {
        Log.d("response",url);
        AppHelper.showDialog(getActivity(),"Loading Please Wait...");
        final StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        AppHelper.hideDialog();
                        try {
                            JSONObject objResponse = new JSONObject(response);
                            JSONArray objArray = objResponse.getJSONArray("data");

                            final String[] idArray = new String[objArray.length()];
                            String[] nameArray=new String[objArray.length()];
                            for (int i = 0; i < objArray.length(); i++) {
                                JSONObject dataObject =objArray.getJSONObject(i);
                                nameArray[i]=dataObject.optString(name);
                                idArray[i]=dataObject.optString(id);
                            }

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Select Choice")
                                    .setItems(nameArray, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Log.e("response",classId);
                                                }
                                            }
                                    )
                                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNeutralButton("Search", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create().show();
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




    private void getSinglePackageFilterData(String url) {
        Log.d("response",url);
        AppHelper.showDialog(getActivity(),"Loading Please Wait...");
        final StringRequest stringRequest;
            stringRequest = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);
                            AppHelper.hideDialog();
                            try {
                                JSONObject objResponse = new JSONObject(response);
                                JSONArray objArray = objResponse.getJSONArray("data");



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

    private void setrecvview(String url) {
        Log.d("response",url);
        singleprogress.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        singleprogress.setVisibility(View.GONE);
                        try {
                            JSONObject objResponse = new JSONObject(response);
                            if (objResponse.getString("status").equals("ok")) {

                                JSONArray jsonArray=objResponse.getJSONArray("data");

                                if (jsonArray.length()==0){
                                    noDataFoundSingle.setVisibility(View.VISIBLE);
                                }
                                    if (jsonArray.length()>0) {
                                        if (data != null) {
                                            data.clear();
                                        }

                                        noDataFoundSingle.setVisibility(View.GONE);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            String id = jsonObject.getString("id");
                                            String quiz_category = jsonObject.getString("quiz_category");
                                            String quiz_subject = jsonObject.getString("quiz_subject");
                                            String quiz_sublevel = jsonObject.getString("quiz_sublevel");
                                            String quiz_subchapter = jsonObject.getString("quiz_subchapter");
                                            String quiz_pack = jsonObject.getString("quiz_pack");
                                            String quiz_offer_type = jsonObject.getString("quiz_offer_type");
                                            String quiz_num = jsonObject.getString("quiz_num");
                                            String questions_num = jsonObject.getString("questions_num");
                                            String quiz_el = jsonObject.getString("quiz_el");
                                            String quiz_tet = jsonObject.getString("quiz_tet");
                                            String quiz_ques = jsonObject.getString("quiz_ques");
                                            String quiz_offer_price = jsonObject.getString("quiz_offer_price");
                                            String quiz_offer_description = jsonObject.getString("quiz_offer_description");
                                            String quiz_offer_readmore = jsonObject.getString("quiz_offer_readmore");
                                            String quiz_offer_title = jsonObject.getString("quiz_offer_title");
                                            String quiz_offer_image = jsonObject.getString("quiz_offer_image");
                                            String published = jsonObject.getString("published");
                                           String Subjects=jsonObject.optString("Subjects");
                                            PackagesItems packagesItems = new PackagesItems(id, quiz_category, quiz_subject, quiz_sublevel,
                                                    quiz_subchapter, quiz_pack, quiz_offer_type, quiz_num, questions_num, quiz_el,
                                                    quiz_tet, quiz_ques, quiz_offer_price, quiz_offer_description,
                                                    quiz_offer_readmore, quiz_offer_title, quiz_offer_image, published,Subjects);

                                            data.add(packagesItems);
                                        }

                                        recv_pack.setAdapter(new PackagesAdapter(getActivity(), data));
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

    @OnClick(R.id.spinner_single_filter)
    void clicklFilter(){
        Intent intent=new Intent(getActivity(), FilterActivity.class);
        intent.putExtra("pack",1);
        startActivityForResult(intent,1332);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1332){
//            Log.e("response",data.getDataString());
            if (resultCode == 444){
                Bundle extras = data.getExtras();
                Log.e("response",extras.getString("searchurl"));
                setrecvview(extras.getString("searchurl"));
            }
        }
    }

}
