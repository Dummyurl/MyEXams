package com.saxxis.myexams.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.saxxis.myexams.R;
import com.saxxis.myexams.app.AppConstants;
import com.saxxis.myexams.app.MyExamsApp;
import com.saxxis.myexams.helper.AppHelper;
import com.saxxis.myexams.helper.PermissionHandler;
import com.saxxis.myexams.helper.UserPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.saxxis.myexams.R.id.username;
import static com.saxxis.myexams.app.AppConstants.SELECT_PROFILE_CAMERA;
import static com.saxxis.myexams.app.AppConstants.SELECT_PROFILE_PICTURE;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.cledtprofile)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.til_name)TextInputLayout tilName;
    @BindView(R.id.tilPassword)TextInputLayout tilPassword;
    @BindView(R.id.tilConfPassword)TextInputLayout tilConfPassword;
    @BindView(R.id.til_email)TextInputLayout tilEmail;
    @BindView(R.id.tilconfemail)TextInputLayout tilConfEmail;
    @BindView(R.id.til_mobile)TextInputLayout tilMobilenumber;
    @BindView(R.id.til_city)TextInputLayout tilCity;

    @BindView(R.id.name)
    TextView txtName;

    @BindView(username)
    TextView txtUsername;

    @BindView(R.id.password)
    TextInputEditText txtPassword;

    @BindView(R.id.conf_password)
    TextInputEditText txtConfPassword;

    @BindView(R.id.emailaddress)
    TextInputEditText txtEmailAddress;

    @BindView(R.id.confemailaddeess)
    TextInputEditText txtConfirmEmailAdd;

    @BindView(R.id.city)
    TextInputEditText txtCity;

    @BindView(R.id.mobileno)
    TextInputEditText mobileNumber;

    @BindView(R.id.dateofbirth)
    TextView txtDateOfBirth;

    @BindView(R.id.choose_profilepic)
    TextView chooseProfilepick;

    @BindView(R.id.editprofiletoolbar)
    Toolbar edToolbar;

    private UserPrefs userPrefs;

    DatePickerDialog datePickerDialog;
    Calendar c;

    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        setSupportActionBar(edToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edToolbar.setTitle("Edit Profile");
        edToolbar.setTitleTextColor(Color.WHITE);

        c = Calendar.getInstance();
        userPrefs = new UserPrefs(EditProfileActivity.this);
        txtName.setText(userPrefs.getName());
        txtUsername.setText(userPrefs.getUserName());
        txtUsername.setEnabled(false);
        mobileNumber.setText(userPrefs.getMobileNumber());
        txtEmailAddress.setText(userPrefs.getEmail());
        txtConfirmEmailAdd.setText(userPrefs.getEmail());

        datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtDateOfBirth.setText(dayOfMonth+"-"+(month+1)+"-"+year);
            }
        },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        Calendar dot = Calendar.getInstance();
        dot.add(Calendar.YEAR ,-100);
        datePickerDialog.getDatePicker().setMinDate(dot.get(Calendar.YEAR));
    }

    @OnClick(R.id.dateofbirth)
    void getDateOfBirth(){
        datePickerDialog.show();
    }

    @OnClick(R.id.choose_profilepic)
    void chooseProfilePick(){

        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @OnClick(R.id.submit_profile)
    void getEditProfile(){
        if (valid()) {
            AppHelper.showDialog(EditProfileActivity.this, "Updating Please Wait...");
//            Log.e("response", AppConstants.EDIT_MYPROFILE + "&email="++"&name="++"&username="++"&mobile="++"&password="++"&id="+userPrefs.getUserId());
            StringRequest stringRequest = null;
            try{
                String url = AppConstants.EDIT_MYPROFILE +
                        "&email="+txtEmailAddress.getText().toString()+
                        "&name="+ URLEncoder.encode(txtName.getText().toString(),"UTF-8")+
                        "&username="+URLEncoder.encode(userPrefs.getUserName(),"UTF-8")+
                        "&mobile="+URLEncoder.encode(mobileNumber.getText().toString(),"UTF-8")+
                        "&password="+URLEncoder.encode(txtPassword.getText().toString(),"UTF-8")+
                        "&id="+userPrefs.getUserId()+
                        "&dob="+URLEncoder.encode(txtDateOfBirth.getText().toString(),"UTF-8");

                Log.e("response",url);
                stringRequest = new StringRequest(url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Log.e("response",s);
                                AppHelper.hideDialog();
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    if (jsonObject.getString("status").equals("ok")){
                                        userPrefs.setName(txtName.getText().toString());
                                        userPrefs.setMobileNumber(mobileNumber.getText().toString());//.isEmpty()?userPrefs.getMobileNumber():mobileNumber.getText().toString());
                                        userPrefs.setEmail(txtEmailAddress.getText().toString());
                                        Toast.makeText(EditProfileActivity.this, "Profile Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        AppHelper.LaunchActivity(EditProfileActivity.this,MainActivity.class);
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
    }

    private boolean valid() {
        if(txtName.getText().toString().isEmpty()){
            tilName.setError("Please Enter Name");
            return false;
        }
        if (!(txtPassword.getText().toString().isEmpty() || txtConfPassword.getText().toString().isEmpty())){
            if (!txtPassword.getText().toString().trim().equals(txtConfPassword.getText().toString().trim())){
                    tilConfPassword.setError("Confirm Password Must be same as Password");
                    return false;
            }
            if (txtPassword.getText().toString().length()<6){
                tilConfPassword.setError("Confirm Password Must be same as Password");
                return false;
            }
        }
        if (txtEmailAddress.getText().toString().isEmpty()){
            tilEmail.setError("Enter Your Email");
            return false;
        }
        if(txtConfirmEmailAdd.getText().toString().isEmpty()){
            tilConfEmail.setError("Confirm Your Email Address");
            return false;
        }
        if (!txtEmailAddress.getText().toString().equals(txtConfirmEmailAdd.getText().toString())){
            tilEmail.setError("Confirm Email Must Be Same as Email");
            return false;
        }

//        if (txtCity.getText().toString().isEmpty()){
//            tilCity.setError("Please Enter Your City");
//            return false;
//        }
//        if (mobileNumber.getText().toString().isEmpty()){
//            tilMobilenumber.setError("Please Enter Mobile Number");
//            return false;
//        }
//
//        if (txtDateOfBirth.getText().toString().isEmpty()){
//            txtDateOfBirth.setError("Please Select Your Date Of Birth");
//            return false;
//        }
        return true;
    }


    private void cameraIntent() {
        if (PermissionHandler.checkPermission(EditProfileActivity.this, Manifest.permission.CAMERA)) {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            startActivityForResult(cameraIntent, SELECT_PROFILE_CAMERA);
        } else {

            PermissionHandler.requestPermission(EditProfileActivity.this, Manifest.permission.CAMERA);
        }
    }

    private void galleryIntent() {
        if (PermissionHandler.checkPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent mIntent = new Intent();
            mIntent.setType("image/*");
            mIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(mIntent, getString(R.string.select_picture)), SELECT_PROFILE_PICTURE);
        } else {
            PermissionHandler.requestPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PROFILE_PICTURE:
                    Uri filePath = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        onCaptureImageResult(bitmap);
                    }catch (Exception e){

                    }
                    break;
                case SELECT_PROFILE_CAMERA:
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    onCaptureImageResult(thumbnail);
                    break;
            }
        }
    }

    private void onCaptureImageResult(Bitmap thumbnail) {
        imagePath = getStringImage(thumbnail);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //userImage.setImageBitmap(thumbnail);
        uploadImagetoServer();
    }

    private void uploadImagetoServer() {
        AppHelper.showDialog(EditProfileActivity.this,"Loading Please Wait...");
        StringRequest request = new StringRequest(Request.Method.POST,AppConstants.ADD_IMAGE,//+"&image="+imagePath,
         new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AppHelper.hideDialog();
                Toast.makeText(EditProfileActivity.this, "Profile Image Updated SuccessFully", Toast.LENGTH_SHORT).show();
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Cookie",mUser.getSessionId());
//                return headers;
//            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("userid",userPrefs.getUserId());
                headers.put("image",imagePath);
                Log.e("response",imagePath+"\n"+userPrefs.getUserId());
                return headers;
            }
        };

        MyExamsApp.getMyInstance().addToRequestQueue(request);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
