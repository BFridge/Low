package com.mindmac.eagleeye.adpater;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.mindmac.eagleeye.R;
import com.mindmac.eagleeye.activity.AppActivity;
import com.mindmac.eagleeye.entity.AppInfo;

import java.util.List;

/**
 * Created by Fridge on 16/4/23.
 */
public class AppListAdapter extends BaseAdapter {
    private List<AppInfo> list;
    private Context context;

    public AppListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.app_layout, null);
            holder = new AppViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (AppViewHolder)convertView.getTag();
        }
        holder.bindAppInfo(list.get(position));
        return convertView;
    }

    public void setList(List<AppInfo> list) {
        this.list = list;
    }



    public class AppViewHolder {
        protected TextView vName;
        protected TextView vApk;
        protected ImageView vIcon;
        protected ButtonFlat vExtract;
        protected ButtonFlat vShare;
        protected CardView vCard;

        public AppViewHolder(View v) {
            vName = (TextView) v.findViewById(R.id.txtName);
            vApk = (TextView) v.findViewById(R.id.txtApk);
            vIcon = (ImageView) v.findViewById(R.id.imgIcon);
            vExtract = (ButtonFlat) v.findViewById(R.id.btnExtract);
            vShare = (ButtonFlat) v.findViewById(R.id.btnShare);
            vCard = (CardView) v.findViewById(R.id.app_card);

        }

        private void bindAppInfo(final AppInfo appInfo){
            vName.setText(appInfo.getName());
            vApk.setText(appInfo.getAPK());
            vIcon.setImageDrawable(appInfo.getIcon());
            vCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity activity = (Activity) context;

                    Intent intent = new Intent(context, AppActivity.class);
                    intent.putExtra("app_name", appInfo.getName());
                    intent.putExtra("app_apk", appInfo.getAPK());
                    intent.putExtra("app_version", appInfo.getVersion());
                    intent.putExtra("app_source", appInfo.getSource());
                    intent.putExtra("app_data", appInfo.getData());
                    Bitmap bitmap = ((BitmapDrawable) appInfo.getIcon()).getBitmap();
                    intent.putExtra("app_icon", bitmap);
                    intent.putExtra("app_isSystem", appInfo.isSystem());
                    intent.putExtra("app_uid", appInfo.getUid());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        String transitionName = context.getResources().getString(R.string.transition_app_icon);

                        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, vIcon, transitionName);
                        context.startActivity(intent, transitionActivityOptions.toBundle());
                    } else {
                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
                    }
                }
            });
        }
    }
}
