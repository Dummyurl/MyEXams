package com.saxxis.myexams.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.saxxis.myexams.R;
import com.saxxis.myexams.adapters.BottomItemAdapter;
import com.saxxis.myexams.adapters.BottomMonthAdapter;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.fragments.myresult.subexplanation.SubQuizSegmentQuestionFragment;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.utils.CustomViewPager;
import com.saxxis.myexams.model.CurrentAffairsQuizz;
import com.saxxis.myexams.model.QuizzFilterItems;
import com.saxxis.myexams.model.QuizzFilterMonths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrentAffairsQuizzActivity extends AppCompatActivity {

//    @BindView(R.id.fab_selection)
//    FloatingActionButton floatingActionButton;

    @BindView(R.id.currentaffairsquizztoolbar)
    Toolbar toolbar;

    @BindView(R.id.customviewpager)
    CustomViewPager customViewPager;

    @BindView(R.id.select_language)
    TextView selectLanguage;

    @BindView(R.id.select_month)
    TextView getSelectMonth;

    @BindView(R.id.select_examtype)
    TextView selectExamType;

    @BindView(R.id.txtExamType)
    TextView txtExamType;

    @BindView(R.id.previous)
    TextView previous;

    @BindView(R.id.next)
    TextView next;

    FragmentManager fragmentManager;

    int language=1;
    String monthYear="";
    SectionPagerAdapter adapter;
    ArrayList<CurrentAffairsQuizz> quixxData;


    ArrayList<String> selecteddates = new ArrayList<>();
    boolean[] checkedColors = new boolean[13];

//    Bottom sheet items
    BottomSheetBehavior behavior;
    RecyclerView recyclerView;
    TextView cancelText,filterCategoryText, filterMonthText;
    private BottomItemAdapter mBottomAdapter;
    private BottomMonthAdapter mBottomMonthAdapter;
    String categorySelected="", monthSelected="";
    ArrayList<QuizzFilterItems> quizzFilteritems = new ArrayList<>();
    ArrayList<QuizzFilterMonths> quizMonthItems = new ArrayList<>();

    private AdView mAdView;

//     horizontallview components
//    @BindView(R.id.horizontalmonthyear)
//    HorizontalScrollView horizontallView;

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_affairs_quizz);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView)findViewById(R.id.adbannerquizzView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*
        * analytics tracker */

        mTracker = MyExamsApp.getMyInstance().getDefaultTracker();
        mTracker.setScreenName("Current Affairs Quizz");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
       // monthYear = "&months[]="+AppHelper.getSelectMonth(new Date());

        quixxData = new ArrayList<>();
        getAllData("","");

        fragmentManager = getSupportFragmentManager();

        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        cancelText =(TextView)bottomSheet.findViewById(R.id.cancel_filter);
        filterCategoryText =(TextView)bottomSheet.findViewById(R.id.filter_selection);
        filterMonthText =(TextView)bottomSheet.findViewById(R.id.filter_month);
        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        filterCategoryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                String categorydata="";
                for (int i = 0; i <= QuizzFilterItems.selectedId.size(); i++) {
                    if (i!=QuizzFilterItems.selectedId.size()){
                        Log.e("response",QuizzFilterItems.selectedId.get(i));
                        categorydata += "&catids[]="+QuizzFilterItems.selectedId.get(i);
                    }
                    if (i==QuizzFilterItems.selectedId.size()){
                        categorySelected = categorydata;
                        getAllData(monthYear,categorydata);
                    }
                }
            }
        });
        filterMonthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                String monthdata="";
                Log.e("month", Integer.toString(QuizzFilterMonths.selectedId.size()     ));
                for (int i = 0; i <= QuizzFilterMonths.selectedId.size(); i++) {
                    if (i!=QuizzFilterMonths.selectedId.size()){
                        Log.e("response",QuizzFilterMonths.selectedId.get(i));
                        monthdata += "&months[]="+QuizzFilterMonths.selectedId.get(i);
                    }
                    if (i==QuizzFilterMonths.selectedId.size()){
                        monthSelected = monthdata;
                        Log.e("month", monthdata);
                        getAllData(monthdata,"");
                    }
                }
            }
        });

        recyclerView = (RecyclerView)bottomSheet.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StringRequest stringRequest = new StringRequest(AppConstants.FILTER_CURRENT_AFFAIRSQUIZZ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response",response);
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray datajsonArray=jsonObject.getJSONArray("data");

                            for (int i = 0; i < datajsonArray.length(); i++) {
                                    JSONObject dataObject = datajsonArray.getJSONObject(i);
                                    quizzFilteritems.add(new QuizzFilterItems(dataObject.getString("examtypeid"),
                                            dataObject.getString("examtypename")));
                            }

                            mBottomAdapter = new BottomItemAdapter(quizzFilteritems, CurrentAffairsQuizzActivity.this);
                            recyclerView.setAdapter(mBottomAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        }
                });

       MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);

