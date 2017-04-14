package com.ets.gd.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferencesManager {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor ;
    public static final String MY_PREFERENCES = "MyPrefs" ;
    public static final String  SYNC_STATE = "syncState";
    public static final String  IS_CAMERA_PERMISSION = "permissionCamera";
    public static final String IS_NEVER_ASK_AGAIN = "neveraskagain";
    public static final String  LAST_SYNC_DATE = "syncDate";
    public static final String LAST_SYNC_TIME = "syncTime";
    public static final String  MY_DEVICE_ID = "deviceID";
    public static final String  MY_SYNC_CUSTOMER_ID = "customerID";




    public SharedPreferencesManager(Context context) {
        sharedpreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();;
    editor = sharedpreferences.edit();
}

    public  void setString(String key, String value){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key){
        return sharedpreferences.getString(key, "Not Found");
    }


    public  void setBoolean(String key, boolean value){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public  boolean getBoolean(String key){
        return sharedpreferences.getBoolean(key, false);
    }


}
