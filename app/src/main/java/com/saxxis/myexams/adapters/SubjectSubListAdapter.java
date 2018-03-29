package com.saxxis.myexams.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.model.SubjectsSubCategories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by android2 on 6/13/2017.
 */

public class SubjectSubListAdapter extends RecyclerView.Adapter<SubjectSubListAdapter.MySubjectsSubHolder> {

    private Context context;
    private ArrayList<SubjectsSubCategories> mylist;
    public SubjectSubListAdapter(Context context, ArrayList<SubjectsSubCategories> mylist){
            this.context=context;
            this.mylist=mylist;
    }

    @Override
    public MySubjectsSubHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subjectslist_item,parent,false);
        return new MySubjectsSubHolder(view);
    }

    @Override
    public void onBindViewHolder(MySubjectsSubHolder holder, int position) {
        Picasso.with(context).load(AppConstants.SERVER_URL+mylist.get(position).getImage().replaceAll(" ","%20"))
        .fit()
        .placeholder(R.drawable.myexams_replacer)
        .error(R.drawable.myexams_replacer)
        .into(holder.subjectImage);

        holder.subjectTitle.setText(mylist.get(position).getTitle());
        if (!mylist.get(position).getDescription().equals(null)){
            holder.textDescription.setText(AppHelper.fromHtml(mylist.get(position).getDescription()));
            holder.textDescription.setVisibility(View.VISIBLE);
        }else {
            holder.textDescription.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return  mylist.size();
    }

    public class MySubjectsSubHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.subjectlist_image)
        ImageView subjectImage;

        @BindView(R.id.subject_title)
        TextView subjectTitle;

        @BindView(R.id.subject_description)
        TextView textDescription;

        public MySubjectsSubHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
