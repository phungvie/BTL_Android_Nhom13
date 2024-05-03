package com.example.sonic.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
 public class MySharedPreferences {
    protected static final String MY_SHARED_PRE="MY_SHARED_PRE";
    protected Context mContext;

    protected MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }
    protected void putBooleanValue(String key,boolean value){
        SharedPreferences mSharedPreferences=mContext.getSharedPreferences(MY_SHARED_PRE,Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putBoolean(key,value);
        mEditor.apply();
    }

    protected boolean getBooleanValue(String key){
        SharedPreferences mSharedPreferences=mContext.getSharedPreferences(MY_SHARED_PRE,Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(key,false);
    }

    protected void putStringValue(String key,String value){
        SharedPreferences mSharedPreferences=mContext.getSharedPreferences(MY_SHARED_PRE,Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(key,value);
        mEditor.apply();
    }

    protected String getStringValue(String key){
        SharedPreferences mSharedPreferences=mContext.getSharedPreferences(MY_SHARED_PRE,Context.MODE_PRIVATE);
        return mSharedPreferences.getString(key,"");
    }
}
