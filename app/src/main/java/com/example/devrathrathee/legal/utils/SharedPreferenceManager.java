package com.example.devrathrathee.legal.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferenceManager sharedPreferenceManager;

    private SharedPreferenceManager(){
    }

    public static SharedPreferenceManager getInstance(Context context){
        if (sharedPreferenceManager == null){
            sharedPreferenceManager = new SharedPreferenceManager();
            sharedPreferences = context.getSharedPreferences(Constants.LEGAL_SHARED_PREFS, Context.MODE_PRIVATE);
        }

        return sharedPreferenceManager;
    }

    public void putString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,"");
    }
}
