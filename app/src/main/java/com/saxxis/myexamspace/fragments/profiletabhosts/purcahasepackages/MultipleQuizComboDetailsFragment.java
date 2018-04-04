package com.saxxis.myexamspace.fragments.profiletabhosts.purcahasepackages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.activities.MyPurchasedExamsActivity;
import com.saxxis.myexamspace.adapters.MySinglePackageDetails;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.helper.UserPrefs;
import com.saxxis.myexamspace.helper.utils.ItemClickSupport;
import com.saxxis.myexamspace.helper.utils.RecvDecors;
import com.saxxis.myexamspace.model.PackageData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link MultipleQuizComboDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultipleQuizComboDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<PackageData> combodata;
    private String mParam2;

    RecyclerView comborecyclerview;
    TextView noComboyet;
    private UserPrefs userPrefs;

    public MultipleQuizComboDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param combodata Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultipleQuizComboDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MultipleQuizComboDetailsFragment newInstance(ArrayList<PackageData> combodata, String param2) {
        MultipleQuizComboDetailsFragment fragment = new MultipleQuizComboDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, combodata);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            combodata = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purcahasepackages, container, false);

        userPrefs = new UserPrefs(getActivity());
        noComboyet =(TextView)view.findViewById(R.id.nocomboyet);

        if (!combodata.isEmpty()){
            noComboyet.setVisibility(View.GONE);
            comborecyclerview =(RecyclerView)view.findViewById(R.id.comborecyclerview);
            comborecyclerview.setHasFixedSize(true);
            comborecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            comborecyclerview.addItemDecoration(new RecvDecors(getActivity(),R.dimen.job_listing_main_offset));
            comborecyclerview.setAdapter(new MySinglePackageDetails(getActivity(),combodata));
            ItemClickSupport.addTo(comborecyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                    PackageData packageData = combodata.get(position);
                    String categoryname=packageData.getCategoryName()== "null" ? "" :packageData.getCategoryName();
                    String subjectname = packageData.getSubjectName() == "null" ? "" :"/"+packageData.getSubjectName();
                    String sublevel =packageData.getSublevelName() == "null" ? "" :"/"+packageData.getSublevelName();
                    String subchaptername =packageData.getSublevelName() == "null" ? "" : "/"+packageData.getSublevelName();

                    Intent intent = new Intent(getActivity(), MyPurchasedExamsActivity.class);
                    intent.putExtra("subchapterid",packageData.getQuiz_subchapter());
                    intent.putExtra("subjecttitle",categoryname+subjectname+sublevel+subchaptername);
                    intent.putExtra("subjecturl", AppConstants.MY_PACKAGE_COMBO_EXAM_LIST+userPrefs.getUserId()+"&subid="+packageData.getQuiz_subject());
                    getActivity().startActivity(intent);
                }
            });
        }

        if (combodata.isEmpty()){
               noComboyet.setVisibility(View.VISIBLE);
        }
        return view;
    }

}
