package com.saxxis.myexamspace.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.model.QuizzFilterItems;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 10/17/2017.
 */

public class ItemSelectAdapter extends RecyclerView.Adapter<ItemSelectAdapter.ItemHolder> {

    Context context;
    ArrayList<QuizzFilterItems> quizzFilterItemses;

    public ItemSelectAdapter(Context context,ArrayList<QuizzFilterItems> quizzFilterItemses){
        this.context=context;
        this.quizzFilterItemses = quizzFilterItemses;
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.itemtype.setText(quizzFilterItemses.get(position).getExamtypename());
    }

    @Override
    public int getItemCount() {
        return quizzFilterItemses.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView itemtype;

        public ItemHolder(View itemView) {
            super(itemView);
            itemtype = (TextView)itemView.findViewById(R.id.item_type);
        }
    }
}
