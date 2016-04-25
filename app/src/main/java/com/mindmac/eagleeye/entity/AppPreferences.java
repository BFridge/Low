package com.mindmac.eagleeye.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.mindmac.eagleeye.R;
import com.mindmac.eagleeye.utils.UtilsApp;

import java.util.HashSet;
import java.util.Set;

public class AppPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public static final String KeyIsRooted = "prefIsRooted";

    // List
    public static final String KeyFavoriteApps = "prefFavoriteApps";
    public static final String KeyHiddenApps = "prefHiddenApps";
    public static final String KeyWatchedApp = "preWatchedApp";
    public static final String KeyCustomPath = "preCustomPath";
    public static final String KeyCustomFilename = "preCustomFileName";

    public AppPreferences(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sharedPreferences.edit();
        this.context = context;
    }

    public int getRootStatus() {
        return sharedPreferences.getInt(KeyIsRooted, 0);
    }

    public void setRootStatus(int rootStatus) {
        editor.putInt(KeyIsRooted, rootStatus);
        editor.commit();
    }


    public Set<String> getFavoriteApps() {
        return sharedPreferences.getStringSet(KeyFavoriteApps, new HashSet<String>());
    }

    public void setFavoriteApps(Set<String> favoriteApps) {
        editor.remove(KeyFavoriteApps);
        editor.commit();
        editor.putStringSet(KeyFavoriteApps, favoriteApps);
        editor.commit();
    }

    public Set<String> getHiddenApps() {
        return sharedPreferences.getStringSet(KeyHiddenApps, new HashSet<String>());
    }

    public void setHiddenApps(Set<String> hiddenApps) {
        editor.remove(KeyHiddenApps);
        editor.commit();
        editor.putStringSet(KeyHiddenApps, hiddenApps);
        editor.commit();
    }

    public Set<String> getWatchedApps() {
        return sharedPreferences.getStringSet(KeyWatchedApp, new HashSet<String>());
    }

    public void setWatchedApp(Set<String> watchedApps){
        editor.remove(KeyWatchedApp);
        editor.commit();
        editor.putStringSet(KeyWatchedApp, watchedApps);
        editor.commit();
    }

    public String getCustomPath() {
        return sharedPreferences.getString(KeyCustomPath, UtilsApp.getDefaultAppFolder().getPath());
    }

    public void setCustomPath(String path) {
        editor.putString(KeyCustomPath, path);
        editor.commit();
    }

    public String getCustomFilename() {
        return sharedPreferences.getString(KeyCustomFilename, "1");
    }
}
