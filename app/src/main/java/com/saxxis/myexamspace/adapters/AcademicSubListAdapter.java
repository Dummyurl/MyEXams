package com.saxxis.myexamspace.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.model.AcademicSubList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by android2 on 6/13/2017.
 */

public class AcademicSubListAdapter extends RecyclerView.Adapter<AcademicSubListAdapter.AcademicHolder> {

    private Context context;
    private ArrayList<AcademicSubList> mlist;
    public AcademicSubListAdapter(Context context, ArrayList<AcademicSubList> mList){
        this.context=context;
        this.mlist=mList;
    }

    @Override
    public AcademicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.subjectslist_item,parent,false);
        return new AcademicHolder(view);
    }

    @Override
    public void onBindViewHolder(AcademicHolder holder, int position) {
        Picasso.with(context).load(AppConstants.SERVER_URL+mlist.get(position).getUploadlogo().replaceAll(" ","%20"))
                .fit()
                .placeholder(R.drawable.myexams_replacer)
                .error(R.drawable.myexams_replacer)
                .into(holder.subjectImage);

        holder.subjectTitle.setText(mlist.get(position).getSubjectName());
        holder.textDescription.setText(AppHelper.fromHtml(mlist.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class AcademicHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.subjectlist_image)
        ImageView subjectImage;

        @BindView(R.id.subject_title)
        TextView subjectTitle;

        @BindView(R.id.subject_description)
        TextView textDescription;

        public AcademicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
