package com.saxxis.myexamspace.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.fragments.HomeFragment;
import com.saxxis.myexamspace.fragments.MainProfileFragment;
import com.saxxis.myexamspace.fragments.PackagesFragment;
import com.saxxis.myexamspace.fragments.TrendingFragment;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.helper.UserPrefs;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_container)
    DrawerLayout main_drawerlayout;
    @BindView(R.id.main_toolbar)
    Toolbar main_toolbar;
    @BindView(R.id.left_navigation)
    NavigationView main_navview;
    @BindView(R.id.btm_navigation)
    BottomNavigationView navigation;
//    @BindView(R.id.toolbarimg)
//    ImageView imgLogotoolbar;

    private GoogleApiClient mGoogleApiClient;

    private UserPrefs userPrefs;

//    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;

    LayoutInflater mInflater;

    CircleImageView profileimage;

    /**
     * The {@link Tracker} used to record screen views.
     */
    private Tracker mTracker;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragmentMain();
                return true;
                case R.id.navigation_profile:
                    if (userPrefs.isLoggedIn()){
                        MainProfileFragment profile_fragment = new MainProfileFragment();

                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content,profile_fragment).commit();
                    }else {
                        // AppHelper.LaunchActivity(MainActivity.this,LoginActivity.class);
                        Intent inten=new Intent(MainActivity.this,LoginActivity.class);
                        inten.putExtra("finishthis","yes");
                        startActivity(inten);
                    }
                    return true;
                case R.id.navigation_settings:
                    TrendingFragment trendingFragment = new TrendingFragment();
