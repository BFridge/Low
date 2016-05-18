package com.mindmac.eagleeye.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindmac.eagleeye.R;
import com.mindmac.eagleeye.Util;
import com.mindmac.eagleeye.entity.LogEntity;
import com.mindmac.eagleeye.utils.ShellUtils;
import com.mindmac.eagleeye.utils.SystemPropertiesProxy;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Fridge on 16/5/10.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    private ListView mainList;
    private WelcomeItem item_app_list = new WelcomeItem("App设置", "查看和设置监控开关");
    private WelcomeItem item_log_upload = new WelcomeItem("上传测试", "将LOW目录下所有文件上传至服务器");
    private WelcomeItem item_test = new WelcomeItem("测试","测试");
    private WelcomeItem item_shell_test = new WelcomeItem("monkey测试", "对本机中所有app进行monkey测试");
    private WelcomeItem item_delete_file = new WelcomeItem("删除log", "删除掉LowPath下的所有文件");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mainList = (ListView) findViewById(R.id.lv_main_list);
        mainList.setAdapter(new WelcomeAdapter(this));
        TextView tutorial = (TextView) findViewById(R.id.txtTutorial);
        tutorial.setText(Html.fromHtml("欢迎使用<b>Low监测工具</b><br/>请选择你想要进行的操作:"));
        TextView lowPath = (TextView) findViewById(R.id.txtLowPath);
        lowPath.setText("Log 保存位置: " + System.getenv("EXTERNAL_STORAGE") + "/LOW");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("LowManager");
    }

    @Override
    protected void loadData() {
    }

    private class WelcomeItem{
        public WelcomeItem(String mainTitle, String des) {
            this.title = mainTitle;
            this.des = des;
        }

        
        private String title;
        private String des;
    }

    private class WelcomeAdapter extends BaseAdapter{
        private ArrayList<WelcomeItem> itemArrayList;
        private Context mContext;



        public WelcomeAdapter(Context mContext) {
            this.mContext = mContext;
            itemArrayList = new ArrayList<>();
            itemArrayList.add(item_app_list);
            itemArrayList.add(item_test);
            itemArrayList.add(item_shell_test);
            itemArrayList.add(item_log_upload);
            itemArrayList.add(item_delete_file);
        }

        @Override
        public int getCount() {
            return itemArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_list_layout,null);
            convertView.setOnClickListener(MainActivity.this);
            TextView tittle = (TextView)convertView.findViewById(R.id.txtTitle);
            TextView des = (TextView) convertView.findViewById(R.id.txtDes);
            
            tittle.setText(itemArrayList.get(position).title);
            des.setText(itemArrayList.get(position).des);
            convertView.setTag(itemArrayList.get(position));
            convertView.setOnClickListener(MainActivity.this);
            return convertView;
        }
    }


    @Override
    public void onClick(View v) {
        WelcomeItem item = (WelcomeItem)v.getTag();
        if(item == item_app_list){
            Intent intent = new Intent(MainActivity.this, AppListActivity.class);
            startActivity(intent);
        }else if(item == item_test){
            test();
        }else if(item == item_shell_test){
            ShellUtils.setProp(Util.IGNORE_UIDS_PROP_KEY, true);
            Toast.makeText(MainActivity.this, "值测试：" + SystemPropertiesProxy.getBoolean(Util.IGNORE_UIDS_PROP_KEY, false), Toast.LENGTH_SHORT).show();
//            Set<String> apkList = AppUtils.getAllAppList();
            ShellUtils.monkeyApp(200);
        } else if (item == item_log_upload) {
            //上传文件至django服务器

        } else if (item == item_delete_file) {
            ShellUtils.clearApp();
            Toast.makeText(MainActivity.this, "删除完成", Toast.LENGTH_SHORT).show();
        }
    }
    
    
    private void test(){
        String url = "http://10.205.3.131:8000/log/";
        LogEntity logEntity = new LogEntity();
        logEntity.apkName = "shit";


        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(logEntity))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                });
    }


}
