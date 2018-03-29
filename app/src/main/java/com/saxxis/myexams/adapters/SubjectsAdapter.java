package com.saxxis.myexams.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.model.SubjectsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by android2 on 6/12/2017.
 */

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.Subjectsholder> {


    Context context;
    ArrayList<SubjectsList> mData;



    public SubjectsAdapter(Context context, ArrayList<SubjectsList> mData){
        this.context=context;
        this.mData=mData;
    }

    @Override
    public Subjectsholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exams_layout,parent,false);
        return new Subjectsholder(view);
    }

    @Override
    public void onBindViewHolder(Subjectsholder holder, int position) {
        holder.txtExamName.setText(mData.get(position).getTitle());
        Picasso.with(context).load(mData.get(position).getSubjectImage().replaceAll(" ","%20"))
                .fit()
                .placeholder(R.drawable.myexams_replacer)
                .error(R.drawable.myexams_replacer)
                .into(holder.imageExamIcoan);

//        holder.imageExamIcoan.setImageResource(R.drawable.entrance);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Subjectsholder extends RecyclerView.ViewHolder{

        @BindView(R.id.exam_icon_image)
        ImageView imageExamIcoan;

        @BindView(R.id.txt_iconname)
        TextView txtExamName;

        public Subjectsholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
