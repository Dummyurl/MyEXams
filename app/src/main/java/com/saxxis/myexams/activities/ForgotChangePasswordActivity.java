package com.saxxis.myexams.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotChangePasswordActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_changepwd)Toolbar mToolBar;

    @BindView(R.id.tipl_verificationcode)TextInputLayout tilverificationcode;
    @BindView(R.id.tipl_newpassword)TextInputLayout tilnewPassword;
    @BindView(R.id.tipl_conf_passwrd)TextInputLayout tilconfNewPassword;

    @BindView(R.id.edtverifycode)TextInputEditText verifyCode;
    @BindView(R.id.edt_newpassword)TextInputEditText edtNewPassword;
    @BindView(R.id.edt_confpassword)TextInputEditText edtConfpassword;

    @BindView(R.id.forgotpasswrd_submit)Button forgotpasswrd_submit;

    String emails="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_change_password);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            emails = extras.getString("emailId");
        }

    }

    @OnClick(R.id.forgotpasswrd_submit)
    void forgotPassword(){

        String verificationcode = verifyCode.getText().toString();
        String newPassword = edtNewPassword.getText().toString();
        String confPassword = edtConfpassword.getText().toString();

        if (valid(verificationcode,newPassword,confPassword)){

        AppHelper.showDialog(ForgotChangePasswordActivity.this,"Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(AppConstants.FORGOT_CHANGEPASSWORD +emails
                +"&verificationcode="+verificationcode+"&password="+newPassword,
          new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AppHelper.hideDialog();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.e("response",response);
                    String status=jsonObject.getString("status");
                    if (status.equals("ok")){
                        Toast.makeText(ForgotChangePasswordActivity.this, jsonObject.getString("message")+" Please Login", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    if (status.equals("ko")){
                        Toast.makeText(ForgotChangePasswordActivity.this,"You Entered Wrong Verification Code", Toast.LENGTH_SHORT).show();
                    }
//                    else {
//                        Toast.makeText(LoginActivity.this,""+response,Toast.LENGTH_SHORT).show();
//                    }
                }
                catch (JSONException e) {
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
    }

    private boolean valid(String verificationcode, String newPassword, String confPassword) {
        if (verificationcode.isEmpty()){
            tilverificationcode.setError("Please Enter Verification Code");
            return false;
        }
        if (newPassword.isEmpty()){
            tilnewPassword.setError("Please Enter New Password ");
            return false;
        }

        if (newPassword.length()<6){
            tilnewPassword.setError("Maintain Password Length Must be greater Than 6 Or More");
            return false;
        }

        if (confPassword.isEmpty()){
            tilconfNewPassword.setError("Please Enter Confirm New Password ");
            return false;
        }

        if (!newPassword.equals(confPassword)){
            edtConfpassword.setError("Confirm Password Must Be Same As New Password");
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
