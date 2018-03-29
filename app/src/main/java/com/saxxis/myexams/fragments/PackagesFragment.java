package com.saxxis.myexams.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saxxis.myexams.R;
import com.saxxis.myexams.fragments.packages.ComboPackage;
import com.saxxis.myexams.fragments.packages.SinglePackage;
import com.saxxis.myexams.helper.utils.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PackagesFragment extends Fragment {


    public PackagesFragment() {

    }

    @BindView(R.id.packagetabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager_package)
    CustomViewPager vpagerPackage;

//    @BindView(R.id.spinner_filter)
//    Spinner spinnerFilter;

    SectionPagerAdapter sectionPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_packages, container, false);
        ButterKnife.bind(this,view);

//        sectionPagerAdapter = new SectionPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
//        vpagerPackage.setAdapter(sectionPagerAdapter);
        setupViewPager(vpagerPackage);
        tabLayout.setupWithViewPager(vpagerPackage);
        vpagerPackage.setPagingEnabled(false);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
        adapter.addFragment(SinglePackage.getInstance(), "Single Packages");
        adapter.addFragment(ComboPackage.getInstance(), "Multiple Combos");
        viewPager.setAdapter(adapter);
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter{
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

//    public static class PackagePlaceHolderFragment extends Fragment{
//
//        public static final String ARG_SECTION_NUMBER="number";
//        public static final String ARGS_SECTION_URL="url";
//
//        @BindView(R.id.recv_packages)
//        RecyclerView recv_pack;
//
//        public static PackagePlaceHolderFragment getInstance(int sectionNumber){
//            PackagePlaceHolderFragment ppHolderFragment = new PackagePlaceHolderFragment();
//
//            Bundle args=new Bundle();
//            args.putInt(ARG_SECTION_NUMBER,sectionNumber);
//
//            if (sectionNumber==1){
//                args.putString(ARGS_SECTION_URL, AppConstants.PACKAGES_URL);
//            }
//
//            if (sectionNumber==2){
//                args.putString(ARGS_SECTION_URL, AppConstants.COMBO_PACKAGES_URL);
//            }
//
//            ppHolderFragment.setArguments(args);
//            return ppHolderFragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//            View view = inflater.inflate(R.layout.package_view,container,false);
//            ButterKnife.bind(this,view);
//
//            recv_pack.setHasFixedSize(true);
//            recv_pack.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recv_pack.addItemDecoration(new RecvDecors(getActivity(),R.dimen.job_listing_main_offset));
//
//            setrecvview(getArguments().getString(ARGS_SECTION_URL));
//            return view;
//        }
//
//        private void setrecvview(String url) {
//            Log.d("response",url);
//            StringRequest stringRequest=new StringRequest(url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.d("response",response);
//
//                            try {
//                                JSONObject objResponse = new JSONObject(response);
//                                if (objResponse.getString("status").equals("ok")) {
//
//                                    ArrayList<PackagesItems> data=new ArrayList<>();
//
//                                    PackagesItems packagesItems=null;
//                                    JSONArray jsonArray=objResponse.getJSONArray("data");
//                                    for (int i=0;i<jsonArray.length();i++){
//                                        JSONObject jsonObject=jsonArray.getJSONObject(i);
//                                        String id=jsonObject.getString("id");
//                                        String quiz_category=jsonObject.getString("quiz_category");
//                                        String quiz_subject=jsonObject.getString("quiz_subject");
//                                        String quiz_sublevel=jsonObject.getString("quiz_sublevel");
//                                        String quiz_subchapter=jsonObject.getString("quiz_subchapter");
//                                        String quiz_pack=jsonObject.getString("quiz_pack");
//                                        String quiz_offer_type=jsonObject.getString("quiz_offer_type");
//                                        String quiz_num=jsonObject.getString("quiz_num");
//                                        String questions_num=jsonObject.getString("questions_num");
//                                        String quiz_el=jsonObject.getString("quiz_el");
//                                        String quiz_tet=jsonObject.getString("quiz_tet");
//                                        String quiz_ques=jsonObject.getString("quiz_ques");
//                                        String quiz_offer_price=jsonObject.getString("quiz_offer_price");
//                                        String quiz_offer_description=jsonObject.getString("quiz_offer_description");
//                                        String quiz_offer_readmore=jsonObject.getString("quiz_offer_readmore");
//                                        String quiz_offer_title=jsonObject.getString("quiz_offer_title");
//                                        String quiz_offer_image=jsonObject.getString("quiz_offer_image");
//                                        String published=jsonObject.getString("published");
//                                        String Subjects=jsonObject.getString("Subjects");
//                                        packagesItems = new PackagesItems(id,quiz_category,quiz_subject,quiz_sublevel,
//                                                quiz_subchapter,quiz_pack,quiz_offer_type,quiz_num,questions_num,quiz_el,
//                                                quiz_tet,quiz_ques,quiz_offer_price,quiz_offer_description,
//                                                quiz_offer_readmore,quiz_offer_title,quiz_offer_image,published,Subjects);
//
//                                        data.add(packagesItems);
//                                    }
//                                    recv_pack.setAdapter(new PackagesAdapter(getActivity(),data));
//                                }
//                            }catch (JSONException je){
//
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });
//
//            MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
//        }
//    }



//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        //super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.package_filter,menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.fil_package_class){
//            Intent intent=new Intent(getActivity(), FilterActivity.class);
//            startActivityForResult(intent,1212);
//            return true;
//        }
//        if (id == R.id.fil_package_class){
//            Intent intent=new Intent(getActivity(), FilterActivity.class);
//            startActivityForResult(intent,1212);
//            return true;
//        }
//        if (id == R.id.fil_package_class){
//            Intent intent=new Intent(getActivity(), FilterActivity.class);
//            startActivityForResult(intent,1212);
//            return true;
//        }
//        if (id == R.id.fil_package_class){
//            Intent intent=new Intent(getActivity(), FilterActivity.class);
//            startActivityForResult(intent,1212);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode==1212){
//            if(resultCode==RESULT_OK){
//                Bundle extras=data.getExtras();
//                Log.e("response",extras.getString("categoryID"));
//            }
//        }
//    }
}
