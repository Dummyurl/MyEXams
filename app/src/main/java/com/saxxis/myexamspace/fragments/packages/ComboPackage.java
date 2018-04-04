package com.saxxis.myexamspace.fragments.packages;


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
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.activities.FilterActivity;
import com.saxxis.myexamspace.adapters.PackagesAdapter;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
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
public class ComboPackage extends Fragment {


    @BindView(R.id.combos_progress)
    ProgressBar comboprogress;

    @BindView(R.id.recv_combo_packages)
    RecyclerView recv_pack;

    @BindView(R.id.img_combo_filter)
    ImageView imgFilter;

    @BindView(R.id.nodatafound)
    TextView noDataFoundTxt;

    public ComboPackage() {
        // Required empty public constructor
    }

    public static ComboPackage getInstance(){
        return new ComboPackage();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_combo_package, container, false);
        ButterKnife.bind(this,rootView);

        recv_pack.setHasFixedSize(true);
        recv_pack.setLayoutManager(new LinearLayoutManager(getActivity()));
        recv_pack.addItemDecoration(new RecvDecors(getActivity(),R.dimen.job_listing_main_offset));

        setrecvview(AppConstants.COMBO_PACKAGES_URL);
        return rootView;
    }

    private void setrecvview(String url) {
        Log.d("response",url);
        comboprogress.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        comboprogress.setVisibility(View.GONE);
                        try {
                            JSONObject objResponse = new JSONObject(response);
                            if (objResponse.getString("status").equals("ok")) {
                                ArrayList<PackagesItems> data=new ArrayList<>();
                                JSONArray jsonArray=objResponse.getJSONArray("data");

                                if (jsonArray.length()==0){
                                    noDataFoundTxt.setVisibility(View.VISIBLE);
                                }
                                if (jsonArray.length()>0){

                                noDataFoundTxt.setVisibility(View.GONE);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String id=jsonObject.getString("id");
                                    String quiz_category=jsonObject.getString("quiz_category");
                                    String quiz_subject=jsonObject.getString("quiz_subject");
                                    String quiz_sublevel=jsonObject.getString("quiz_sublevel");
                                    String quiz_subchapter=jsonObject.getString("quiz_subchapter");
                                    String quiz_pack=jsonObject.getString("quiz_pack");
                                    String quiz_offer_type=jsonObject.getString("quiz_offer_type");
                                    String quiz_num=jsonObject.getString("quiz_num");
                                    String questions_num=jsonObject.getString("questions_num");
                                    String quiz_el=jsonObject.getString("quiz_el");
                                    String quiz_tet=jsonObject.getString("quiz_tet");
                                    String quiz_ques=jsonObject.getString("quiz_ques");
                                    String quiz_offer_price=jsonObject.getString("quiz_offer_price");
                                    String quiz_offer_description=jsonObject.getString("quiz_offer_description");
                                    String quiz_offer_readmore=jsonObject.getString("quiz_offer_readmore");
                                    String quiz_offer_title=jsonObject.getString("quiz_offer_title");
                                    String quiz_offer_image=jsonObject.getString("quiz_offer_image");
                                    String published=jsonObject.getString("published");
                                    String Subjects=jsonObject.getString("Subjects");
                                    PackagesItems packagesItems = new PackagesItems(id,quiz_category,quiz_subject,quiz_sublevel,
                                            quiz_subchapter,quiz_pack,quiz_offer_type,quiz_num,questions_num,quiz_el,
                                            quiz_tet,quiz_ques,quiz_offer_price,quiz_offer_description,
                                            quiz_offer_readmore,quiz_offer_title,quiz_offer_image,published,Subjects);

                                    data.add(packagesItems);
                                }
                                 recv_pack.setAdapter(new PackagesAdapter(getActivity(),data));
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


    @OnClick(R.id.img_combo_filter)
    void getFilter(){
        Intent intent=new Intent(getActivity(), FilterActivity.class);
        intent.putExtra("pack",2);
        startActivityForResult(intent,1333);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1333){
//            Log.e("response",data.getDataString());
            if (resultCode == 444){
                Bundle extras=data.getExtras();
                Log.e("response",extras.getString("searchurl"));
                setrecvview(extras.getString("searchurl"));
            }
        }
    }

}
