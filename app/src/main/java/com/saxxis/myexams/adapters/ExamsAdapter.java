package com.saxxis.myexams.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.model.ExamTypes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by android2 on 5/23/2017.
 */

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.MyExamsHolder> {

    Context context;
    ArrayList<ExamTypes> mData;

    public ExamsAdapter(Context context, ArrayList<ExamTypes> mData){
        this.context=context;
        this.mData=mData;
    }

    @Override
    public MyExamsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.exams_layout,parent,false);
        return new MyExamsHolder(view);
    }

    @Override
    public void onBindViewHolder(MyExamsHolder holder, int position) {

        Picasso.with(context).load(mData.get(position).getImage_src())
                .fit()
                .placeholder(R.drawable.myexams_replacer)
                .error(R.drawable.myexams_replacer)
                .into(holder.imageView);
//        holder.imageView.setImageResource();
        holder.txt_examname.setText(mData.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyExamsHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.exam_icon_image)
        ImageView imageView ;

        @BindView(R.id.txt_iconname)
        TextView txt_examname;

        public MyExamsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
