package com.saxxis.myexamspace.adapters;

import android.app.Activity;
import android.content.Context;
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
import com.saxxis.myexamspace.model.CurrentaffairsList;

import java.util.ArrayList;

/**
 * Created by android2 on 6/1/2017.
 */

public class CurrentAffairsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private Context context;
    private ArrayList<CurrentaffairsList>  listdata;

    private boolean isLoadingAdded = false;
    public OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading;
    private Activity activity;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;

    public CurrentAffairsAdapter(RecyclerView recyclerView, ArrayList<CurrentaffairsList>  listdata, Activity activity){
        this.activity=activity;
        this.listdata=listdata;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return listdata.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.currentlist_item, parent, false);
            return new MyCurrentHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_progress, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyCurrentHolder) {
            CurrentaffairsList contact = listdata.get(position);
            MyCurrentHolder viewHolder = (MyCurrentHolder) holder;
            viewHolder.txtTitle.setText(listdata.get(position).getTitle());
            viewHolder.txtCreatedDate.setText(AppHelper.spanDateFormater(listdata.get(position).getCreated()));
            viewHolder.txtDescript.setText(AppHelper.fromHtml(listdata.get(position).getDescription()));
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return listdata == null ? 0 : listdata.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class MyCurrentHolder extends RecyclerView.ViewHolder{

        TextView txtTitle,txtDescript,txtCreatedDate;

        public MyCurrentHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.curnt_title);
            txtDescript = (TextView) itemView.findViewById(R.id.curnt_descript);
            txtCreatedDate = (TextView) itemView.findViewById(R.id.txtCreatedDate);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.loadmore_progress);
        }
    }
}
