package com.saxxis.myexams.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.saxxis.myexams.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamPapersActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_papers)
    Toolbar papers_toolbar;

    @BindView(R.id.questions_selection)
    TextView questions_selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_papers);
        ButterKnife.bind(this);
        setSupportActionBar(papers_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @OnClick(R.id.questions_selection)
    void questionsclick(){
//        Intent intent=new Intent(ExamPapersActivity.this, DialogActivity.class);
//        startActivity(intent);
    }

}
