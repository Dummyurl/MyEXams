package com.saxxis.myexamspace.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.helper.AppHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentAffaDetailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentAffaDetailedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String title;
    private String date;
    private String content;

    @BindView(R.id.detl_curraff_title)
    TextView txtTitle;
    @BindView(R.id.txtCreatedDate)
    TextView txtDate;

    @BindView(R.id.detl_curraff_description)
    WebView webviewDescript;

    public CurrentAffaDetailedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentAffaDetailedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentAffaDetailedFragment newInstance(String param1, String param2, String param3) {
        CurrentAffaDetailedFragment fragment = new CurrentAffaDetailedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            content = getArguments().getString(ARG_PARAM2);
            date = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_affa_detailed, container, false);
        ButterKnife.bind(this,view);

        txtTitle.setText(AppHelper.fromHtml(title));
        txtDate.setText(AppHelper.spanDateFormater(date));
        webviewDescript.getSettings().setDefaultTextEncodingName("utf-8");
        webviewDescript.getSettings().setJavaScriptEnabled(true);
        webviewDescript.getSettings().setMinimumLogicalFontSize(16);
        webviewDescript.getSettings().setAllowContentAccess(true);
        WebSettings webSettings = webviewDescript.getSettings();

        //  Log.d("response",mData.getDescription().toString());
        webviewDescript.loadDataWithBaseURL(AppConstants.SERVER_URL,content.replace("{googleads center}","").replace("{}",""),"text/html; charset=utf-8","UTF-8",null);

        return view;
    }

}
