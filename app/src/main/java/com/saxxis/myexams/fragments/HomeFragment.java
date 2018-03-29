package com.saxxis.myexams.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.saxxis.myexams.R;
import com.saxxis.myexams.activities.CAffDetailActivity;
import com.saxxis.myexams.activities.EducaitonDetailedActivity;
import com.saxxis.myexams.activities.ExamsDescriptionActivity;
import com.saxxis.myexams.activities.SliderImageDetailsActivity;
import com.saxxis.myexams.activities.SubjectsSubListActivity;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.model.CurrentaffairsList;
import com.saxxis.myexams.model.EducationNews;
import com.saxxis.myexams.model.LatestUpdates;
import com.saxxis.myexams.model.SlidersHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

//    @BindView(R.id.imageslider)
//    ViewPager imagePager;
    @BindView(R.id.slider)
    SliderLayout slideImage;
    @BindView(R.id.custom_indicator)
    PagerIndicator indicator;

    private TextSliderView textSliderView;

    @BindView(R.id.latst_caff_one)
    TextView txt_caffOne;
    @BindView(R.id.latst_caff_two)
    TextView txt_caffTwo;
    @BindView(R.id.latst_caff_three)
    TextView txt_caffThree;
    @BindView(R.id.latst_caff_four)
    TextView txt_caffFour;

    @BindView(R.id.trend_upsc)
    TextView txtTrend_upsc;
    @BindView(R.id.trend_ssccgl)
    TextView txtTrend_ssccgl;
    @BindView(R.id.trend_ibpspo)
    TextView txtTrend_ibpspo;
    @BindView(R.id.trend_ibpsclerk)
    TextView txtTrend_ibpsclerk;
    @BindView(R.id.trend_railways)
    TextView txtTrend_railways;
    @BindView(R.id.trend_ugcnet)
    TextView txtTrend_ugcnet;
    @BindView(R.id.trend_appsc)
    TextView txtTrend_appsc;
    @BindView(R.id.trend_tspsc)
    TextView txtTrend_tspsc;


    @BindView(R.id.ltstnotf_one)
    TextView txtNotOne;
    @BindView(R.id.ltstnotf_two)
    TextView txtNotTwo;
    @BindView(R.id.ltstnotf_three)
    TextView txtNotThree;
