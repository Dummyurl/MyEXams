package com.saxxis.myexamspace.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.helper.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.reg_name)
    TextInputLayout txtRegName;
    @BindView(R.id.reg_signup_et_name)
    TextInputEditText etName;

    @BindView(R.id.reg_username)
    TextInputLayout txtUserName;
    @BindView(R.id.reg_signup_et_username)
    TextInputEditText etUserName;


    @BindView(R.id.reg_useremail)
    TextInputLayout txtUserEmail;
    @BindView(R.id.reg_signup_et_useremail)
    TextInputEditText etUserEmail;


    @BindView(R.id.reg_pwd)
    TextInputLayout txtPassword;
    @BindView(R.id.reg_signup_et_pwd)
    TextInputEditText etPassword;


    @BindView(R.id.reg_mobile)
    TextInputLayout txtMobile;
    @BindView(R.id.reg_signup_et_mobile)
    TextInputEditText etMobile;

    @BindView(R.id.reg_signup_btn_submit)
    Button btnRegister;

    @BindView(R.id.cl_register)
    CoordinatorLayout regCoordinatorLayout;

    @BindView(R.id.registrationtoolbar)
    Toolbar regToolbar;


    @BindView(R.id.termsandcondition)
    TextView termsandConditions;


    private String name;
    private String username;
    private String email;
    private String pwd;
    private String mobilenumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        setSupportActionBar(regToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("New User? Sign up");;
    }

    @OnClick(R.id.reg_signup_btn_submit)
    void registerhere(){

        name = etName.getText().toString().trim();
        username = etUserName.getText().toString().trim();
        email = etUserEmail.getText().toString().trim();
        pwd = etPassword.getText().toString().trim();
        mobilenumber = etMobile.getText().toString().trim();

        if (validate(name,username,email,pwd,mobilenumber)) {
            callURLForRegister();
            return;
        }else {
            onSignUpFailed();
        }
    }

    private void callURLForRegister() {
//        StringRequest stringRequest= null;
//        try {

            class MyRegister extends AsyncTask<String,Void,String>{
                InputStream iStream = null;
                String urlstr = "";
                String data="";
                HttpURLConnection urlConnection = null;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    AppHelper.showDialog(RegistrationActivity.this,"Loading Please Wait...");
                }

                @Override
                protected String doInBackground(String... params) {
                    try {
                        URL url = new URL(AppConstants.USER_REGISTER+
                                URLEncoder.encode(name,"utf-8")+"&username="+URLEncoder.encode(username,"utf-8")+
                                "&password="+URLEncoder.encode(pwd,"utf-8")+"&email="+URLEncoder.encode(email,"utf-8")
                                +"&mobileno="+mobilenumber);

                         urlConnection = (HttpURLConnection)url.openConnection();
                        // Connecting to url
                        urlConnection.connect();

                        // Reading data from url
                        iStream = urlConnection.getInputStream();

                        BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                        StringBuffer sb = new StringBuffer();

                        String line = "";
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }

                        data = sb.toString();
                        Log.d("downloadUrl", data.toString());
                        br.close();
                    }catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (java.io.IOException e) {
                                e.printStackTrace();
                    }

                    return data;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    AppHelper.hideDialog();
                    try{
                        JSONObject jsonObject=new JSONObject(s);
                        if (jsonObject.getString("status").equals("ok")){
                            Toast.makeText(RegistrationActivity.this, "Registration SuccessFull Please Login", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish(); // finish registration activity
                        }else if (jsonObject.getString("status").equals("ko")){
                            AppHelper.Snackbar(RegistrationActivity.this,regCoordinatorLayout,
                                    jsonObject.getString("error_description"),R.color.white,R.color.colorPrimaryDark);
                        }
                    }catch (JSONException jse){

                    }
                }
            }

            MyRegister myRegister =new MyRegister();
            myRegister.execute();

    }

    private void onSignUpFailed() {
        AppHelper.Snackbar(this,regCoordinatorLayout, "Please Enter all the Details",R.color.colorBlueLight,R.color.colorRedDark);
        btnRegister.setEnabled(true);
    }

    private boolean validate(String name, String username, String email, String pwd, String mobilenumber) {
        boolean valid = true;

        if (name.isEmpty()) {
            txtRegName.setError("enter a valid Name");
            valid = false;
        } else {
            txtRegName.setError(null);
        }
        if (username.isEmpty()) {
            txtUserName.setError("enter a valid Username");
            valid = false;
        } else {
            txtUserName.setError(null);
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtUserEmail.setError("enter a valid Email");
            valid = false;
        } else {
            txtUserEmail.setError(null);
        }
        if (pwd.isEmpty() || pwd.length() < 4 || pwd.length() > 10) {
            txtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            txtPassword.setError(null);
            txtPassword.setErrorEnabled(false);
        }
        if (mobilenumber.isEmpty() || mobilenumber.length()!=10) {
            txtMobile.setError("enter a valid Mobile Number");
            valid = false;
        } else {
            txtMobile.setError(null);
        }
        return valid;
    }

    @OnClick(R.id.termsandcondition)
    void termsandconditions(){
        Intent intent = new Intent(RegistrationActivity.this,TermsAndConditionsActivity.class);
        startActivity(intent);
    }

}
