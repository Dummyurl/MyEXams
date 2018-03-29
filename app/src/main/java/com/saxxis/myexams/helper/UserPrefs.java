package com.saxxis.myexams.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by android2 on 5/31/2017.
 */

public class UserPrefs {

    private Context mContext;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private static final String USER_PREFS="shareduserprefs";

    private static final String loggedIn = "isloggedIn";

    private static final String SESSION_KEY = "sessionID";
    private static final String USER_ID_KEY = "e_userID";

    private static final String NAME_KEY = "e_name";
    private static final String USERNAME_KEY = "e_username";
    private static final String EMAIL_KEY = "e_email";
    private static final String MN_KEY = "e_mobilenumber";
    private static final String REG_DATE = "regdate";

    public UserPrefs(Context context){
        this.mContext = context;
        mPrefs = mContext.getSharedPreferences(USER_PREFS,Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn(){
        return mPrefs.getBoolean(loggedIn,false);
    }

    public void setLogOutUser(){
        mPrefs.edit().clear().apply();
    }

    public void setLoggedIn(){
        mPrefs.edit().putBoolean(loggedIn,true).apply();
    }

    public void setSessionId(String id){
        mPrefs.edit().putString(SESSION_KEY,id).apply();
    }

    public String getSessionId(){
        return mPrefs.getString(SESSION_KEY,"guest");
    }

    public void setUserId(String id){
        mPrefs.edit().putString(USER_ID_KEY,id).apply();
    }

    public String getUserId(){
        return mPrefs.getString(USER_ID_KEY,"guest");
    }

    public void setName(String name){
        mPrefs.edit().putString(NAME_KEY,name).apply();
    }

    public String getName(){
        return mPrefs.getString(NAME_KEY,"guest");
    }

    public void setUserName(String un){
        mPrefs.edit().putString(USERNAME_KEY,un).apply();
    }

    public String getUserName(){
        return mPrefs.getString(USERNAME_KEY,"Hai");
    }

    public void setEmail(String email){
        mPrefs.edit().putString(EMAIL_KEY,email).apply();
    }

    public String getEmail(){
        return mPrefs.getString(EMAIL_KEY,"Update EmailId");
    }

    public void setMobileNumber(String mn){
        mPrefs.edit().putString(MN_KEY,mn).apply();
    }

    public String getMobileNumber(){
        return mPrefs.getString(MN_KEY,"Update Mobile No!");
    }

    public void setRegisterDate(String registerDate) {
        mPrefs.edit().putString(REG_DATE,registerDate).apply();
    }

    public String getRegisterDate(){
        return mPrefs.getString(REG_DATE,"registered date!");
    }
}
