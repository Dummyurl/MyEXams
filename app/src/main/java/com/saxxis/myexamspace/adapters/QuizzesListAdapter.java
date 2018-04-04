package com.saxxis.myexamspace.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.activities.exampapers.ExamInstructsActivity;
import com.saxxis.myexamspace.helper.UserPrefs;
import com.saxxis.myexamspace.model.QuizzListList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by android2 on 6/14/2017.
 */

public class QuizzesListAdapter extends RecyclerView.Adapter<QuizzesListAdapter.MyQuizzListHolder> {

    private Context context;
    private ArrayList<QuizzListList> list;
    private UserPrefs userPrefs;
    public QuizzesListAdapter(Context context, ArrayList<QuizzListList>  list){
        this.context=context;
        userPrefs =new UserPrefs(context);
        this.list=list;
    }

    @Override
    public MyQuizzListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.noofquizzlist_item,parent,false);

        return new MyQuizzListHolder(view);
    }

    @Override
    public void onBindViewHolder(MyQuizzListHolder holder, int position) {

        final QuizzListList quizzListList=list.get(position);

        holder.txtTitle.setText(quizzListList.getQuizName());
        holder.txtNoOfQuestions.setText(quizzListList.getQuestionCount());
        holder.txtExamBoard.setText(quizzListList.getSubjectName());
        holder.txtNoofLang.setText(quizzListList.getExamLanguage());
        holder.txtTotalmarks.setText(quizzListList.getTotalExamMarks());
        holder.txtQuizTime.setText(quizzListList.getTotalExamTime());
        holder.txtExamType.setText(quizzListList.getTestExamType());

        if (quizzListList.getVersion().equals("1")){
            holder.starttest.setText("Take Test");

            holder.txtBanner.setText("Free");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                holder.txtBanner.setBackgroundColor(context.getResources().getColor(R.color.colorGreenDark,context.getTheme()));
//            }
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                holder.txtBanner.setBackgroundResource(R.drawable.gree_ribbon);
//            }
        }

        if (quizzListList.getVersion().equals("2")){
            holder.starttest.setText("Buy");

            holder.txtBanner.setText("Buy");
            holder.txtBanner.setBackgroundResource(R.drawable.red_ribbon);
        }

        holder.starttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizzListList.getVersion().equals("1")){
                    if (userPrefs.isLoggedIn()) {
                        Intent intent = new Intent(context.getApplicationContext(), ExamInstructsActivity.class);
                        intent.putExtra("quizId", quizzListList.getQuizId());
                        intent.putExtra("quizname", quizzListList.getQuizName());
                        ((Activity) context).startActivityForResult(intent, 100);
                        //context.startActivity(intent);
                    }
                    if (!userPrefs.isLoggedIn()){
                        Toast.makeText(context, "Please Login and Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyQuizzListHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.noofquizz_title)
        TextView txtTitle;

        @BindView(R.id.noofquizz_noofquestions)
        TextView txtNoOfQuestions;

        @BindView(R.id.noofquizz_examboard)
        TextView  txtExamBoard;

        @BindView(R.id.noofquizz_examlang)
        TextView    txtNoofLang;

        @BindView(R.id.noofquizz_examtype)
        TextView   txtExamType;

        @BindView(R.id.noofquizz_totalmarks)
        TextView     txtTotalmarks;

        @BindView(R.id.noofquizz_minutes)
        TextView  txtQuizTime;

        @BindView(R.id.noofquizz_taketest)
        Button starttest;

        @BindView(R.id.text_banner)
        TextView txtBanner;

        public MyQuizzListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
