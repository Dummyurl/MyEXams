package com.saxxis.myexamspace.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.transition.ChangeBounds;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.activities.examsresults.MyResultsActivity;
import com.saxxis.myexamspace.model.MyResultsList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saxxis25 on 6/27/2017.
 */
public class MyResultsAdapter  extends RecyclerView.Adapter<MyResultsAdapter.MyResultHolder>{

    private Context context;
    private ArrayList<MyResultsList> mlist;
    private int mExpandedPosition=-1;
    RecyclerView recyclerView;

    public MyResultsAdapter(Context context, ArrayList<MyResultsList> mlist,RecyclerView recyclerView){
        this.context=context;
        this.mlist=mlist;
        this.recyclerView=recyclerView;
    }

    @Override
    public MyResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myresults_item,parent,false);
        return new MyResultHolder(view);
    }

    @Override
    public void onBindViewHolder(MyResultHolder holder, final int position) {
        final boolean isExpanded = position==mExpandedPosition;
        holder.details.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.quizTitle.setActivated(isExpanded);
        holder.quizTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                ChangeBounds transition = new ChangeBounds();
                transition.setDuration(125);
                notifyDataSetChanged();
            }
        });

        holder.quizTitle.setText(mlist.get(position).getQuizname());
        holder.txtQuizNo.setText(position+1+"");
        holder.quizView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyResultsActivity.class);
                intent.putExtra("ticketId",mlist.get(position));
                intent.putExtra("Quizname",mlist.get(position).getQuizname());
                intent.putExtra("tabselection","viewresult");
                context.startActivity(intent);
            }
        });
        holder.quizAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), MyResultsActivity.class);
                intent.putExtra("ticketId",mlist.get(position));
                intent.putExtra("Quizname",mlist.get(position).getQuizname());
                intent.putExtra("tabselection","analysis");
                context.startActivity(intent);
            }
        });
        holder.quizExplanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), MyResultsActivity.class);
                intent.putExtra("ticketId",mlist.get(position));
                intent.putExtra("Quizname",mlist.get(position).getQuizname());
                intent.putExtra("tabselection","explanation");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyResultHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.quiz_no)
        TextView txtQuizNo;
        @BindView(R.id.quiz_title)
        TextView quizTitle;
        @BindView(R.id.quiz_explanation)
        TextView quizExplanation;
        @BindView(R.id.quiz_analysis)
        TextView quizAnalysis;
        @BindView(R.id.quizview)
        TextView quizView;
        @BindView(R.id.details)
        LinearLayout details;

        public MyResultHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
