package com.saxxis.myexams.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.interfaces.OnLoadMoreListener;
import com.saxxis.myexams.model.MustRead;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 9/19/2017.
 */

public class MustReadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 10;
    private int lastVisibleItem,totalItemCount;

    private Activity activity;
    private ArrayList<MustRead> currAffairsArraylist;

    public MustReadAdapter(RecyclerView mRecyclerView, ArrayList<MustRead> currAffairsArraylist, Activity activity){
        this.activity = activity;
        this.currAffairsArraylist = currAffairsArraylist;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return currAffairsArraylist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    if (viewType == VIEW_TYPE_ITEM){
        View view = LayoutInflater.from(activity).inflate(R.layout.currentlist_item,parent,false);
        return new MustReadViewHolder(view);
    } else if (viewType == VIEW_TYPE_LOADING) {
        View view= LayoutInflater.from(activity).inflate(R.layout.progressbar_item,parent,false);
        return new MyProgressHolder(view);
    }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MustReadViewHolder) {
            MustReadViewHolder educationViewHolder = (MustReadViewHolder) holder;
            MustRead eduNews = currAffairsArraylist.get(position);
            System.out.println(eduNews.getTitle()+":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            educationViewHolder.txtTitle.setText(eduNews.getTitle());
            educationViewHolder.txtCurrentDate.setText(AppHelper.spanDateFormater(eduNews.getCreated()));
            educationViewHolder.txtDescription.setText(AppHelper.fromHtml(eduNews.getIntrotext()));
        }

        if (holder instanceof MyProgressHolder){
            MyProgressHolder myProgressHolder=(MyProgressHolder)holder;
            myProgressHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return currAffairsArraylist == null ? 0 : currAffairsArraylist.size();
    }

    public class MustReadViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtCurrentDate;
        TextView txtDescription;

        public MustReadViewHolder(View itemView) {
            super(itemView);
            txtTitle=(TextView)itemView.findViewById(R.id.curnt_title);
            txtCurrentDate=(TextView)itemView.findViewById(R.id.txtCreatedDate);
            txtDescription=(TextView)itemView.findViewById(R.id.curnt_descript);
        }
    }

    public class MyProgressHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public MyProgressHolder(View itemView) {
            super(itemView);
            progressBar=(ProgressBar)itemView.findViewById(R.id.dataprogress);
        }
    }


    public void setLoaded() {
        isLoading = false;
    }
}
