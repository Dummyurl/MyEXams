package com.saxxis.myexamspace.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.fragments.profiletabhosts.MyFavouritesFragment;
import com.saxxis.myexamspace.fragments.profiletabhosts.MyPurchasePakgeFragment;
import com.saxxis.myexamspace.fragments.profiletabhosts.MyResultsFragment;
import com.saxxis.myexamspace.helper.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainProfileFragment extends Fragment {

    @BindView(R.id.profilemaintabl)
    TabLayout tablayout;

    @BindView(R.id.viewpager_profile)
    CustomViewPager customviewpager;

    SectionProfilePagerAdapter profilePagerAdapter;

    public MainProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_profile, container, false);
        ButterKnife.bind(this,view);

        profilePagerAdapter = new SectionProfilePagerAdapter(getChildFragmentManager());

        customviewpager.setAdapter(profilePagerAdapter);
        tablayout.setupWithViewPager(customviewpager);
        customviewpager.setPagingEnabled(false);

        // Inflate the layout for this fragment
        return view;
    }



    public class SectionProfilePagerAdapter extends FragmentPagerAdapter{

        public SectionProfilePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment=null;
            if (position==0){
                fragment =new MyProfileFragment();
            }else if (position==1){
                fragment =new MyResultsFragment();
            }else if(position==2){
                fragment =new MyPurchasePakgeFragment();
            }else if(position==3){
                fragment =new MyFavouritesFragment();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0:
                    return "My Profile";
                case 1:
                    return "My Results";
                case 2:
                    return "My Packages";
                case 3:
                    return "My Favourites";
            }
            return null;
        }
    }
}
