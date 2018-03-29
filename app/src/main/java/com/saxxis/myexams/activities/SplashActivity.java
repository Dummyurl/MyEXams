package com.saxxis.myexams.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.saxxis.myexams.R;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.UserPrefs;

public class SplashActivity extends AppCompatActivity {

    private UserPrefs userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userPrefs=new UserPrefs(SplashActivity.this);

        if (AppHelper.isNetWorkAvailable(SplashActivity.this)){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                           AppHelper.LaunchActivity(SplashActivity.this,MainActivity.class);
                        finish();
                    }
                },3000);
        }else {
            new AlertDialog.Builder(SplashActivity.this)
                    .setTitle("No Internet Connection")
                    .setMessage("It looks like your internet connection is off. Please turn it on and try again")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SplashActivity.this.finish();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }
}