//    @BindView(R.id.ltstnotf_four)
//    TextView txtNotFour;
    @BindView(R.id.myexams_description)
    WebView webView;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,view);
        imageSlider();

        return view;
    }

    private void imageSlider() {
        Log.e("URL",AppConstants.HOME_SLIDER);
        StringRequest stringRequest = new StringRequest(AppConstants.HOME_SLIDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("SliderData",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("ok")){
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        final ArrayList<SlidersHome> url_list = new ArrayList<SlidersHome>();
                        SlidersHome slidersHome;

                        webView.getSettings().setDefaultTextEncodingName("utf-8");
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.getSettings().setMinimumLogicalFontSize(14);
                        webView.getSettings().setAllowContentAccess(true);
                        webView.loadDataWithBaseURL("http://myexamspace.com/",jsonObject.getString("description"), "text/html", "UTF-8",null);

                        /**
                         * images for slider animation
                         */
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject bannerObject=jsonArray.getJSONObject(i);
                            slidersHome = new SlidersHome(bannerObject.getString("id"),
                                    bannerObject.getString("title"),
                                    bannerObject.getString("image"),
                                    bannerObject.getString("params"));

                            url_list.add(slidersHome);
                            Log.d("response",bannerObject.getString("image"));
                        }

                       for (int i=0;i<url_list.size();i++){
                           String encdurl=url_list.get(i).getImage().replaceAll(" ","%20");
                           textSliderView = new TextSliderView(getActivity());
                           final int finalI = i;
                           textSliderView.description(url_list.get(i).getTitle())
                                    .image(AppConstants.SERVER_URL+ encdurl)
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                        @Override
                                        public void onSliderClick(BaseSliderView slider) {

                                            Log.e("response",url_list.get(finalI).getParams());
                                            //Intent i = new Intent(Intent.ACTION_VIEW);//params
                                            Intent i = new Intent(getActivity(), SliderImageDetailsActivity.class);//params
                                            i.putExtra("sliderId", url_list.get(finalI).getParams());
                                            //i.setData(Uri.parse("http://myexamspace.com/index.php/englishupdates/"+url_list.get(finalI).getParams()));
                                            //i.setData(Uri.parse("http://myexamspace.com/index.php?option=com_jbackend&view=request&action=get&module=user&resource=sliderslink&sliderid="+url_list.get(finalI).getParams()));
                                            startActivity(i);
                                            //Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",  Toast.LENGTH_SHORT).show();
                                        }
                                    });

                           //add your extra information
                           textSliderView.bundle(new Bundle());
                           textSliderView.getBundle().putString("extra",url_list.get(i).getTitle());

                           slideImage.addSlider(textSliderView);
                           slideImage.setPresetTransformer(SliderLayout.Transformer.Accordion);
                           slideImage.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                           slideImage.setCustomAnimation(new DescriptionAnimation());
                           slideImage.setDuration(3000);
                           slideImage.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
                               @Override
                               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                               }
                               @Override
                               public void onPageSelected(int position) {
                                   //Log.d("Slider Demo", "Page Changed: " + position);
                               }
                               @Override
                               public void onPageScrollStateChanged(int state) {

                               }
                           });
                       }

                        /**
                         *
                         *  strings  For LATEST CURRENT AFFAIRS
                         *  */
                        JSONArray engupdatesArray=jsonObject.getJSONArray("engupdates");
                        final ArrayList<LatestUpdates> objengUpdates=new ArrayList<>();

                        for (int i=0;i<engupdatesArray.length();i++) {
                            JSONObject objLatstCurAff=engupdatesArray.getJSONObject(i);
                            String currentAffByte=objLatstCurAff.getString("title");
                            String titleId=objLatstCurAff.getString("id");
                            String introtext =objLatstCurAff.getString("introtext");

                            objengUpdates.add(new LatestUpdates(titleId,currentAffByte,introtext));
                        }

                        TextView txtarr[]={txt_caffOne,txt_caffTwo,txt_caffThree,txt_caffFour};
                        for (int i=0;i<txtarr.length;i++){
                            txtarr[i].setText(objengUpdates.get(i).getCurrentAffByte());
                            final int finalI = i;
                            txtarr[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getActivity(), CAffDetailActivity.class);
                                    intent.putExtra("detaildata",new
                                            CurrentaffairsList(objengUpdates.get(finalI).getCurrentAffByte(),
                                            objengUpdates.get(finalI).getIntrotext(),objengUpdates.get(finalI).getTitleId(),""));
                                    intent.putExtra("detailtitle", "English Current Affairs");
                                    intent.putExtra("comefrom", "home");
                                    getActivity().startActivity(intent);
                                }
                            });
                        }

                        /**
                         * strings for Latest Notifications
                         */
                        JSONArray latstnotifications=jsonObject.getJSONArray("latestnotification");
                        final ArrayList<EducationNews> latestNotiflist=new ArrayList<EducationNews>();
                        for (int i=0;i<latstnotifications.length();i++){
                            JSONObject notifyobj=latstnotifications.getJSONObject(i);
                            latestNotiflist.add(new EducationNews(notifyobj.getString("id"),notifyobj.getString("title"),
                                    notifyobj.getString("introtext"),""));
                        }

                        TextView txtNotArr[] ={txtNotOne,txtNotTwo,txtNotThree};
                        for (int i=0;i<txtNotArr.length;i++){
                            txtNotArr[i].setText(latestNotiflist.get(i).getTitle());
                            final int finalI = i;
                            txtNotArr[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getActivity(), EducaitonDetailedActivity.class);
                                    intent.putExtra("detaildata",new EducationNews(latestNotiflist.get(finalI).getId(),
                                                    latestNotiflist.get(finalI).getTitle(),
                                                    latestNotiflist.get(finalI).getIntrotext(),""));
                                    intent.putExtra("detailtitle", "Latest Updates in English");
                                    intent.putExtra("comefrom","home");
                                    getActivity().startActivity(intent);
                                }
                            });
                        }
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
    }

    /**
     *On clicks for trending jobs
     *
     */
  @OnClick(R.id.trend_upsc)
  void ontrend_upsc(){
      Intent inTrend_upsc=new Intent(getActivity(),ExamsDescriptionActivity.class);
      inTrend_upsc.putExtra("subexamsubid","138");
      inTrend_upsc.putExtra("examtitle","Civil Services(IAS/IPS)");
      startActivity(inTrend_upsc);
  }

  @OnClick(R.id.trend_ssccgl)
  void ontrend_ssccgl(){
      Intent inTrend_ssccgl=new Intent(getActivity(),ExamsDescriptionActivity.class);
      inTrend_ssccgl.putExtra("subexamsubid","312");
      inTrend_ssccgl.putExtra("examtitle","SSC CGL");
      startActivity(inTrend_ssccgl);
  }

  @OnClick(R.id.trend_ibpspo)
  void ontrend_ibpspo(){
      Intent inTrend_ibpspo=new Intent(getActivity(),ExamsDescriptionActivity.class);
      inTrend_ibpspo.putExtra("subexamsubid","186");
      inTrend_ibpspo.putExtra("examtitle","IBPS-PO");
      startActivity(inTrend_ibpspo);
  }

  @OnClick(R.id.trend_ibpsclerk)
  void ontrend_ibpsclerk(){
      Intent inTrend_ibpsclerk=new Intent(getActivity(),ExamsDescriptionActivity.class);
      inTrend_ibpsclerk.putExtra("subexamsubid","187");
      inTrend_ibpsclerk.putExtra("examtitle","IBPS Clerk");
      startActivity(inTrend_ibpsclerk);
  }

  @OnClick(R.id.trend_railways)
  void ontrend_railways(){
      Intent inTrend_railways=new Intent(getActivity(),SubjectsSubListActivity.class);
      inTrend_railways.putExtra("subjectid","1407");
      inTrend_railways.putExtra("subjectname","RAILWAYS");
      startActivity(inTrend_railways);
  }

  @OnClick(R.id.trend_ugcnet)
  void ontrend_ugcnet(){
      Intent inTrend_ugcnet=new Intent(getActivity(),SubjectsSubListActivity.class);
      inTrend_ugcnet.putExtra("subjectid","1808");
      inTrend_ugcnet.putExtra("subjectname","UGC-NET");
      startActivity(inTrend_ugcnet);
  }

  @OnClick(R.id.trend_appsc)
  void ontrend_appsc(){
      Intent inTrend_appsc=new Intent(getActivity(),SubjectsSubListActivity.class);
      inTrend_appsc.putExtra("subjectid","1644");
      inTrend_appsc.putExtra("subjectname","APPSC");
      startActivity(inTrend_appsc);
  }

  @OnClick(R.id.trend_tspsc)
    void ontrend_tspsc(){
      Intent inTrend_tspsc=new Intent(getActivity(),SubjectsSubListActivity.class);
      inTrend_tspsc.putExtra("subjectid","1645");
      inTrend_tspsc.putExtra("subjectname","TSPSC");
      startActivity(inTrend_tspsc);
  }


}
