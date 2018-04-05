package com.saxxis.myexamspace.activities.exampapers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.fragments.ExamInstructFragment;
import com.saxxis.myexamspace.fragments.SectionExamInstructFragment;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.helper.UserPrefs;
import com.saxxis.myexamspace.helper.utils.CustomViewPager;
import com.saxxis.myexamspace.model.MyResultsList;
import com.saxxis.myexamspace.model.Questions;
import com.saxxis.myexamspace.model.QuizSections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ExamInstructsActivity extends AppCompatActivity {
    CustomViewPager quizzviewpager;
    WebView instructuionsText;
    Button taketest;
    ScrollView instructionsLayout;
    TabLayout tabLayout;
    TextView timertxt, submit;

    UserPrefs userPrefs;
    private QuestionSectPagerAdapter mQuestionSectPagerAdapter;
    String questionPaperId, quizName;
    public static Bundle args = new Bundle();
    public static ArrayList<Questions> qiData;

    List<Fragment> mFragmentList = new ArrayList<>();

    private Handler handler = new Handler();
    private TimerTask timerTask;
    private Timer timertime;

    private boolean isExamStarted = false;

    String ticketId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_instructs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tiilbarTextView = (TextView)findViewById(R.id.soi_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userPrefs = new UserPrefs(ExamInstructsActivity.this);

        quizzviewpager = (CustomViewPager) findViewById(R.id.quizviewpagwer);
        tabLayout = (TabLayout) findViewById(R.id.qizztab);
        submit = (TextView) findViewById(R.id.submit);
        timertxt = (TextView)findViewById(R.id.timer);

//        next = (TextView)findViewById(R.id.next);

        Bundle extras=getIntent().getExtras();
        if (extras!=null){
            questionPaperId = extras.getString("quizId");
            quizName = extras.getString("quizname");
            tiilbarTextView.setText(quizName);
//            getSupportActionBar().setTitle(extras.getString("quizname"));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ExamInstructsActivity.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Are You Sure")
                        .setMessage("You Want To Submit The Exam ?")
                        .setPositiveButton("Yes, Submit",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        submitTotalExam();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        });
        AppHelper.showDialog(ExamInstructsActivity.this,"Loading Please Wait...");
        Log.e("response", AppConstants.QUESTIONS_LIST+questionPaperId+"&userid="+userPrefs.getUserId());
        StringRequest stringRequest = new StringRequest(AppConstants.QUESTIONS_LIST+questionPaperId+"&userid="+userPrefs.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        Log.e("response",response);
                            try{
                                final JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equals("ok")){
                                    final JSONObject descriptionObject = jsonObject.getJSONArray("description").getJSONObject(0);
                                    final JSONObject quizsubmitdetailsObject = jsonObject.getJSONArray("quizsubmitdetails").getJSONObject(0);

                                    String description = descriptionObject.optString("Description");
                                    final Long totalTime = descriptionObject.getLong("TotalTime");
                                    instructuionsText = (WebView) findViewById(R.id.test_instructions);
                                    instructuionsText.loadData(description,"text/html; charset=utf-8","UTF-8");
                                    taketest = (Button)findViewById(R.id.taketest);
                                    instructionsLayout = (ScrollView) findViewById(R.id.instructions_layout);
                                    instructionsLayout.setVisibility(View.VISIBLE);
                                    taketest.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            isExamStarted = true;
                                            instructionsLayout.setVisibility(View.GONE);
                                            tabLayout.setVisibility(View.VISIBLE);
                                            quizzviewpager.setVisibility(View.VISIBLE);
                                            submit.setVisibility(View.VISIBLE);
                                            qiData = new ArrayList<Questions>();
                                            mQuestionSectPagerAdapter = new QuestionSectPagerAdapter(getSupportFragmentManager());

                                            try {
                                                JSONObject dataObject  = jsonObject.getJSONObject("data");
                                                JSONArray quizinfo = dataObject.getJSONArray("quizinfo");
                                                JSONArray sectionsObject = dataObject.optJSONArray("sections");

                                                String StatisticsInfoId = quizsubmitdetailsObject.getString("StatisticsInfoId");
                                                ticketId = quizsubmitdetailsObject.getString("TicketId");

                                                for (int i = 0; i < quizinfo.length(); i++) {
                                                    JSONObject questionAnsObject = quizinfo.getJSONObject(i);
                                                    String QuestionId=questionAnsObject.getString("StatisticsId");
                                                    String Question =questionAnsObject.getString("Question");
                                                    String QuestionIndex =questionAnsObject.getString("QuestionIndex");
                                                    String QuestionVersionId = questionAnsObject.getString("QuestionVersionId");
                                                    String cscore = questionAnsObject.getString("cscore");
                                                    String wscore = questionAnsObject.getString("wscore");
                                                    String[] answers = questionAnsObject.getString("options").split("<answer id=");
                                                    String description = "";
                                                    if(questionAnsObject.has("Description")) {
                                                        description = questionAnsObject.getString("Description");
                                                    }
                                                    qiData.add(new Questions(QuestionId,Question,QuestionIndex,QuestionVersionId,answers, description, cscore, wscore));
                                                }

                                                if (sectionsObject==null){
                                                    for (int J = 0; J < qiData.size(); J++) {
                                                        int i2 = J + 1;
                                                        mQuestionSectPagerAdapter.addFragment(ExamInstructFragment.newInstance(qiData.get(J), "Question "+i2+" of "+qiData.size(),qiData.get(J).getCscore(),qiData.get(J).getWscore(), i2, StatisticsInfoId, ticketId), i2 + "");
                                                    }

                                                    quizzviewpager.setPagingEnabled(true);
                                                    quizzviewpager.setAdapter(mQuestionSectPagerAdapter);
                                                    tabLayout.setupWithViewPager(quizzviewpager);
                                                }

                                               ArrayList<QuizSections> sectionsData = new ArrayList<>();
                                               if (sectionsObject!=null){
                                                    for (int i = 0; i < sectionsObject.length(); i++) {
                                                        JSONObject sectionObject =  sectionsObject.getJSONObject(i);
                                                        sectionsData.add(new QuizSections(sectionObject.getString("id"),
                                                                sectionObject.getString("quizid"),
                                                                sectionObject.getString("time"),
                                                                sectionObject.getString("marks"),
                                                                sectionObject.getString("qcount"),
                                                                sectionObject.getString("qstart"),
                                                                sectionObject.getString("title"),
                                                                sectionObject.getString("qtime"),
                                                                sectionObject.getString("cscore"),
                                                                sectionObject.getString("wscore"),
                                                                sectionObject.getString("cutoffmarks")));
                                                    }

                                                   for (int i = 0; i < sectionsData.size(); i++) {
                                                       mQuestionSectPagerAdapter.addFragment(SectionExamInstructFragment.newInstance(qiData,sectionsData.get(i), StatisticsInfoId, ticketId, i),sectionsData.get(i).getTitle());
                                                   }

                                                   quizzviewpager.setPagingEnabled(false);
                                                   quizzviewpager.setAdapter(mQuestionSectPagerAdapter);
                                                   tabLayout.setupWithViewPager(quizzviewpager);
                                                }

                                       } catch (JSONException e) {
                                            e.printStackTrace();
                                       }
                                        /*timer started for given data*/
                                        startTimer(totalTime*1000);
                                    }
                                });
                            }
                        }catch (JSONException jse){

                        }
                        }
                    }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                   });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class QuestionSectPagerAdapter extends FragmentPagerAdapter {
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public QuestionSectPagerAdapter(FragmentManager fm){//}, ArrayList<Questions> mdata) {
            super(fm);
//            this.mdata=mdata;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
//                if (position < mdata.size()){
//                    return  String.valueOf(position+1);
//                }
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (isExamStarted && id==android.R.id.home){
            new AlertDialog.Builder(ExamInstructsActivity.this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("Are You Sure")
                    .setMessage("You Want To Submit The Exam ?")
                    .setPositiveButton("Yes, Submit",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    submitTotalExam();
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
//            super.onBackPressed();
            return true;
        }
        //return super.onOptionsItemSelected(item);
        super.onBackPressed();
        return true;
    }

    public void startTimer(Long totaltime) {
        new CountDownTimer(totaltime, 1000) {
            public void onTick(long millisUntilFinished) {
                timertxt.setText(String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                finish();
                submitTotalExam();
            }
        }.start();
    }

    public void showNextQuestion(int index) {
        quizzviewpager.setCurrentItem(index);
    }
    public void markTabForReview(int index) {
        TabLayout.Tab currentTab = tabLayout.getTabAt(index-1);
        LinearLayout customView = (LinearLayout) this.getLayoutInflater().inflate(R.layout.exam_tab_mark, null);

        currentTab.setCustomView(customView);
        currentTab.setText(currentTab.getText());
        //currentTab.setIcon(R.drawable.correct_ans);
    }
    public void submitTotalExam() {
        String URL = AppConstants.TOTAL_EXAM_SUBMIT+ticketId+"&userid="+userPrefs.getUserId();
        Log.e("Response", URL);
        AppHelper.showDialog(ExamInstructsActivity.this,"Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        Log.e("Response", response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray detailsArr = jsonObject.getJSONArray("quizdetails");
                            JSONObject objData = detailsArr.optJSONObject(0);
                            Log.e("quizdetails", objData.toString());
                            MyResultsList resultData = new MyResultsList(objData.getString("StatisticsInfoId"), objData.getString("QuizId"), objData.getString("UserId"), objData.getString("Status"), objData.getString("TicketId"),
                                    objData.getString("StartDate"), objData.getString("EndDate"), objData.getString("PassedScore"), objData.getString("UserScore"), objData.getString("MaxScore"),
                                    objData.getString("Passed"), objData.getString("CreatedDate"), objData.getString("QuestionCount"), objData.getString("TotalTime"), objData.getString("ResultEmailed"),
                                    quizName);

                            Intent intent = new Intent();
                            intent.putExtra("ticketId", resultData);
                            intent.putExtra("Quizname", quizName);
                            intent.putExtra("tabselection","viewresult");
                            setResult(100, intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }

}
