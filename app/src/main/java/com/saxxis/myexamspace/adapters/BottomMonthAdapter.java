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
import java.util.List;

/**
 * Created by saxxis25 on 10/13/2017.
 */

public class BottomMonthAdapter extends RecyclerView.Adapter<BottomMonthAdapter.BottomItemHolder> {

    private ArrayList<QuizzFilterMonths> mData;
    private Context context;
    private List<String> mSelected;

    public BottomMonthAdapter(Context context) {
        this.mData = new ArrayList<>();
        this.context = context;
        mSelected = new ArrayList<>();
    }

    @Override
    public BottomItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottomlayout_filteritem, parent, false);
        return new BottomItemHolder(view);
    }

    @Override
    public void onBindViewHolder(BottomItemHolder holder, final int position) {
        QuizzFilterMonths quizzFilterMonths = mData.get(position);

        holder.txtBottomItem.setText(quizzFilterMonths.getExamtypename());

        if (mSelected.contains(quizzFilterMonths.getExamtypeid())) {
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
                    mSelected.remove(mData.get(position).getExamtypeid());
                }
            }
        });
    }

    public List<String> getSelected() {
        return mSelected;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItems(List<QuizzFilterMonths> quizMonthItems) {
        mData.addAll(quizMonthItems);
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
