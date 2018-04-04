package com.saxxis.myexamspace.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.activities.EditProfileActivity;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.helper.UserPrefs;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {


    @BindView(R.id.image_profile)
    CircleImageView imageprofile;
    @BindView(R.id.profile_real_name)
    TextView txtName;
    @BindView(R.id.profile_real_username)
    TextView txtUserName;
    @BindView(R.id.profile_real_email)
    TextView txtEmail;
    @BindView(R.id.profile_real_rd)
    TextView txtregDate;
    @BindView(R.id.profile_real_mn)
    TextView txtMobileNumber;
    @BindView(R.id.share)
    Button share;
    @BindView(R.id.rateme)
    Button rateme;

    @BindView(R.id.edit_profile)
    TextView txtEditProfile;

    private UserPrefs userPrefs;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        userPrefs=new UserPrefs(getActivity());
        txtName.setText(userPrefs.getName());
        txtUserName.setText(userPrefs.getUserName());
        txtEmail.setText(userPrefs.getEmail());
        txtregDate.setText(AppHelper.spanDateFormater(userPrefs.getRegisterDate()));
        txtMobileNumber.setText(userPrefs.getMobileNumber());

        getUserProfile();
        return view;
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
                                Picasso.with(getActivity()).load(imagepath).resize(125,125)
                                        .placeholder(R.drawable.icon_profile)
                                        .error(R.drawable.icon_profile)
                                        .into(imageprofile);
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

    @OnClick(R.id.share)
    void shareApp(){
        String shareBody = "https://play.google.com/store/apps/details?id=com.saxxis.myexams&rdid=com.saxxis.myexams";//+"com.enventpc_03.nav11";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MyExamSpace (Open it in Google Play Store to Download and Install the Application)");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        getActivity().startActivity(Intent.createChooser(sharingIntent, "Share App via"));
    }

    @OnClick(R.id.rateme)
    void rateApplication(){
        Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
        sharingIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.saxxis.myexams&rdid=com.saxxis.myexams"));
        getActivity().startActivity(sharingIntent);
    }

    @OnClick(R.id.edit_profile)
    void editProfile(){
        AppHelper.LaunchActivity(getActivity(), EditProfileActivity.class);
    }


}
