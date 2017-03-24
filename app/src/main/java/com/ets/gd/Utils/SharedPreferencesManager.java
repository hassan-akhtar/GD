package com.ets.gd.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor ;
    public static final String MY_PREFERENCES = "MyPrefs" ;
    public static final String  SYNC_STATE = "syncState";
    public static final String  IS_CAMERA_PERMISSION = "permissionCamera";
    public static final String IS_NEVER_ASK_AGAIN = "neveraskagain";




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
