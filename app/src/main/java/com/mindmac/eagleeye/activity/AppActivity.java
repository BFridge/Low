package com.mindmac.eagleeye.activity;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindmac.eagleeye.R;
import com.mindmac.eagleeye.Util;
import com.mindmac.eagleeye.application.LowApplication;
import com.mindmac.eagleeye.entity.AppInfo;
import com.mindmac.eagleeye.entity.AppPreferences;
import com.mindmac.eagleeye.utils.UtilsApp;

import java.io.File;
import java.util.Set;

public class AppActivity extends BaseActivity implements SwitchCompat.OnCheckedChangeListener{

    //Const
    private int UNINSTALL_REQUEST_CODE = 1;

    // Load Settings
    private AppPreferences appPreferences;

    // General variables
    private AppInfo appInfo;
    private Set<String> appsFavorite;
    private Set<String> appsHidden;

    @Override
    protected void initVariables() {
        appPreferences = LowApplication.getAppPreferences();
        String appName = getIntent().getStringExtra("app_name");
        String appApk = getIntent().getStringExtra("app_apk");
        String appVersion = getIntent().getStringExtra("app_version");
        String appSource = getIntent().getStringExtra("app_source");
        String appData = getIntent().getStringExtra("app_data");
        Bitmap bitmap = getIntent().getParcelableExtra("app_icon");
        Drawable appIcon = new BitmapDrawable(getResources(), bitmap);
        Boolean appIsSystem = getIntent().getExtras().getBoolean("app_isSystem");
        int appUid = getIntent().getIntExtra("app_uid",0);
        appInfo = new AppInfo(appName, appApk, appVersion, appSource, appData, appIcon, appIsSystem, appUid);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_app);
        TextView header =   (TextView) findViewById(R.id.header);
        ImageView icon = (ImageView) findViewById(R.id.app_icon);
        ImageView icon_googleplay = (ImageView) findViewById(R.id.app_googleplay);
        TextView name = (TextView) findViewById(R.id.app_name);
        TextView version = (TextView) findViewById(R.id.app_version);
        TextView apk = (TextView) findViewById(R.id.app_apk);
        CardView googleplay = (CardView) findViewById(R.id.id_card);
        CardView start = (CardView) findViewById(R.id.start_card);
        CardView extract = (CardView) findViewById(R.id.extract_card);
        CardView uninstall = (CardView) findViewById(R.id.uninstall_card);
        CardView cache = (CardView) findViewById(R.id.cache_card);
        CardView clearData = (CardView) findViewById(R.id.clear_data_card);
        SwitchCompat monitor = (SwitchCompat) findViewById(R.id.switch_monitor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        monitor.setOnCheckedChangeListener(this);
        monitor.setChecked(UtilsApp.isAppConfigured(Integer.toString(appInfo.getUid())));
        icon.setImageDrawable(appInfo.getIcon());
        name.setText(appInfo.getName());
        apk.setText(appInfo.getAPK());
        version.setText(appInfo.getVersion());

        // CardView
        if (appInfo.isSystem()) {
            icon_googleplay.setVisibility(View.GONE);
            start.setVisibility(View.GONE);
        } else {
            googleplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UtilsApp.goToGooglePlay(AppActivity.this, appInfo.getAPK());
                }
            });



            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(appInfo.getAPK());
                        startActivity(intent);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });

            extract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File logFile = UtilsApp.getLogFile(appInfo.getAPK());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(logFile),"text/plain");
                    startActivity(intent);
                }
            });
            uninstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
                    intent.setData(Uri.parse("package:" + appInfo.getAPK()));
                    intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                    startActivityForResult(intent, UNINSTALL_REQUEST_CODE);
                }
            });
        }

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            Util.addFrameworkLogAppUids(appInfo.getUid());
            Log.i("shitshit", "[AppActivity] : " + appInfo.getUid());
        }else{
            Util.removeFrameworkLogAppUtils(appInfo.getUid());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_forward, R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UNINSTALL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.i("App", "OK");
                Intent intent = new Intent(AppActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
            } else if (resultCode == RESULT_CANCELED) {
                Log.i("App", "CANCEL");
            }
        }
    }
}