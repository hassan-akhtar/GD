package com.ets.gd.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonActions {

    private CharSequence inputStr = "";

    private static CustomProgressDialog mCustomProgressDialog;

    private Context mContext = null;

    public CommonActions() {

    }

    public CommonActions(Context context) {
        this.mContext = context;
    }

    public void setActivityContext(Context context) {
        mContext = context;
    }

    public static void showProgressDialog(Context con) {
        mCustomProgressDialog = new CustomProgressDialog(con);
        mCustomProgressDialog.show("");
    }

    public static void showProgressDialog(Context con, String text) {
        mCustomProgressDialog = new CustomProgressDialog(con);
        mCustomProgressDialog.show(text);
    }

    public static void DismissesDialog() {

        mCustomProgressDialog.dismiss("");
    }


    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
        inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }

    public boolean isNameValid(String name) {
        boolean isValid = false;
        String expression = "^[a-zA-Z ]*$";
        inputStr = name;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }

    public Date stringToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy", Locale.US);
        Date convertedDate = null;
        try {
            convertedDate = format.parse(date);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    public String dateToString(Date date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("mm/dd/yyyy", Locale.US);
        String stringDate = "";
        stringDate = dateformat.format(date);
        return stringDate;
    }

}
