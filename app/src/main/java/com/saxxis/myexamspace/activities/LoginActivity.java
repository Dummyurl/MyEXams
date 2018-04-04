package com.saxxis.myexamspace.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.saxxis.myexamspace.R;
import com.saxxis.myexamspace.app.AppConstants;
import com.saxxis.myexamspace.app.MyExamsApp;
import com.saxxis.myexamspace.helper.AppHelper;
import com.saxxis.myexamspace.helper.UserPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_btn_submit)
    Button btnLogin;

    @BindView(R.id.login_et_email)
    AppCompatEditText edtEmail;

    @BindView(R.id.login_et_pwd)
    AppCompatEditText edtPassword;

    @BindView(R.id.login_ipl_email)
    TextInputLayout txtinputemail;

    @BindView(R.id.login_ipl_pwd)
    TextInputLayout txtinputpassword;

    @BindView(R.id.cllogin)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar_login)
    Toolbar logintb;

    @BindView(R.id.create_account)
    Button txtCreateAccount;

    @BindView(R.id.termsandcondition)
    TextView termsandConditions;

    @BindView(R.id.forgotpassword)
    TextView forgotPassword;


//    @BindView(R.id.btn_sign_in)
    SignInButton signInButton;
    LoginButton facebookSignInButton;
    CallbackManager callbackManager;

    String FILENAME = "AndroidSSO_data";
    private static String APP_ID="1617019491651740";

    private UserPrefs userPrefs;
    private String finishthisintent;

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPrefs = new UserPrefs(LoginActivity.this);
        ButterKnife.bind(this);

        signInButton = (SignInButton)findViewById(R.id.btn_sign_in);
        facebookSignInButton = (LoginButton) findViewById(R.id.btn_facebook_signin);
//        facebookSignInButton.setReadPermissions("email");
//        facebookSignInButton.setReadPermissions("user_friends");
//        facebookSignInButton.setReadPermissions("public_profile");
//        facebookSignInButton.setReadPermissions("user_birthday");
//        facebookSignInButton.setPadding(10,10,10,10);
        facebookSignInButton.setText("Login with Facebook");
        // If using in a fragment

//        facebookSignInButton.setFragment(this);


        setSupportActionBar(logintb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        finishthisintent=getIntent().getExtras().getString("finishthis");

        logintb.setTitle(getResources().getString(R.string.login));
        logintb.setTitleTextColor(Color.parseColor("#FFFFFF"));

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

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        facebookSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email","user_birthday","contact_email"));
                callbackManager = CallbackManager.Factory.create();
                facebookSignInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("response",loginResult.getAccessToken()+"");
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());
                                        // Application code
                                            Log.e("response",object.toString());
                                            Log.e("response",AppConstants.USER_SOCIAL_LOGIN + object);
                                            AppHelper.showDialog(LoginActivity.this,"Loading Please Wait...");

                                        StringRequest stringRequest = null;
                                        try {
                                            stringRequest = new StringRequest(AppConstants.USER_SOCIAL_LOGIN + URLEncoder.encode(object.toString(),"UTF-8"),
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String s) {
                                                            try {
                                                                Log.e("response",s);
                                                                AppHelper.hideDialog();
                                                                JSONObject response = new JSONObject(s);
                                                                if (response.getString("status").equals("ok")){
                                                                    userPrefs.setUserId(response.getString("userid"));
                                                                    userPrefs.setName(response.getString("name"));
                                                                    userPrefs.setUserName(response.getString("username"));
                                                                    userPrefs.setEmail(response.getString("email"));
                                                                    userPrefs.setLoggedIn();
                                                                    AppHelper.LaunchActivity(LoginActivity.this,MainActivity.class);
                                                                    finish();
                                                                }
                                                                if (response.getString("status").equals("ko")){
                                                                    AppHelper.LaunchActivity(LoginActivity.this,RegistrationActivity.class);
                                                                    finish();
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
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }

                                        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);


                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "FaceBook Login Has Failed Please Try Again", Toast.LENGTH_SHORT).show();
                        // App code
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, "FaceBook Login Has Failed Please Try Again", Toast.LENGTH_SHORT).show();
                        // App code
                    }
                });

            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout.LayoutParams llParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                llParams.setMargins(5,5,5,5);

                LinearLayout ll =new LinearLayout(LoginActivity.this);
                ll.setLayoutParams(llParams);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setPadding(10,10,10,10);
                ll.setBackgroundColor(Color.WHITE);

                TextView textView = new TextView(LoginActivity.this);
                textView.setLayoutParams(llParams);
                textView.setTextColor(Color.BLACK);
                textView.setPadding(10,10,10,10);
                textView.setTextSize(16);
                textView.setText("Please Enter Your Registered Email-Id To get Verification Code");
                textView.setBackgroundColor(Color.WHITE);
                ll.addView(textView);

                final EditText editText = new EditText(LoginActivity.this);
                editText.setLayoutParams(llParams);
                editText.setTextColor(Color.BLACK);
                editText.setPadding(10,10,10,10);
                editText.setTextSize(16);
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                editText.setHint("Enter Email-Id");
                editText.setBackgroundColor(Color.WHITE);
                ll.addView(editText);

                new AlertDialog.Builder(LoginActivity.this)
                        .setView(ll)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("response",editText.getText().toString());
                                getEmailsSendCode(editText.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        });
    }

    private void getEmailsSendCode(final String email) {

        AppHelper.showDialog(LoginActivity.this,"Loading Please Wait...");
        final StringRequest stringRequest = new StringRequest(AppConstants.FORGOT_PASSWORD+email,
           new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response",response);
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("ok")){
                        AppHelper.hideDialog();
                        startActivity(new Intent(LoginActivity.this,ForgotChangePasswordActivity.class)
                                .putExtra("emailId",email));
                        finish();
                    }
                    if (status.equals("ko")){
                        AppHelper.hideDialog();
//                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        AppHelper.Snackbar(LoginActivity.this,coordinatorLayout,jsonObject.getString("message")+" Try Again!"
                                ,R.color.colorPrimaryDark,R.color.white);
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
                AppHelper.hideDialog();
            }
        });

        MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
