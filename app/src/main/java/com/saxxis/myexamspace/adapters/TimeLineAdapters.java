package com.saxxis.myexamspace.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.activities.TimeLineActivity;
import com.saxxis.myexamspace.model.TimeLineModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by saxxis25 on 7/31/2017.
 */

public class TimeLineAdapters extends RecyclerView.Adapter<TimeLineAdapters.MyTimeLineHolder> {

    private Context context;
    private ArrayList<TimeLineModel> timelinelist;
    public TimeLineAdapters(Context context,ArrayList<TimeLineModel> timelinelist){
                this.context=context;
                this.timelinelist=timelinelist;
    }

    @Override
    public MyTimeLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View timelineitem = LayoutInflater.from(context).inflate(R.layout.timeline_item,parent,false);
        return new MyTimeLineHolder(timelineitem);
    }

    @Override
    public void onBindViewHolder(final MyTimeLineHolder holder, final int position) {
        holder.textSam.setText(timelinelist.get(position).getTitle());

        SimpleDateFormat sdf=null;
        if(timelinelist.get(position).getTimelinedate().length()==10){
            sdf = new SimpleDateFormat("dd-MM-yyyy");
        }if(timelinelist.get(position).getTimelinedate().length()==19){
            sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        }

        if (!timelinelist.get(position).getTimelinedate().equals("")){
            try {
                Date givendate = sdf.parse(timelinelist.get(position).getTimelinedate());
                Date date = new Date();
                if (givendate.before(date)){
                    holder.textSam.setBackgroundResource(R.color.grey_bg);
                    //                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //                    holder.textSam.setBackgroundColor(context.getResources().getColor(R.color.blue,context.getTheme()));
                    //                }
                    //                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    //                    holder.textSam.setBackgroundColor(context.getResources().getColor(R.color.blue));
                    //                }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        holder.textSam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timelinelist.get(position).getLinks().equals("")) {
                    Intent intent = new Intent(context, TimeLineActivity.class);
                    intent.putExtra("linktext",timelinelist.get(position).getLinks());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return timelinelist.size();
    }

    public class MyTimeLineHolder extends RecyclerView.ViewHolder{
        TextView textSam;
        public MyTimeLineHolder(View itemView) {
            super(itemView);
            textSam =(TextView)itemView.findViewById(R.id.text_sam);
        }
    }

}
