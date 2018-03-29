package com.saxxis.myexams.fragments.profiletabhosts;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.fragments.profiletabhosts.purcahasepackages.FinalExamResultsFragment;
import com.saxxis.myexams.fragments.profiletabhosts.purcahasepackages.MultipleQuizComboDetailsFragment;
import com.saxxis.myexams.fragments.profiletabhosts.purcahasepackages.OrderListFragment;
import com.saxxis.myexams.fragments.profiletabhosts.purcahasepackages.PackageDetailsFragment;
import com.saxxis.myexams.helper.UserPrefs;
import com.saxxis.myexams.helper.utils.CustomViewPager;
import com.saxxis.myexams.model.MyPackOrderListData;
import com.saxxis.myexams.model.PackageData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPurchasePakgeFragment extends Fragment {


    private CustomViewPager viewpager_packages;
    private TabLayout tabLayout;
    private MyPurchasePackagesPagerAdapter adapter;

    private UserPrefs userPrefs;

    ProgressBar progressbar;

    public MyPurchasePakgeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_purchase_pakge, container, false);
        userPrefs = new UserPrefs(getActivity());
        progressbar = (ProgressBar)view.findViewById(R.id.progress);

        adapter = new MyPurchasePackagesPagerAdapter(getChildFragmentManager());

        tabLayout = (TabLayout)view.findViewById(R.id.mypurchasepackages);
        viewpager_packages = (CustomViewPager)view.findViewById(R.id.viewpager_packages);
        getPurchacePackages();

        return view;
    }

    private void getPurchacePackages() {
        progressbar.setVisibility(View.VISIBLE);
        Log.e("response",AppConstants.MY_PACKAGES_DETAILS+userPrefs.getUserId());
        StringRequest stringRequest = new StringRequest(AppConstants.MY_PACKAGES_DETAILS+userPrefs.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressbar.setVisibility(View.GONE);
                            Log.e("response",response);
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("status").equals("ok")){
                                JSONArray dataArray = jsonObject.getJSONArray("data");
                                ArrayList<MyPackOrderListData> orderlist = new ArrayList<>();
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataObject = dataArray.getJSONObject(i);
                                    String order_num = dataObject.getString("order_num");
                                    String order_price = dataObject.getString("order_price");
                                    String package_id = dataObject.getString("package_id");
                                    String order_status = dataObject.getString("order_status");
                                    orderlist.add(new MyPackOrderListData(order_num,order_price,package_id,order_status));
                                }

                                JSONArray singlepackages= jsonObject.getJSONArray("singlepackages");
                                JSONArray combopackages= jsonObject.getJSONArray("combopackages");
                                ArrayList<PackageData> singledata=new ArrayList<>();
                                ArrayList<PackageData> combodata=new ArrayList<>();

                                for (int i = 0; i < singlepackages.length(); i++) {
                                    JSONObject quizObject = singlepackages.getJSONObject(i);
                                    String OrderNum = quizObject.getString("OrderNum");
                                    String PackageId = quizObject.getString("PackageId");
                                    String PackageName = quizObject.getString("PackageName");
                                    String QuizNum = quizObject.getString("QuizNum");
                                    String PackagePrice = quizObject.getString("PackagePrice");
                                    String QuizCategoryId = quizObject.getString("QuizCategoryId");
                                    String PackageQuantity = quizObject.getString("PackageQuantity");
                                    String PackageType = quizObject.getString("PackageType");
                                    String quiz_category = quizObject.getString("quiz_category");
                                    String quiz_subject = quizObject.getString("quiz_subject");
                                    String quiz_sublevel = quizObject.getString("quiz_sublevel");
                                    String quiz_subchapter = quizObject.getString("quiz_subchapter");
                                    String CategoryName = quizObject.getString("CategoryName");
                                    String SubjectName = quizObject.getString("SubjectName");
                                    String SublevelName = quizObject.getString("SublevelName");
                                    String SubchapterName = quizObject.getString("SubchapterName");
                                    String TotalQuizzes = quizObject.getString("TotalQuizzes");

                                        singledata.add(new PackageData(OrderNum,PackageId,PackageName,QuizNum,
                                                PackagePrice,QuizCategoryId,PackageQuantity,PackageType,quiz_category,quiz_subject,
                                                quiz_sublevel,quiz_subchapter,CategoryName,SubjectName,SublevelName,SubchapterName,
                                                TotalQuizzes));
                                }

                                for (int i = 0; i < combopackages.length(); i++) {
                                    JSONObject quizObject = combopackages.getJSONObject(i);
                                    String OrderNum = quizObject.getString("OrderNum");
                                    String PackageId = quizObject.getString("PackageId");
                                    String PackageName = quizObject.getString("PackageName");
                                    String QuizNum = quizObject.getString("QuizNum");
                                    String PackagePrice = quizObject.getString("PackagePrice");
                                    String QuizCategoryId = quizObject.getString("QuizCategoryId");
                                    String PackageQuantity = quizObject.getString("PackageQuantity");
                                    String PackageType = quizObject.getString("PackageType");
                                    String quiz_category = quizObject.getString("quiz_category");
                                    String quiz_subject = quizObject.getString("quiz_subject");
                                    String quiz_sublevel = quizObject.getString("quiz_sublevel");
                                    String quiz_subchapter = quizObject.getString("quiz_subchapter");
                                    String CategoryName = quizObject.getString("CategoryName");
                                    String SubjectName = quizObject.getString("SubjectName");
                                    String SublevelName = quizObject.getString("SublevelName");
                                    String SubchapterName = quizObject.getString("SubchapterName");
                                    String TotalQuizzes = quizObject.getString("TotalQuizzes");

                                    combodata.add(new PackageData(OrderNum,PackageId,PackageName,QuizNum,
                                            PackagePrice,QuizCategoryId,PackageQuantity,PackageType,quiz_category,quiz_subject,
                                            quiz_sublevel,quiz_subchapter,CategoryName,SubjectName,SublevelName,SubchapterName,
                                            TotalQuizzes));

                                }

                                adapter.addFragment(MultipleQuizComboDetailsFragment.newInstance(combodata,""),"Combo Details");
                                adapter.addFragment(PackageDetailsFragment.newInstance(singledata,""),"Package Details");
                                adapter.addFragment(FinalExamResultsFragment.newInstance(jsonObject.getString("Totalexams"),
                                        jsonObject.getString("Attamted Exams"),jsonObject.getString("Un-Attamted Exams")),"Exams Count");
                                adapter.addFragment(OrderListFragment.newInstance(orderlist,""),"Order List");

                                viewpager_packages.setAdapter(adapter);
                                tabLayout.setupWithViewPager(viewpager_packages);
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

    public class MyPurchasePackagesPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyPurchasePackagesPagerAdapter(FragmentManager fm){
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
