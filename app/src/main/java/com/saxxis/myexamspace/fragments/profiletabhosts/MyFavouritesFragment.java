package com.saxxis.myexamspace.fragments.profiletabhosts;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.fragments.myresult.subexplanation.SubQuizSegmentQuestionFragment;
import com.saxxis.myexamspace.fragments.profiletabhosts.myfavouritequestions.MyFavouriteQuestionFragment;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.helper.UserPrefs;
import com.saxxis.myexamspace.helper.utils.CustomViewPager;
import com.saxxis.myexamspace.model.MyFavourites;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.saxxis.myexamspace.R.id.fav_custmviewpager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFavouritesFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private  UserPrefs userPrefs;

    CustomViewPager customViewPager;
    TextView txtQuestionNumber,nofavfound;
    ImageView imgFavourite;//,imgReport;

    LinearLayout content_favlist;
    ProgressBar progressBar;

    MyFavPagerAdapter adapter;

    private ArrayList<MyFavourites> favouritesList;// = new ArrayList<MyFavourites>();

    FragmentManager fragmentManager;

    public MyFavouritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFavouritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFavouritesFragment newInstance(String param1, String param2) {
        MyFavouritesFragment fragment = new MyFavouritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPrefs = new UserPrefs(getActivity());
        fragmentManager = getChildFragmentManager();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_favourites, container, false);

        customViewPager = (CustomViewPager) view.findViewById(fav_custmviewpager);
        txtQuestionNumber =(TextView)view.findViewById(R.id.question_number);
        nofavfound = (TextView)view.findViewById(R.id.nofavfound);
        imgFavourite = (ImageView)view.findViewById(R.id.remove_favourite);

        content_favlist = (LinearLayout)view.findViewById(R.id.content_favlist);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_fav);
        //imgReport = (ImageView)view.findViewById(R.id.report_question);

        adapter = new MyFavPagerAdapter(fragmentManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavourites();
    }


    private void getFavourites() {
        Log.d("response",AppConstants.MY_FAVOURITEXS + userPrefs.getUserId());
        progressBar.setVisibility(View.VISIBLE);
        content_favlist.setVisibility(View.GONE);
        customViewPager.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(AppConstants.MY_FAVOURITEXS + userPrefs.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            content_favlist.setVisibility(View.VISIBLE);
                            adapter = new MyFavPagerAdapter(fragmentManager);
                            Log.e("response",s);
                            JSONObject jsonObject = new JSONObject(s);
//                            favouritesList = new ArrayList<>();
                            if (jsonObject.getString("status").equals("ok")){
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
                                    if (favouritesList!=null){
                                        favouritesList.clear();
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                                favouritesList = new ArrayList<MyFavourites>();

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject objData = jsonArray.getJSONObject(i);
                                    favouritesList.add(new MyFavourites(objData.getString("QuestionVersionId"),
                                            objData.getString("StatisticsInfoId"),objData.getString("UserId"),objData.getString("CreatedDate"),
                                            objData.getString("Status"),objData.getString("QuestionId"),objData.getString("QuestionTypeId"),
                                            objData.getString("Question"),objData.getString("Solution"),objData.getString("HashCode"),objData.getString("Created"),
                                            objData.getString("CreatedBy"),objData.getString("Data"),objData.getString("ShowAsImage"),objData.getString("Score"),
                                            objData.getString("wscore"),objData.getString("comp"),objData.getString("CompId"),objData.getString("PublishedDate")));
                                }


                                if (favouritesList.size()!=0){
                                    nofavfound.setVisibility(View.GONE);
                                    content_favlist.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < favouritesList.size(); i++) {
                                        int i2=i+1;
                                        adapter.addFragment(MyFavouriteQuestionFragment.newInstance(favouritesList.get(i),"Question "+i2+" of "+favouritesList.size()),i2+"");
                                    }
                                    customViewPager.setAdapter(adapter);
                                }
                                if (favouritesList.size()==0){
                                    content_favlist.setVisibility(View.GONE);
                                    nofavfound.setVisibility(View.VISIBLE);
                                }

                            }


//                            final ArrayList<MyFavourites> finalFavouritesList = favouritesList;
                            txtQuestionNumber.setText("Question 1 of "+favouritesList.size());
                            customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }
                                @Override
                                public void onPageSelected(int position) {
                                    int positionone=position+1;
                                    txtQuestionNumber.setText("Question "+positionone+" of "+favouritesList.size());
                                }
                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });

                            imgFavourite.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (favouritesList.size()>0){

                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Are You Sure ?")
                                            .setMessage("Remove This Item From Favourites")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Log.e("response",AppConstants.REMOVE_FACOURITE + favouritesList.get(customViewPager.getCurrentItem()).getQuestionVersionId()+ "&userid=" + userPrefs.getUserId());
                                                    AppHelper.showDialog(getActivity(),"Loading Please Wait...");
                                                    StringRequest favRequest = new
                                                            StringRequest(AppConstants.REMOVE_FACOURITE + favouritesList.get(customViewPager.getCurrentItem()).getQuestionVersionId()+ "&userid=" + userPrefs.getUserId(),
                                                            new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String s) {
                                                                    AppHelper.hideDialog();
                                                                    Log.e("response",s);
                                                                    getFavourites();
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError volleyError) {

                                                        }
                                                    });
                                                    dialog.dismiss();
                                                    MyExamsApp.getMyInstance().addToRequestQueue(favRequest);
                                                }
                                            })
                                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).create().show();
                                    }
//                                    customViewPager.removeViewAt(customViewPager.getCurrentItem());
//                                    adapter.notifyDataSetChanged();
                                }
                            });

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


    @Override
    public void onPause() {
        super.onPause();
//        if (fragmentManager.getFragments() != null) {
//            fragmentManager.getFragments().clear();
//
//            List<Fragment> fragments = fragmentManager.getFragments();
//            if (fragments != null) {
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//                for (Fragment f : fragments) {
//                    //You can perform additional check to remove some (not all) fragments:
//                    if (f instanceof SubQuizSegmentQuestionFragment) {
//                        ft.remove(f);
//                    }
//                }
//
//                customViewPager.removeAllViewsInLayout();
//                customViewPager.removeAllViews();
//                ft.commitAllowingStateLoss();
//            }
//            if (favouritesList!=null){
//                favouritesList.clear();
//            }
//            adapter.notifyDataSetChanged();
//        }
    }

    public class MyFavPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyFavPagerAdapter(FragmentManager fm){
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
}
