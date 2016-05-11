package com.mindmac.eagleeye.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.mindmac.eagleeye.R;
import com.mindmac.eagleeye.adpater.AppListAdapter;
import com.mindmac.eagleeye.entity.AppInfo;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AppListActivity extends BaseActivity {

    //view

    private List<AppInfo> appList;
    private ListView listView;
    private AppListAdapter adapter;
    private ProgressWheel progressWheel;
    private Toolbar toolbar;


    @Override
    protected void initVariables() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_app_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("App设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.lv_app_list);
        listView.setDivider(null);
        progressWheel = (ProgressWheel) findViewById(R.id.progress);
        progressWheel.setVisibility(View.VISIBLE);
    }

    @Override
    protected void loadData() {
        //// TODO: 16/4/23 读取设置文件
        new getInstalledApps().execute();
    }


    class getInstalledApps extends AsyncTask<Void, String, Void> {
        private Integer actualApps;
        private Integer totalApps;
        public getInstalledApps() {
            actualApps = 0;
            totalApps = 0;
            appList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            final PackageManager packageManager = getPackageManager();
            List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
            totalApps = packages.size();
            // Get Sort Mode
            // Comparator by Name (default)
            Collections.sort(packages, new Comparator<PackageInfo>() {
                @Override
                public int compare(PackageInfo p1, PackageInfo p2) {
                    return packageManager.getApplicationLabel(p1.applicationInfo).toString().toLowerCase().compareTo(packageManager.getApplicationLabel(p2.applicationInfo).toString().toLowerCase());
                }
            });

            // Installed & System Apps
            for (PackageInfo packageInfo : packages) {
                if (!(packageManager.getApplicationLabel(packageInfo.applicationInfo).equals("") || packageInfo.packageName.equals(""))) {

                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        try {
                            // Non System Apps
                            AppInfo tempApp = new AppInfo(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString(), packageInfo.packageName, packageInfo.versionName, packageInfo.applicationInfo.sourceDir, packageInfo.applicationInfo.dataDir, packageManager.getApplicationIcon(packageInfo.applicationInfo), false, packageInfo.applicationInfo.uid);
                            appList.add(tempApp);
                        } catch (OutOfMemoryError e) {
                            //TODO Workaround to avoid FC on some devices (OutOfMemoryError). Drawable should be cached before.
                            AppInfo tempApp = new AppInfo(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString(), packageInfo.packageName, packageInfo.versionName, packageInfo.applicationInfo.sourceDir, packageInfo.applicationInfo.dataDir, getResources().getDrawable(R.drawable.ic_android), false, packageInfo.applicationInfo.uid);
                            appList.add(tempApp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                    else {

//                        try {
//                            // System Apps
//                            AppInfo tempApp = new AppInfo(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString(), packageInfo.packageName, packageInfo.versionName, packageInfo.applicationInfo.sourceDir, packageInfo.applicationInfo.dataDir, packageManager.getApplicationIcon(packageInfo.applicationInfo), true);
//                            appSystemList.add(tempApp);
//                        } catch (OutOfMemoryError e) {
//                            //TODO Workaround to avoid FC on some devices (OutOfMemoryError). Drawable should be cached before.
//                            AppInfo tempApp = new AppInfo(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString(), packageInfo.packageName, packageInfo.versionName, packageInfo.applicationInfo.sourceDir, packageInfo.applicationInfo.dataDir, getResources().getDrawable(R.drawable.ic_android), false);
//                            appSystemList.add(tempApp);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                }

                actualApps++;
                publishProgress(Double.toString((actualApps * 100) / totalApps));
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            progressWheel.setProgress(Float.parseFloat(progress[0]));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapter = new AppListAdapter(AppListActivity.this);
            adapter.setList(appList);
            listView.setAdapter(adapter);
            progressWheel.setVisibility(View.GONE);

        }

    }



}