//                        updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

//            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String username = acct.getGivenName();
            String photourl = String.valueOf(acct.getPhotoUrl());
//            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

//            Log.e("response", "Name: " + personName + ", email: " + email
//                    + ", Image: "+photourl+" userName :"+username);

            AppHelper.showDialog(LoginActivity.this,"Loading Please Wait...");
            try{
            JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",username);
                jsonObject.put("email",email);
                jsonObject.put("personname",personName);
                Log.e("response",AppConstants.USER_G_PLUS_LOGIN + jsonObject.toString());
                StringRequest stringRequest = null;
                try {
                    stringRequest = new StringRequest(AppConstants.USER_G_PLUS_LOGIN + URLEncoder.encode(jsonObject.toString(),"UTF-8"),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    AppHelper.hideDialog();
                                    Log.e("response",s);
                                    try {
                                        JSONObject response = new JSONObject(s);
                                        if (response.getString("status").equals("ok")){
                                            userPrefs.setUserId(response.getString("userid"));
                                            userPrefs.setName(response.getString("name"));
                                            userPrefs.setUserName(response.getString("username"));
                                            userPrefs.setEmail(response.getString("email"));
                                            userPrefs.setLoggedIn();
                                            AppHelper.LaunchActivity(LoginActivity.this,MainActivity.class);
                                            finishAffinity();
                                        }
                                        if (response.getString("status").equals("ko")){
                                            AppHelper.LaunchActivity(LoginActivity.this,RegistrationActivity.class);
                                            finish();
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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                MyExamsApp.getMyInstance().addToRequestQueue(stringRequest);

            }catch (JSONException jse){

            }

        } else {

        }
    }


    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
//            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
//            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (requestCode != RC_SIGN_IN) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }


    @OnClick(R.id.login_btn_submit)
    void loginbutton(){
        String name=edtEmail.getText().toString();
        String password=edtPassword.getText().toString();

        if (!validate(name,password)) {
            onLoginFailed();
            return;
        }

        try {

            String url = AppConstants.USER_LOGIN+ URLEncoder.encode(name,"utf-8")+"&password="+URLEncoder.encode(password,"utf-8");

            AppHelper.showDialog(LoginActivity.this,"Loading Please Wait...");
            StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    AppHelper.hideDialog();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        Log.e("response",response);
                        String status=jsonObject.getString("status");
                        if (status.equals("ok")){
                           userPrefs.setUserId(jsonObject.getString("userid"));
                           userPrefs.setName(jsonObject.getString("name"));
                           userPrefs.setUserName(jsonObject.getString("username"));
                           userPrefs.setEmail(jsonObject.getString("email"));
                           userPrefs.setMobileNumber(jsonObject.getString("mobileno"));
                           userPrefs.setRegisterDate(jsonObject.getString("registerDate"));
                           userPrefs.setLoggedIn();

                          AppHelper.LaunchActivity(LoginActivity.this,MainActivity.class);
                          finish();

                        }else if (status.equals("ko")){
                            AppHelper.Snackbar(LoginActivity.this,coordinatorLayout,jsonObject.getString("error_description"),R.color.darkgrey,R.color.white);
                        }else {
                            Toast.makeText(LoginActivity.this,""+response,Toast.LENGTH_SHORT).show();
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

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void onLoginFailed() {
        AppHelper.Snackbar(this,coordinatorLayout, "Login failed",R.color.colorBlueLight,R.color.colorRedDark);
    }

    public boolean validate(String email, String password) {
        boolean valid = true;

        if (email.isEmpty()) {
            txtinputemail.setError("enter a valid Username");
            valid = false;
        } else {
            txtinputemail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            txtinputpassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            txtinputpassword.setError(null);
        }

        return valid;
    }

    @OnClick(R.id.create_account)
    void createAcc(){
        AppHelper.LaunchActivity(LoginActivity.this,RegistrationActivity.class);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.termsandcondition)
    void termsandconditions(){
        Intent intent =new Intent(LoginActivity.this,TermsAndConditionsActivity.class);
        startActivity(intent);
    }

}
