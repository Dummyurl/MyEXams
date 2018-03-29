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
import com.saxxis.myexams.activities.OrderDetailsActivity;
import com.saxxis.myexams.adapters.MyOrderListAdapter;
import com.saxxis.myexams.helper.utils.ItemClickSupport;
import com.saxxis.myexams.helper.utils.RecvDecors;
import com.saxxis.myexams.model.MyPackOrderListData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<MyPackOrderListData> orderlist;
    private String mParam2;

    private RecyclerView recvOrderList;
    private TextView noExamYet;


    public OrderListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param orderlist Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderListFragment newInstance(ArrayList<MyPackOrderListData> orderlist , String param2) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, orderlist);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderlist = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        recvOrderList = (RecyclerView)view.findViewById(R.id.recv_orderlist);
        noExamYet = (TextView)view.findViewById(R.id.noexamyet);

        if (!orderlist.isEmpty()){
            noExamYet.setVisibility(View.GONE);
            recvOrderList.setHasFixedSize(true);
            recvOrderList.setLayoutManager(new LinearLayoutManager(getActivity()));
            recvOrderList.addItemDecoration(new RecvDecors(getActivity(),R.dimen.job_listing_main_offset));
            recvOrderList.setAdapter(new MyOrderListAdapter(getActivity(),orderlist));

            ItemClickSupport.addTo(recvOrderList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(getActivity(),OrderDetailsActivity.class);
                    intent.putExtra("orderdetails",orderlist.get(position));
    //                intent.putExtra("ordernumber",orderlist.get(position).getOrder_num());
                    startActivity(intent);
                }
            });
        }

        if (orderlist.isEmpty()){
            noExamYet.setVisibility(View.VISIBLE);
        }
        return view;
    }


}
