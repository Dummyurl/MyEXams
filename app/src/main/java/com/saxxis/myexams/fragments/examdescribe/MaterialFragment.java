package com.saxxis.myexams.fragments.examdescribe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.saxxis.myexams.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MaterialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaterialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.section_label)
    WebView webview;

    public MaterialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MaterialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MaterialFragment newInstance(String param1, String param2) {
        MaterialFragment fragment = new MaterialFragment();
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
        View rooView = inflater.inflate(R.layout.fragment_material, container, false);
        ButterKnife.bind(this,rooView);

        webview.getSettings().setDefaultTextEncodingName("utf-8");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setMinimumLogicalFontSize(19);
        webview.getSettings().setAllowContentAccess(true);

//        try {
//           JSONArray jsonObject = new JSONArray(mParam2);
//        if (jsonObject!=null){
//            for (int i = 0; i < jsonObject.length(); i++) {
//                   JSONObject materialObject=jsonObject.getJSONObject(i);
//                   String titleName = materialObject.getString("title");
//                   String pdfurl = AppConstants.PDF_URL+materialObject.getString("file_name");
//                   webview.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdfurl);
//            }
//        }
//        else {
//            //listdata.getMaterialDesc()
//            webview.loadDataWithBaseURL(AppConstants.SERVER_URL, mParam1, "text/html; charset=utf-8", "UTF-8", null);
//        }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        try {
            JSONArray jsonArray=new JSONArray(mParam2);
            String alldata = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("material_embedded")!=""){
                    alldata += "<center bgcolor='#1faf66'>"+jsonObject.getString("material_embedded")+"</center></br>"+jsonObject.getString("title")+"</br>";
                }

                if (jsonObject.getString("material_embedded")==""&&jsonObject.getString("material_image")!=""){
                    alldata += "<center bgcolor='#1faf66'>"+jsonObject.getString("material_url")+jsonObject.getString("material_image")+"</center></br>"+jsonObject.getString("title")+"</br>";
                }
            }

            alldata = alldata.replaceAll("null","");
            webview.loadDataWithBaseURL("https://www.amazon.in",alldata,"text/html","UTF-8",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rooView;
    }

}
