package com.saxxis.myexamspace.fragments.myresult;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.adapters.TopRankerAdapter;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.model.TopRanksList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyResultAnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyResultAnalysisFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String ticketid;
    private String mParam2;

    @BindView(R.id.toprankers_recv)
    RecyclerView recyclerViewTopRankers;

    @BindView(R.id.youLL) LinearLayout youLL;
    @BindView(R.id.topperLL) LinearLayout topperLL;
    @BindView(R.id.avgLL) LinearLayout avgLL;

    @BindView(R.id.youScore) TextView youScore;
    @BindView(R.id.youAttempted) TextView youAttempted;
    @BindView(R.id.youAccuracy) TextView youAccuracy;
    @BindView(R.id.youTime) TextView youTime;
    @BindView(R.id.topperScore) TextView topperScore;
    @BindView(R.id.topperAttempted) TextView topperAttempted;
    @BindView(R.id.topperAccuracy) TextView topperAccuracy;
    @BindView(R.id.topperTime) TextView topperTime;
    @BindView(R.id.avgScore) TextView avgScore;
    @BindView(R.id.avgAttempted) TextView avgAttempted;
    @BindView(R.id.avgAccuracy) TextView avgAccuracy;
    @BindView(R.id.avgTime) TextView avgTime;

    PieChart pieChart;
    BarChart barChart;

    public MyResultAnalysisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyResultAnalysisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyResultAnalysisFragment newInstance(String param1, String param2) {
        MyResultAnalysisFragment fragment = new MyResultAnalysisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticketid = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis_art, container, false);
        ButterKnife.bind(this,view);
        pieChart = (PieChart)view.findViewById(R.id.piechart);

        Legend pil = pieChart.getLegend();
        pil.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pil.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        pil.setOrientation(Legend.LegendOrientation.VERTICAL);
        pil.setDrawInside(true);
        pil.setYOffset(0f);
        pil.setXOffset(10f);
        pil.setYEntrySpace(0f);
        pil.setTextSize(8f);


        barChart = (BarChart) view.findViewById(R.id.barchart);
//        barChart.setOnChartValueSelectedListener(this);
        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
//        l.setTypeface(mTfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);



        XAxis xAxis = barChart.getXAxis();
