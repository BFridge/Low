package com.mindmac.eagleeye.application;

import android.app.Application;

import com.mindmac.eagleeye.entity.AppPreferences;

/**
 * Created by Fridge on 16/4/23.
 */
public class LowApplication extends Application{
    private static AppPreferences sAppPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppPreferences = new AppPreferences(this);
    }

    public static AppPreferences getAppPreferences() {
        return sAppPreferences;
    }
}