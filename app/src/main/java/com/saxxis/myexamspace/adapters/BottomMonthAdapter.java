package com.saxxis.myexamspace.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.model.QuizzFilterMonths;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 10/13/2017.
 */

public class BottomMonthAdapter extends RecyclerView.Adapter<BottomMonthAdapter.BottomItemHolder> {

    ArrayList<QuizzFilterMonths> mData;
    Context context;
    public BottomMonthAdapter(ArrayList<QuizzFilterMonths> mData, Context context){
        this.mData=mData;
        this.context=context;
    }

    @Override
    public BottomItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottomlayout_filteritem,parent,false);
        return new BottomItemHolder(view);
    }

    @Override
    public void onBindViewHolder(BottomItemHolder holder, final int position) {
        holder.txtBottomItem.setText(mData.get(position).getExamtypename());
        holder.txtBottomItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    QuizzFilterMonths.selectedId.add(mData.get(position).getExamtypeid());
                }
                if (!isChecked){
                    for (int i = 0; i < QuizzFilterMonths.selectedId.size(); i++) {
                        if (mData.get(position).getExamtypeid().equals(QuizzFilterMonths.selectedId.get(i))){
                            QuizzFilterMonths.selectedId.remove(i);
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class BottomItemHolder extends RecyclerView.ViewHolder{
        CheckBox txtBottomItem;
        public BottomItemHolder(View itemView) {
            super(itemView);
            txtBottomItem = (CheckBox)itemView.findViewById(R.id.btmitm_text);

        }
    }
}
