package com.saxxis.myexams.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.saxxis.myexams.R;
import com.saxxis.myexams.model.QuizzFilterItems;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 10/13/2017.
 */

public class BottomItemAdapter extends RecyclerView.Adapter<BottomItemAdapter.BottomItemHolder> {

    ArrayList<QuizzFilterItems> mData;
    Context context;
    public BottomItemAdapter(ArrayList<QuizzFilterItems> mData, Context context){
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
                    QuizzFilterItems.selectedId.add(mData.get(position).getExamtypeid());
                }
                if (!isChecked){
                    for (int i = 0; i < QuizzFilterItems.selectedId.size(); i++) {
                        if (mData.get(position).getExamtypeid().equals(QuizzFilterItems.selectedId.get(i))){
                            QuizzFilterItems.selectedId.remove(i);
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
