package com.saxxis.myexamspace.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.model.QuizzFilterItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saxxis25 on 10/13/2017.
 */

public class BottomItemAdapter extends RecyclerView.Adapter<BottomItemAdapter.BottomItemHolder> {

    private ArrayList<QuizzFilterItems> mData;
    private Context context;
    private List<String> mSelected;

    public BottomItemAdapter(Context context) {
        mData = new ArrayList<>();
        this.context = context;
        mSelected = new ArrayList<>();
    }

    @Override
    public BottomItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottomlayout_filteritem, parent, false);
        return new BottomItemHolder(view);
    }

    public List<String> getSelected() {
        return mSelected;
    }

    @Override
    public void onBindViewHolder(BottomItemHolder holder, final int position) {
        holder.txtBottomItem.setText(mData.get(position).getExamtypename());

        if (mSelected.contains(mData.get(position).getExamtypeid())) {
            holder.txtBottomItem.setOnCheckedChangeListener(null);
            holder.txtBottomItem.setChecked(true);
        } else {
            holder.txtBottomItem.setOnCheckedChangeListener(null);
            holder.txtBottomItem.setChecked(false);
        }

        holder.txtBottomItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSelected.add(mData.get(position).getExamtypeid());
                } else {
                    mSelected.remove(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItems(List<QuizzFilterItems> quizzFilteritems) {
        mData.addAll(quizzFilteritems);
        notifyDataSetChanged();
    }

    public class BottomItemHolder extends RecyclerView.ViewHolder {
        CheckBox txtBottomItem;

        public BottomItemHolder(View itemView) {
            super(itemView);
            txtBottomItem = (CheckBox) itemView.findViewById(R.id.btmitm_text);

        }
    }
}
