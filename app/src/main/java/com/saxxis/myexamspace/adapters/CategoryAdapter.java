package com.saxxis.myexamspace.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.model.Category;

import java.util.ArrayList;

/**
 * Created by saxxis25 on 9/20/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    Context context;
    ArrayList<Category> mData;
    public CategoryAdapter(Context context, ArrayList<Category> mData){
        this.context=context;
        this.mData=mData;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.categoryName.setText(mData.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{

        TextView categoryName;

        public CategoryHolder(View itemView) {
            super(itemView);
            categoryName = (TextView)itemView.findViewById(R.id.categoryname);
        }
    }
}
