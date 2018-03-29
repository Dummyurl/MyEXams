package com.saxxis.myexams.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.model.NotifiDataResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saxxis25 on 11/3/2017.
 */

public class UpComingNotificasListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<NotifiDataResult> mdata;
    public static final int MENU_ITEM_VIEW_TYPE = 0;
    public static final int AD_VIEW_TYPE = 1;

    public UpComingNotificasListAdapter(Context context, ArrayList<NotifiDataResult> mdata){
        this.context=context;
        this.mdata=mdata;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return (position%4==0)?AD_VIEW_TYPE:MENU_ITEM_VIEW_TYPE;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        switch (viewType){
//            case AD_VIEW_TYPE:
//                View aditem  = LayoutInflater.from(context).inflate(R.layout.adview_layout,parent,false);
//                return new AdViewHolder(aditem);
//            case MENU_ITEM_VIEW_TYPE:
        View viewitem= LayoutInflater.from(context).inflate(R.layout.upcoming_notifica_item,parent,false);
        return new NotifyHolder(viewitem);
//                default:
//                    View view=LayoutInflater.from(context).inflate(R.layout.notifications_layout,parent,false);
//                    return new NotifyHolder(view);
//        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        int itemType = getItemViewType(position);
        NotifyHolder notifyHolder =(NotifyHolder)holder;
        notifyHolder.txtTitle.setText(mdata.get(position).getTitle());
//        notifyHolder.txtPostDate.setText(AppHelper.getddMMyyyy(mdata.get(position).getPosting_date()));
        notifyHolder.txtCategory.setText(mdata.get(position).getCategory());
        notifyHolder.txtRecruitmentboard.setText(mdata.get(position).getCompany());
        notifyHolder.txtQualification.setText(mdata.get(position).getQualification());
//        notifyHolder.txtStartDate.setText(AppHelper.getddMMMyyyy(mdata.get(position).getCur_date()));
//        notifyHolder.txtEndDate.setText(AppHelper.getddMMyyyy(mdata.get(position).getClosing_date()));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class NotifyHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.notifi_title)
        TextView txtTitle;

//      @BindView(R.id.notifi_postdata)
//      TextView txtPostDate;

        @BindView(R.id.notifi_category)
        TextView txtCategory;

        @BindView(R.id.notifi_recruitboard)
        TextView txtRecruitmentboard;

        @BindView(R.id.notifi_qual)
        TextView txtQualification;

//        @BindView(R.id.notifi_startdate)
//        TextView txtStartDate;
//
//        @BindView(R.id.notifi_enddate)
//        TextView txtEndDate;

        public NotifyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

//
//    public class AdViewHolder extends RecyclerView.ViewHolder{
//
//        NativeExpressAdView adViewOne;
//
//        public AdViewHolder(View itemView) {
//            super(itemView);
//            adViewOne=(NativeExpressAdView)itemView.findViewById(adView);
//        }
//    }

}
