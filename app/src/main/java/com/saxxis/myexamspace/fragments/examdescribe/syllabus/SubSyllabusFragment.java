package com.saxxis.myexamspace.fragments.examdescribe.syllabus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubSyllabusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubSyllabusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    WebView webView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SubSyllabusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubSyllabusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubSyllabusFragment newInstance(String param1, String param2) {
        SubSyllabusFragment fragment = new SubSyllabusFragment();
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
        View view = inflater.inflate(R.layout.fragment_sub_syllabus, container, false);
        webView =(WebView) view.findViewById(R.id.subsyllabus_data);

        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMinimumLogicalFontSize(19);
        webView.getSettings().setAllowContentAccess(true);
        webView.loadDataWithBaseURL(AppConstants.SERVER_URL, mParam1, "text/html; charset=utf-8", "UTF-8", null);

        return view;
    }

}
