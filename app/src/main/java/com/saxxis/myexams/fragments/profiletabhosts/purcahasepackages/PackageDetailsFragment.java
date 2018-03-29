package com.saxxis.myexams.fragments.profiletabhosts.purcahasepackages;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.activities.MyPurchasedExamsActivity;
import com.saxxis.myexams.adapters.MySinglePackageDetails;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.helper.utils.ItemClickSupport;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.PackageData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PackageDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PackageDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recv_packagedetails;
    TextView noPackagesYet;

    // TODO: Rename and change types of parameters
    private ArrayList<PackageData> mParam1;
    private String mParam2;


    public PackageDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param data Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PackageDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PackageDetailsFragment newInstance(ArrayList<PackageData> data, String param2) {
        PackageDetailsFragment fragment = new PackageDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, data);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package_details, container, false);

        noPackagesYet =(TextView)view.findViewById(R.id.nopackageyet);

        if (!mParam1.isEmpty()) {
            noPackagesYet.setVisibility(View.GONE);
            recv_packagedetails = (RecyclerView) view.findViewById(R.id.recv_packagedetails);
            recv_packagedetails.setHasFixedSize(true);
            recv_packagedetails.setLayoutManager(new LinearLayoutManager(getActivity()));
            recv_packagedetails.addItemDecoration(new RecvDecors(getActivity(), R.dimen.job_listing_main_offset));
            recv_packagedetails.setAdapter(new MySinglePackageDetails(getActivity(), mParam1));

            ItemClickSupport.addTo(recv_packagedetails).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(getActivity(), MyPurchasedExamsActivity.class);
                    intent.putExtra("subchapterid", mParam1.get(position).getQuiz_subchapter());
                    intent.putExtra("subjecttitle", mParam1.get(position).getSublevelName());
                    intent.putExtra("subjecturl", AppConstants.MY_PACKAGE_SINGLE_EXAMS_LIST + mParam1.get(position).getQuiz_subchapter());
                    getActivity().startActivity(intent);
                }
            });
        }
        if (mParam1.isEmpty()){
            noPackagesYet.setVisibility(View.VISIBLE);
        }
        return view;
    }

}
