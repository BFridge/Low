package com.mindmac.eagleeye.entity;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Fridge on 16/4/23.
 */
public class AppInfo implements Serializable {

    private String name;
    private String apk;
    private String version;
    private String source;
    private String data;
    private Drawable icon;
    private Boolean system;
    private int uid;

    public AppInfo(String name, String apk, String version, String source, String data, Drawable icon, Boolean isSystem, int uid) {
        this.name = name;
        this.apk = apk;
        this.version = version;
        this.source = source;
        this.data = data;
        this.icon = icon;
        this.system = isSystem;
        this.uid = uid;
    }

    public AppInfo(String string) {
        String[] split = string.split("##");
        if (split.length == 6) {
            this.name = split[0];
            this.apk = split[1];
            this.version = split[2];
            this.source = split[3];
            this.data = split[4];
            this.system = Boolean.getBoolean(split[5]);
            this.uid = Integer.parseInt(split[6]);
        }
    }

    public String getName() {
        return name;
    }

    public String getAPK() {
        return apk;
    }

    public String getVersion() {
        return version;
    }

    public String getSource() {
        return source;
    }

    public String getData() {
        return data;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Boolean isSystem() {
        return system;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String toString() {
        return getName() + "##" + getAPK() + "##" + getVersion() + "##" + getSource() + "##" + getData() + "##" + isSystem() + "##" + getUid();
    }

}