//        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            }
//        });



    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void getAllData(String months,String quizid){
        Log.e("response", AppConstants.CURRENT_AFFAIRS_QUIZZ+language+months+quizid);
        AppHelper.showDialog(CurrentAffairsQuizzActivity.this,"Loading Please Wait...");
        System.out.println(AppConstants.CURRENT_AFFAIRS_QUIZZ+language+monthSelected+categorySelected);
        StringRequest stringRequest = new StringRequest(AppConstants.CURRENT_AFFAIRS_QUIZZ+language+months+quizid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response",response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray datajsonArray = jsonObject.getJSONArray("data");
                            if (datajsonArray.length()==0){
                                Toast.makeText(CurrentAffairsQuizzActivity.this, "Question are not found", Toast.LENGTH_SHORT).show();
                            }

                            if (fragmentManager.getFragments() != null) {
                                fragmentManager.getFragments().clear();

                                List<Fragment> fragments = fragmentManager.getFragments();
                                if (fragments != null) {
                                    FragmentTransaction ft = fragmentManager.beginTransaction();
                                    for (Fragment f : fragments) {
                                        //You can perform additional check to remove some (not all) fragments:
                                        if (f instanceof SubQuizSegmentQuestionFragment) {
                                            ft.remove(f);
                                        }
                                    }

                                    customViewPager.removeAllViewsInLayout();
                                    customViewPager.removeAllViews();
                                    ft.commitAllowingStateLoss();
                                }
                                adapter.notifyDataSetChanged();

                                if (quixxData!=null){
                                    quixxData.clear();
                                }
                            }
                            adapter = new SectionPagerAdapter(fragmentManager);
                            quixxData.clear();

                            for (int i = 0; i < datajsonArray.length(); i++) {
                                JSONObject dataObject = datajsonArray.getJSONObject(i);
                                quixxData.add(new CurrentAffairsQuizz(dataObject.getString("Question"), dataObject.getString("Solution"), dataObject.getString("QuestionVersionId"), dataObject.getString("Data"), dataObject.getString("comp"), dataObject.getString("CompId"),
                                        dataObject.getString("Score"), dataObject.getString("PublishedDate"), dataObject.getString("status"), dataObject.getString("QlanguageType"), dataObject.getString("QuestionType"),
                                        dataObject.getString("ClassName"), dataObject.getString("Created"), dataObject.getString("QtypeName"), dataObject.getString("QlevelName"), dataObject.getString("examtypename"),
                                        dataObject.getString("CategoryName"), dataObject.getString("SubjectName"), dataObject.getString("SublevelName"), dataObject.getString("SubchapterName"), dataObject.getString("fevorite")));
                            }
System.out.println("Questions size:::::::::::::::::::::::::::::::::::::::::::::: "+quixxData.size());
                            for (int i = 0; i < quixxData.size(); i++) {
                                int i2=i+1;
                                adapter.addFragment(SubQuizSegmentQuestionFragment.newInstance(quixxData.get(i),"Question "+i2+" of "+quixxData.size()),i+1+"");
                            }

                            AppHelper.hideDialog();
                            customViewPager.setAdapter(adapter);

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (customViewPager.getCurrentItem()<quixxData.size()-1){
                                        customViewPager.setCurrentItem(customViewPager.getCurrentItem()+1);
                                    }
                                }
                            });

                            previous.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (customViewPager.getCurrentItem()!=0){
                                        customViewPager.setCurrentItem(customViewPager.getCurrentItem()-1);
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppHelper.hideDialog();
                Toast.makeText(CurrentAffairsQuizzActivity.this, "Error Please Try Again", Toast.LENGTH_SHORT).show();
            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }

    @OnClick(R.id.select_language)
    void onLanguageClick(){
        final String[] languagetitles={"English","Telugu","Hindi"};
        new AlertDialog.Builder(CurrentAffairsQuizzActivity.this)
                .setItems(languagetitles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectLanguage.setText(languagetitles[which]);
                        language = which+1;
                        for (int i = 0; i < checkedColors.length; i++) {
                            checkedColors[i]=false;
                        }
                        getAllData("",categorySelected);
                    }
                })
                .create().show();
    }

    @OnClick(R.id.select_month)
    void onMonthSelected() {
        final CharSequence[] allDates = new CharSequence[13];
        String maxDate = AppHelper.getSelectMonth(new Date());
        final SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM");
        final Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(monthDate.parse(maxDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= 13; i++) {
            String month_name1 = monthDate.format(cal.getTime());
            allDates[i-1] = month_name1;
            cal.add(Calendar.MONTH, -1);
            quizMonthItems.add(new QuizzFilterMonths(month_name1, month_name1));
        }

        txtExamType.setText("SELECT THE EXAM MONTH");
        filterCategoryText.setVisibility(View.GONE);
        filterMonthText.setVisibility(View.VISIBLE);
        mBottomMonthAdapter = new BottomMonthAdapter(quizMonthItems, CurrentAffairsQuizzActivity.this);
        recyclerView.setAdapter(mBottomMonthAdapter);

        if (behavior.getState()== BottomSheetBehavior.STATE_EXPANDED){
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if (behavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });


        /*AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(CurrentAffairsQuizzActivity.this);
        dialogbuilder.setMultiChoiceItems(allDates,checkedColors,
                new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                      getSelectMonth.setText(allDates[which]);
                if (!isChecked){
//                            if (selecteddates.contains("&months[]="+allDates[which])) {
                        for (int i = 0; i < selecteddates.size(); i++) {
                            checkedColors[i]=false;
                            if (selecteddates.get(i).equals("&months[]="+allDates[which])){
                                selecteddates.remove(i);
                                Log.e("response","removes &months[]="+allDates[which]);
                            }
                        }
//                            }
                }
                 if(isChecked){
                     for (int i = 0; i < selecteddates.size(); i++) {
                        checkedColors[i]=true;
                     }
                    selecteddates.add("&months[]="+allDates[which]);
                }
//                      monthYear = monthYear +","+ allDates[which];
            }
        });

        dialogbuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                monthYear=selecteddates.toArray().toString();
                String selectedmonthes="";
                for (String m : selecteddates){
                    selectedmonthes += m;
                }
                monthYear =selectedmonthes;
                getAllData(selectedmonthes,categorySelected);
            }
        });
        dialogbuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogbuilder.setCancelable(false);
        dialogbuilder.create().show();*/
    }


    @OnClick(R.id.select_examtype)
    void onSelectType(){
        onFabSelection();

//    List<String> strings = new ArrayList<>();
//    for (QuizzFilterItems p : quizzFilteritems) {
//        strings.add(p.getExamtypename());
//    }
//
//        final CharSequence[] list=  strings.toArray(new String[strings.size()]);
//    AlertDialog.Builder aleret=new AlertDialog.Builder(CurrentAffairsQuizzActivity.this);
//                aleret.setMessage("Select Exam Type");
//                aleret.setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item, boolean isChecked) {
////                        seleList.add(quizzFilteritems.get(item).getExamtypeid());
////                        seleListTitles.add(quizzFilteritems.get(item).getExamtypename());
//                    }
//                });
//                aleret.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//
//        AlertDialog dlg = aleret.create();
//        dlg.show();
    }


    //    @OnClick(R.id.fab_selection)
    void onFabSelection(){
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        txtExamType.setText("SELECT YOUR EXAM TYPE");
        filterCategoryText.setVisibility(View.VISIBLE);
        filterMonthText.setVisibility(View.GONE);
        mBottomAdapter = new BottomItemAdapter(quizzFilteritems, CurrentAffairsQuizzActivity.this);
        recyclerView.setAdapter(mBottomAdapter);
        if (behavior.getState()== BottomSheetBehavior.STATE_EXPANDED){
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if (behavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
