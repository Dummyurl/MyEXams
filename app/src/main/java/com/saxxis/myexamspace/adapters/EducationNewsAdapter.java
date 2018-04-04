package com.saxxis.myexamspace.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.interfaces.OnLoadMoreListener;
import com.saxxis.myexamspace.model.EducationNews;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 9/19/2017.
 */

public class EducationNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private Activity activity;
    private int visibleThreshold = 1;
    private int lastVisibleItem,totalItemCount;

    private ArrayList<EducationNews> currAffairsArraylist;

    public EducationNewsAdapter(RecyclerView mRecyclerView, ArrayList<EducationNews> currAffairsArraylist, Activity activity){
        this.activity=activity;
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
        return new EducationViewHolder(view);
    } else if (viewType == VIEW_TYPE_LOADING) {
        View view= LayoutInflater.from(activity).inflate(R.layout.progressbar_item,parent,false);
        return new MyProgressHolder(view);
    }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof EducationViewHolder) {
            EducationViewHolder educationViewHolder = (EducationViewHolder) holder;
            EducationNews eduNews = currAffairsArraylist.get(position);
            educationViewHolder.txtTitle.setText(eduNews.getTitle());
            educationViewHolder.txtCurrentDate.setText(AppHelper.spanDateFormater(eduNews.getPublish_up()));
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

    public class EducationViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtCurrentDate;
        TextView txtDescription;

        public EducationViewHolder(View itemView) {
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
