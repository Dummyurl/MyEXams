package com.saxxis.myexams.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saxxis.myexams.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamDescriptionFragment extends Fragment {


    public ExamDescriptionFragment() {
        // Required empty public constructor
    }

    public ExamDescriptionFragment getInstance(){
        return new ExamDescriptionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_exam_description, container, false);


        return rootview;
    }

}
