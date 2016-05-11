package com.mindmac.eagleeye.application;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.mindmac.eagleeye.entity.AppPreferences;
import com.mindmac.eagleeye.utils.AppUtils;
import com.mindmac.eagleeye.utils.ShellUtils;
import com.mindmac.eagleeye.utils.SystemPropertiesProxy;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;

/**
 * Created by Fridge on 16/4/23.
 */
public class LowApplication extends Application{
    private static AppPreferences sAppPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppPreferences = new AppPreferences(this);
        String absoluteLowPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LOW";
        ShellUtils.setProp(AppUtils.LOW_PATH,absoluteLowPath);
    }

    public static AppPreferences getAppPreferences() {
        return sAppPreferences;
    }
}
