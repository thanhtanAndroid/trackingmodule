package com.example.templatemodule.analystic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class DataLocal {
    public static DataLocal sInstance;
    public Context mContext;

    public static DataLocal getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataLocal(context.getApplicationContext());
        }
        return sInstance;
    }

    private DataLocal(final Context context) {
        this.mContext = context;
    }

    public void setString(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getString(key, "");
    }

    private void setInt(String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(mContext).edit().putInt(key, value).apply();
    }

    private int getInt(String key) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getInt(key, 0);
    }
}
