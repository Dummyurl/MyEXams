package com.saxxis.myexams.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.model.TopRanksList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saxxis25 on 10/10/2017.
 */

public class SummaryAnalysisAdapter extends RecyclerView.Adapter<SummaryAnalysisAdapter.SummaryAnalysiskHolder> {

    Context context;
    ArrayList<TopRanksList> mData;
    public SummaryAnalysisAdapter(Context context, ArrayList<TopRanksList> mData){
        this.context=context;
        this.mData=mData;
    }

    @Override
    public SummaryAnalysiskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.top_rank_item,parent,false);
        return new SummaryAnalysiskHolder(view);
    }

    @Override
    public void onBindViewHolder(SummaryAnalysiskHolder holder, int position) {

        TopRanksList topRanksList =mData.get(position);
        holder.txtname.setText(topRanksList.getName());
        holder.txtscore.setText(topRanksList.getUserScore());
        holder.txtrank.setText(topRanksList.getRank());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SummaryAnalysiskHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name)TextView txtname;
        @BindView(R.id.score)TextView txtscore;
        @BindView(R.id.rank)TextView txtrank;

        public SummaryAnalysiskHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
