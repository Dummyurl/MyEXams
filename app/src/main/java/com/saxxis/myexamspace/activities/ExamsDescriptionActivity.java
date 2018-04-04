package com.saxxis.myexamspace.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.activities.examsresults.MyResultsActivity;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.fragments.examdescribe.AnalysisFragment;
import com.saxxis.myexamspace.fragments.examdescribe.DetailsFragment;
import com.saxxis.myexamspace.fragments.examdescribe.MaterialFragment;
import com.saxxis.myexamspace.fragments.examdescribe.StrategyFragment;
import com.saxxis.myexamspace.fragments.examdescribe.SyllabusFragment;
import com.saxxis.myexamspace.fragments.examdescribe.TestsFragment;
import com.saxxis.myexamspace.fragments.examdescribe.TimeLineFragment;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.model.ExamDescList;
import com.saxxis.myexamspace.model.MyResultsList;
import com.saxxis.myexamspace.parsers.ExamDescParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExamsDescriptionActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static String quizdata;
    private static String timelineData;
    private static JSONArray meterialdescriptionArray;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static String subexamid;
    private static String subExamTitle="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager with the sections adapter.
        subexamid = getIntent().getExtras().getString("subexamsubid");
        subExamTitle = getIntent().getExtras().getString("examtitle");
        getSupportActionBar().setTitle(subExamTitle);

        AppHelper.showDialog(ExamsDescriptionActivity.this,"Loading Please Wait...");
        Log.e("response", AppConstants.EXAMS_DETAILS_LIST+subexamid);
        StringRequest stringRequest=new StringRequest(AppConstants.EXAMS_DETAILS_LIST+subexamid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        Log.e("response",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")){
                                JSONArray jsonArray=jsonObject.getJSONArray("data");

                                meterialdescriptionArray = jsonObject.getJSONArray("meterialdescription");

                                timelineData = jsonObject.getString("timelines");
//                                ArrayList<ExamDescList> mdata=new ArrayList<>();
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject dataobject=jsonArray.getJSONObject(i);
                                    quizdata=dataobject.getString("test");
//                                    mdata.add(new ExamDescList(dataobject.getString("Details"),dataobject.getString("syllabus"),
//                                                dataobject.getString("Analysis"),dataobject.getString("strategy"),
//                                                dataobject.getString("MaterialDesc"),dataobject.getString("quizdetails")));
                                }

                                ArrayList<ExamDescList> mdata  = ExamDescParser.getInstance(response).getData();
                                // Create the adapter that will return a fragment for each of the three
                                // primary sections of the activity.
                                mViewPager = (ViewPager) findViewById(R.id.container);
                                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

                                tabLayout.setupWithViewPager(mViewPager);
                                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                                mSectionsPagerAdapter.addFragment(TimeLineFragment.newInstance(timelineData,""),"TimeLine");
                                mSectionsPagerAdapter.addFragment(DetailsFragment.newInstance(mdata.get(0).getDetails(),""),"Details");
                                mSectionsPagerAdapter.addFragment(SyllabusFragment.newInstance(mdata.get(0).getSyllabus(),
                                        jsonObject.getJSONObject("tabs").toString()),"Syllabus");
                                mSectionsPagerAdapter.addFragment(AnalysisFragment.newInstance(mdata.get(0).getAnalysis(),""),"Analysis");
                                mSectionsPagerAdapter.addFragment(StrategyFragment.newInstance(mdata.get(0).getStrategy(),""),"Strategy");
                                mSectionsPagerAdapter.addFragment(MaterialFragment.newInstance(mdata.get(0).getMaterialDesc(),jsonObject.getJSONArray("meterialdescription").toString()),"Material");
                                mSectionsPagerAdapter.addFragment(TestsFragment.newInstance(quizdata,""),"Tests");

                                mViewPager.setAdapter(mSectionsPagerAdapter);
                                mViewPager.setCurrentItem(1);

                            }else {
                                Toast.makeText(ExamsDescriptionActivity.this, "failed to retrive data", Toast.LENGTH_SHORT).show();
                                finish();
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_exams_description, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            super.onBackPressed();
            return true;
//        }else if (id == R.id.action_settings) {
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 100) {
            if (data != null) {
                MyResultsList resultData = (MyResultsList)data.getParcelableExtra("ticketId");
                String quizName = data.getStringExtra("Quizname");
                String tabselection = data.getStringExtra("tabselection");

                if (resultData.getEndDate() == null || resultData.getEndDate().equals("null")) {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    resultData.setEndDate(formattedDate);
                }

                Intent intent = new Intent(this, MyResultsActivity.class);
                intent.putExtra("ticketId", resultData);
                intent.putExtra("Quizname", quizName);
                intent.putExtra("tabselection","viewresult");
                startActivity(intent);
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber,ArrayList<ExamDescList> mdata) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            args.putParcelableArrayList("txtone",mdata);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//
//            View rootView = inflater.inflate(R.layout.fragment_exams_description, container, false);
//            WebView webview = (WebView) rootView.findViewById(R.id.section_label);
//            final RecyclerView recv_Description=(RecyclerView) rootView.findViewById(R.id.recv_quizzDescription);
//
//            webview.getSettings().setDefaultTextEncodingName("utf-8");
//            webview.getSettings().setJavaScriptEnabled(true);
//            webview.getSettings().setMinimumLogicalFontSize(19);
//            webview.getSettings().setAllowContentAccess(true);
//
//            ArrayList<ExamDescList> mdata = getArguments().getParcelableArrayList("txtone");
//
//            ExamDescList listdata = mdata.get(0);
//            if (listdata==null){
//                getActivity().finish();
//                Toast.makeText(getActivity(),"No Data found in this category",Toast.LENGTH_LONG).show();
//            }else {
//                switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
//                    case 1:
//
//                        recv_Description.setVisibility(View.VISIBLE);
//                        webview.setVisibility(View.GONE);
//
////                        recv_Description.setHasFixedSize(true);
////                        recv_Description.setLayoutManager(new LinearLayoutManager(getActivity()));
////                        recv_Description.addItemDecoration(new RecvDecors(getActivity(), R.dimen.job_listing_main_offset));
////
////                        try {
////                            JSONObject timeLineObject = new JSONObject(timelineData);
////                            JSONArray titleArray = timeLineObject.getJSONArray("title");
////                            JSONArray timeLineDates = timeLineObject.getJSONArray("timelinedate");
////                            JSONArray links=timeLineObject.getJSONArray("links");
////
////                            if (titleArray.length() == timeLineDates.length()){
////                                final ArrayList<TimeLineModel> timelinelist= new ArrayList<>();
////                                for (int i = 0; i < titleArray.length(); i++) {
////                                    timelinelist.add(new TimeLineModel(
////                                            titleArray.getString(i),
////                                            timeLineDates.getString(i),
////                                            links.getString(i)));
////                                }
////
////                                recv_Description.setAdapter(new TimeLineAdapters(getActivity(),timelinelist));
////
////
////
//////                                ItemClickSupport.addTo(recv_Description).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//////                                    @Override
//////                                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//////                                        String linkText = timelinelist.get(position).getLinks();
//////
//////                                    }
//////                                });
////                            }
////                        }catch (JSONException jse){
////
////                        }
//                        break;
//                    case 2:
//                        recv_Description.setVisibility(View.GONE);
//                        webview.setVisibility(View.VISIBLE);
//
////                        webview.loadDataWithBaseURL(AppConstants.SERVER_URL, listdata.getDetails(), "text/html; charset=utf-8", "UTF-8", null);
//                        break;
//                    case 3:
//                        recv_Description.setVisibility(View.GONE);
//
//                        webview.setVisibility(View.VISIBLE);
////                        if(!listdata.getSyllabus().contains("{tab ")){
////                            webview.loadDataWithBaseURL(AppConstants.SERVER_URL, listdata.getSyllabus(), "text/html; charset=utf-8", "UTF-8", null);
////                        }
//
//                        if(listdata.getSyllabus().contains("{tab ")){
//
////                            String spliregex="[{]tab";
////                            String[] syllabusitems = listdata.getSyllabus().split(spliregex);
////                            Log.e("response", syllabusitems[1]);
////                            Log.e("response", syllabusitems[2]);
////                      webview.loadDataWithBaseURL(AppConstants.SERVER_URL,syllabusitems[1], "text/html; charset=utf-8", "UTF-8", null);
//                        }
//                        break;
//                    case 4:
//                        recv_Description.setVisibility(View.GONE);
//                        webview.setVisibility(View.VISIBLE);
////                        webview.loadDataWithBaseURL(AppConstants.SERVER_URL, listdata.getAnalysis(), "text/html; charset=utf-8", "UTF-8", null);
//                        break;
//                    case 5:
//                        recv_Description.setVisibility(View.GONE);
//                        webview.setVisibility(View.VISIBLE);
////                        webview.loadDataWithBaseURL(AppConstants.SERVER_URL, listdata.getStrategy(), "text/html; charset=utf-8", "UTF-8", null);
//                        break;
//                    case 6:
//                        recv_Description.setVisibility(View.GONE);
//                        webview.setVisibility(View.VISIBLE);
////                        if (meterialdescriptionArray!=null){
////                            for (int i = 0; i < meterialdescriptionArray.length(); i++) {
////                                try {
////                                    JSONObject materialObject=meterialdescriptionArray.getJSONObject(i);
////                                   String titleName = materialObject.getString("title");
////                                   String pdfurl = AppConstants.PDF_URL+materialObject.getString("file_name");
////                                   webview.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdfurl);
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
////                            }
////                        }
//////                        webview.loadDataWithBaseURL(AppConstants.SERVER_URL, listdata.getMaterialDesc(), "text/html; charset=utf-8", "UTF-8", null);
//                        break;
//                    case 7:
////                        recv_Description.setVisibility(View.VISIBLE);
//                        webview.setVisibility(View.GONE);
//
////                        recv_Description.setHasFixedSize(true);
////                        recv_Description.setLayoutManager(new LinearLayoutManager(getActivity()));
////                        recv_Description.addItemDecoration(new RecvDecors(getActivity(), R.dimen.job_listing_main_offset));
////                    try {
////                        JSONArray jsonObject = new JSONArray(quizdata);
////
////                        ArrayList<QuizzListList> quizmodel=new ArrayList<>();
////                        for (int i=0;i<jsonObject.length();i++){
////                             JSONObject quizObject=jsonObject.getJSONObject(i);
////                             quizmodel.add(new QuizzListList(quizObject.getString("SubjectId"),quizObject.getString("SubjectName"),
////                                     quizObject.getString("QuizId"),
////                                     quizObject.getString("QuizName"),
////                                     quizObject.getString("QuestionCount"),
////                                     quizObject.getString("ExamLanguage"),
////                                     quizObject.getString("TestExamType"),
////                                     quizObject.getString("TotalExamMarks"),
////                                     quizObject.getString("TotalExamTime"),
////                                     quizObject.getString("version")));
////                        }
////                        recv_Description.setAdapter(new QuizzesListAdapter(getActivity(), quizmodel));
////
////                    }catch (JSONException jse){
////
////                    }
//
//
////                    webview.loadDataWithBaseURL(AppConstants.SERVER_URL,"","text/html; charset=utf-8","UTF-8",null);
//                        break;
//                }
//            }
//
//            webview.getSettings().setJavaScriptEnabled(true);
//            return rootView;
//        }
//
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            if(position==2){
//                return
//            }
//            if (position!=2){
//                return PlaceholderFragment.newInstance(position + 1,mdata);
//            }
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
//            switch (position) {
//                case 0:
//                    return "TimeLine";
//                case 1:
//                    return "Details";
//                case 2:
//                    return "Syllabus";
//                case 3:
//                    return "Analysis";
//                case 4:
//                    return "Strategy";
//                case 5:
//                    return "Material";
//                case 6:
//                    return "Tests";
//            }
//            return null;
        }
    }

}
