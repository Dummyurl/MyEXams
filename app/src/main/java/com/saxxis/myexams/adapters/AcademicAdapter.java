package com.saxxis.myexams.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saxxis.myexams.R;
import com.saxxis.myexams.model.ExamIntAcadem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by android2 on 6/6/2017.
 */

public class AcademicAdapter extends RecyclerView.Adapter<AcademicAdapter.AcadecHolder> {

    Context context;
    ArrayList<ExamIntAcadem> list;


    public AcademicAdapter(Context context, ArrayList<ExamIntAcadem> list){
        this.context  = context;
        this.list = list;
    }

    @Override
    public AcadecHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exams_layout,parent,false);
        return new AcadecHolder(view);
    }

    @Override
    public void onBindViewHolder(AcadecHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getIconint())
                .placeholder(R.drawable.myexams_replacer)
                .error(R.drawable.myexams_replacer)
                .into(holder.acadec_icon);
        holder.acadec_name.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AcadecHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.exam_icon_image)
        ImageView acadec_icon;

        @BindView(R.id.txt_iconname)
        TextView acadec_name;

        public AcadecHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
