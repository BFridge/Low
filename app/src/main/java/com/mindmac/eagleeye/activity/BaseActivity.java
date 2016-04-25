package com.mindmac.eagleeye.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Fridge on 16/4/23.
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initView(savedInstanceState);
        loadData();
    }

    protected abstract void initVariables();
    protected abstract void initView(Bundle savedInstanceState);
    protected abstract void loadData();

}
