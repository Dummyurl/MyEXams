package com.saxxis.myexams.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.model.MyPackOrderListData;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 11/7/2017.
 */

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.MyOrderHolder> {

    private Context context;
    private ArrayList<MyPackOrderListData> datalist=new ArrayList<>();

    public MyOrderListAdapter(Context context,ArrayList<MyPackOrderListData> datalist){
        this.context=context;
        this.datalist=datalist;
    }
    @Override
    public MyOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myorder_item,parent,false);
        return new MyOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderHolder holder, int position) {
        holder.order_number.setText(datalist.get(position).getOrder_num());
        holder.order_status.setText(datalist.get(position).getOrder_status());
        holder.price.setText(context.getResources().getString(R.string.rupeecurrency)+datalist.get(position).getOrder_price());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyOrderHolder extends RecyclerView.ViewHolder{
        TextView order_number;
        TextView order_status;
        TextView price;

        public MyOrderHolder(View itemView) {
            super(itemView);
             order_number=(TextView)itemView.findViewById(R.id. order_number);
             order_status=(TextView)itemView.findViewById(R.id. order_status);
             price=(TextView)itemView.findViewById(R.id. price);
        }
    }

}
