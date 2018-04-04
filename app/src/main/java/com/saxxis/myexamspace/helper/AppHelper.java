package com.saxxis.myexamspace.helper;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saxxis.myexamspace.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by android2 on 5/22/2017.
 */

public class AppHelper {

    private static ProgressDialog mDialog;
    private static Dialog dialog;

    private static Context context;

    /**
     * method to show the progress dialog
     *
     * @param mContext this is parameter for showDialog method
     */
    public static void showDialog(Context mContext, String message) {
        context=mContext;
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage(message);
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(true);
        mDialog.show();
    }

    /**
     * method to show the progress dialog
     *
     * @param mContext this is parameter for showDialog method
     */
    public static void showDialog(Context mContext, String message, boolean cancelable) {
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage(message);
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(cancelable);
        mDialog.show();
    }

    /**
     * method to hide the progress dialog
     */
    public static void hideDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public static boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void LaunchActivity(Activity mContext, Class mActivity) {
        Intent mIntent = new Intent(mContext, mActivity);
        mContext.startActivity(mIntent);
        mContext.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static boolean isNetWorkAvailable(Context context){

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3

    }

    /**
     * method to show snack bar
     *
     * @param mContext    this is the first parameter for Snackbar  method
     * @param view        this is the second parameter for Snackbar  method
     * @param Message     this is the thirded parameter for Snackbar  method
     * @param colorId     this is the fourth parameter for Snackbar  method
     * @param TextColorId this is the fifth parameter for Snackbar  method
     */
    public static void Snackbar(Context mContext, View view, String Message, int colorId, int TextColorId) {
        Snackbar snackbar = Snackbar.make(view, Message, Snackbar.LENGTH_SHORT);
        View snackView = snackbar.getView();
        snackView.setBackgroundColor(ContextCompat.getColor(mContext, colorId));
        TextView snackbarTextView = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        snackbarTextView.setTextColor(ContextCompat.getColor(mContext, TextColorId));
        snackbar.show();
    }

    public static void Snackbar(FragmentActivity activity, FrameLayout fmlProfile, String message, String s) {

    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(Html.fromHtml(html,Html.FROM_HTML_MODE_COMPACT).toString(),Html.FROM_HTML_MODE_COMPACT);
        } else {
            result = Html.fromHtml(Html.fromHtml(html).toString());
        }
        return result;
    }


    public static String spanDateFormater(String datefromdata){
        String dateString = datefromdata;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // use SimpleDateFormat to define how to PARSE the INPUT
        try{
                Date date = sdf.parse(dateString);
                // at this point you have a Date-Object with the value of
                // 1437059241000 milliseconds
                // It doesn't have a format in the way you think
                // use SimpleDateFormat to define how to FORMAT the OUTPUT
                System.out.println( sdf.format(date) );
                sdf = new SimpleDateFormat("dd-MMM-yyyy");
                System.out.println(sdf.format(date));
            return sdf.format(date);
//            return date.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSelectMonth(Date date) {
        Date dateString = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // use SimpleDateFormat to define how to PARSE the INPUT
            System.out.println(sdf.format(dateString));
            return sdf.format(dateString);
    }

    public static String getddMMyyyy(String date) {
        SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date dateString = null;
        try {
            dateString = simpledate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        // use SimpleDateFormat to define how to PARSE the INPUT
        System.out.println(sdf.format(dateString));
        return sdf.format(dateString);
    }

    public static String getddMMMyyyy(String date) {
        SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");

        Date dateString = null;
        try {
            dateString = simpledate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        // use SimpleDateFormat to define how to PARSE the INPUT
        System.out.println(sdf.format(dateString));
        return sdf.format(dateString);
    }

    public static void showToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
    public static void showToast(Context context,String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }
}