//                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.content,trendingFragment).commit();
//                    navigation.getMenu().getItem(2).setChecked(true);
//                    SettingsFragment settingsfrag=new SettingsFragment();

                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,trendingFragment).commit();
                    return true;
                case R.id.navigation_packages:
                    PackagesFragment packageFragment = new PackagesFragment();

                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,packageFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPrefs = new UserPrefs(MainActivity.this);

        ButterKnife.bind(this);
        setSupportActionBar(main_toolbar);
        main_toolbar.setLogo(R.drawable.myexam);

        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout mActionBarCustom = (LinearLayout) mInflater.inflate(
                R.layout.customtoolbar, null);

        getSupportActionBar().setCustomView(mActionBarCustom);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        ImageView ivMenu = (ImageView) mActionBarCustom.findViewById(R.id.nav_menu);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main_drawerlayout.isDrawerOpen(GravityCompat.START)) {
                    main_drawerlayout.closeDrawer(GravityCompat.START);
                } else {
                    main_drawerlayout.openDrawer(GravityCompat.START);
                }
            }
        });

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (!userPrefs.isLoggedIn()){
            main_navview.inflateMenu(R.menu.logout_left_navigation);
        }else {
            main_navview.inflateMenu(R.menu.left_navigation);
            main_navview.inflateHeaderView(R.layout.header_main);

            View headerView = main_navview.getHeaderView(0).getRootView();
            TextView txtname = (TextView)headerView.findViewById(R.id.profile_name);
            profileimage = (CircleImageView)headerView.findViewById(R.id.profilepic);
            txtname.setText(userPrefs.getUserName());
            getUserProfile();
        }

        main_navview.setNavigationItemSelectedListener(onleftNavItemSelected);
        setFragmentMain();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d("Failed", "onConnectionFailed:" + connectionResult);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        /*
        * analytics tracker */
        mTracker = MyExamsApp.getMyInstance().getDefaultTracker();
        mTracker.setScreenName("Home Page");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void setFragmentMain() {
        HomeFragment homefrag = new HomeFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,homefrag).commit();
    }

    private NavigationView.OnNavigationItemSelectedListener onleftNavItemSelected =
            new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.leftnav_myprofile:
                    if(userPrefs.isLoggedIn()){
                        MainProfileFragment profile_fragment=new MainProfileFragment();

                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content,profile_fragment).commit();
                        navigation.getMenu().getItem(1).setChecked(true);
                    }else {
                      //  AppHelper.LaunchActivity(MainActivity.this,LoginActivity.class);
                        Intent inten=new Intent(MainActivity.this,LoginActivity.class);
                        inten.putExtra("finishthis","yes");
                        startActivity(inten);
                    }
                    break;
                case R.id.leftnav_exams:
                    AppHelper.LaunchActivity(MainActivity.this,ExamsListActivity.class);
                    break;
                case R.id.leftnav_subjects:
                     AppHelper.LaunchActivity(MainActivity.this,SubjectsActivity.class);
                    break;
               /* case R.id.leftnav_currentaffairs:
                    AppHelper.LaunchActivity(MainActivity.this,CurrentAffairsActivity.class);
                    break;*/
                case R.id.leftnav_ca_english:
                    AppHelper.LaunchActivity(MainActivity.this,CAInEnglishActivity.class);
                    break;
                case R.id.leftnav_ca_telugu:
                    AppHelper.LaunchActivity(MainActivity.this,CAInTeluguActivity.class);
                    break;
                case R.id.leftnav_ca_hindi:
                    AppHelper.LaunchActivity(MainActivity.this,CAInHindiActivity.class);
                    break;
                case R.id.leftnav_currentaff_quizz:
                    AppHelper.LaunchActivity(MainActivity.this,CurrentAffairsQuizzActivity.class);
                    break;
                /*case R.id.leftnav_latest_updates:
                    AppHelper.LaunchActivity(MainActivity.this,EducationNewsActivity.class);
                    break;*/
                case R.id.leftnav_packages:
                    PackagesFragment packageFragment = new PackagesFragment();

                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,packageFragment).commit();
                    navigation.getMenu().getItem(3).setChecked(true);
                    break;
                case R.id.leftnav_notifications:
                    AppHelper.LaunchActivity(MainActivity.this,NotificationActivity.class);
                    break;
                case R.id.leftnav_academics:
                    AppHelper.LaunchActivity(MainActivity.this,AcademicsActivity.class);
                    break;
                case R.id.leftnav_trending:
                    TrendingFragment trendingFragment = new TrendingFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,trendingFragment).commit();
                    navigation.getMenu().getItem(2).setChecked(true);
                    break;
                case R.id.leftnav_logout:
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Are You Sure")
                            .setMessage("You Want To Logout ?")
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    userPrefs.setLogOutUser();
                                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                            new ResultCallback<Status>() {
                                                @Override
                                                public void onResult(Status status) {

                                                }
                                            });
                                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                                            .Callback() {
                                        @Override
                                        public void onCompleted(GraphResponse graphResponse) {
                                            LoginManager.getInstance().logOut();
                                        }
                                    }).executeAsync();
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                    break;
                ////  logout nav clicks
                case R.id.nav_home:
                    setFragmentMain();
                    break;
                case R.id.nav_login:
                    Intent inten=new Intent(MainActivity.this,LoginActivity.class);
                    inten.putExtra("finishthis","yes");
                    startActivity(inten);
                    break;
                case R.id.nav_register:
                    AppHelper.LaunchActivity(MainActivity.this,RegistrationActivity.class);
                    break;
            }
            main_drawerlayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if (main_drawerlayout.isDrawerOpen(GravityCompat.START)) {
            main_drawerlayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("Are You Sure");
                builder.setMessage("You Want to Exit MyExamSpace ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                       // AppHelper.LaunchActivity(MainActivity.this,LoginActivity.class);
                        finishAffinity();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //  setFragmentMain();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }


    private void getUserProfile() {
        StringRequest stringRequest = new StringRequest(AppConstants.PROFILE_IMAGE + userPrefs.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject =new JSONObject(s);
                            if (jsonObject.getString("status").equals("ok")){
                                String imagepath =jsonObject.getString("imagepath");
                                Picasso.with(MainActivity.this).load(imagepath)
                                        .placeholder(R.drawable.icon_profile)
                                        .error(R.drawable.icon_profile)
                                        .into(profileimage, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }
                                            @Override
                                            public void onError() {

                                            }
                                        });
                            }
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