//        xAxis.setTypeface(mTfLight);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        final String[] xaxisName={"You","Topper","Average"};
        xAxis.setLabelCount(3);
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });

        YAxis rightAxis =barChart.getAxisRight();
        rightAxis.setValueFormatter(new LargeValueFormatter());
        rightAxis.setDrawGridLines(false);
        rightAxis.setSpaceTop(35f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        barChart.getAxisRight().setEnabled(true);
        barChart.animateY(3000);

        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        int groupCount = 3;//mSeekBarX.getProgress() + 1;
        int startYear = 1;
        int endYear = startYear + groupCount;


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();

        for (int i = startYear; i < endYear; i++) {
            yVals1.add(new BarEntry(i,1));
            yVals2.add(new BarEntry(i,2));
            yVals3.add(new BarEntry(i,3));
        }

        BarDataSet set1, set2, set3;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) barChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();

        } else {
            // create 3 DataSets
            set1 = new BarDataSet(yVals1, "Acquired Score");
            set1.setColor(Color.rgb(46, 18, 255));
            set2 = new BarDataSet(yVals2, "Accuracy Percentage");
            set2.setColor(Color.rgb(255, 54, 18));
            set3 = new BarDataSet(yVals3, "Attempted Questions");
            set3.setColor(Color.rgb(255,128,0));
            set1.setStackLabels(xaxisName);
            set2.setStackLabels(xaxisName);
            set3.setStackLabels(xaxisName);

            BarData data = new BarData(set1, set2, set3);
            data.setValueFormatter(new LargeValueFormatter());
//            data.setValueTypeface(mTfLight);


            barChart.setData(data);
        }

        // specify the width each bar should have
        barChart.getBarData().setBarWidth(barWidth);
        Description bardescribe = new Description();
        bardescribe.setText("Performance Compare");
        barChart.setDescription(bardescribe);

        // restrict the x-axis range
        barChart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChart.getXAxis().setAxisMaximum(startYear + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        barChart.groupBars(startYear, groupSpace, barSpace);
        barChart.invalidate();

        AppHelper.showDialog(getActivity(),"Loading Please Wait...");
        String url = AppConstants.RESULT_ANALYSIS+ticketid;
        Log.e("response", url);
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppHelper.hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            JSONObject dataObject = jsonObject.getJSONObject("data");

//                            float totalquestioncount = dataObject.getInt("QuestionCount");
                            float attempted=jsonObject.getInt("attempt");
                            float unattempt = jsonObject.getInt("unattempt");
                            ArrayList<PieEntry> entries = new ArrayList<>();
                                    entries.add(new PieEntry(attempted,"Attempted"));
                                    entries.add(new PieEntry(unattempt,"UnAttempted"));

                            PieDataSet dataset = new PieDataSet(entries,"");
                            dataset.setSliceSpace(3f);

                            ArrayList<Integer> colors = new ArrayList<Integer>();
                            for (int c : ColorTemplate.COLORFUL_COLORS)
                                colors.add(c);
                            colors.add(ColorTemplate.getHoloBlue());

                            dataset.setColors(colors);
                            PieData data = new PieData(dataset); // initialize Piedata<br />
                            pieChart.setData(data);

                            pieChart.setTransparentCircleRadius(150f);
                            dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                            pieChart.setUsePercentValues(true);
                            pieChart.animateY(3000);
                            Description se = new Description();
                            se.setText("Your Quiz Result Summary");

                            pieChart.setDescription(se);
                            pieChart.setDrawHoleEnabled(false);
                            pieChart.setDragDecelerationFrictionCoef(0.95f);
                            JSONArray topRankersArray = jsonObject.getJSONArray("toprank");
                            ArrayList<TopRanksList> toperslist=new ArrayList<>();
                            for (int i = 0; i < topRankersArray.length(); i++) {
                                JSONObject dataJsnObject = topRankersArray.getJSONObject(i);
                                toperslist.add(new TopRanksList(dataJsnObject.getString("name"),
                                        dataJsnObject.getString("UserScore"),
                                        dataJsnObject.getString("rank")));
                            }

                            recyclerViewTopRankers.setHasFixedSize(true);
                            recyclerViewTopRankers.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerViewTopRankers.setAdapter(new TopRankerAdapter(getActivity(),toperslist));

                            JSONObject summaryObj = jsonObject.getJSONObject("summary");
                            JSONObject youObj = summaryObj.getJSONObject("you");
                            JSONObject topperObj = summaryObj.getJSONObject("topper");
                            JSONObject avgObj = summaryObj.getJSONObject("avg");
                            if(youObj != null) {
                                youScore.setText(youObj.getString("score"));
                                youAttempted.setText(youObj.getString("attempted"));
                                youAccuracy.setText(youObj.getString("accuracy"));
                                youTime.setText(youObj.getString("time"));
                            } else {
                                youLL.setVisibility(View.GONE);
                            }
                            if(topperObj != null) {
                                topperScore.setText(topperObj.getString("score"));
                                topperAttempted.setText(topperObj.getString("attempted"));
                                topperAccuracy.setText(topperObj.getString("accuracy"));
                                topperTime.setText(topperObj.getString("time"));
                            } else {
                                topperLL.setVisibility(View.GONE);
                            }
                            if(avgObj != null) {
                                avgScore.setText(avgObj.getString("score"));
                                avgAttempted.setText(avgObj.getString("attempted"));
                                avgAccuracy.setText(avgObj.getString("accuracy"));
                                avgTime.setText(avgObj.getString("time"));
                            } else {
                                avgLL.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
        return view;
    }
}
