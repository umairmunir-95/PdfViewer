package com.cessna.a172m_1975_poh.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.widget.TextView;

import com.cessna.a172m_1975_poh.R;


public class Helpers {

    public static Integer getPageNumber(Context context)
    {
        int pageNumber=0;
        SharedPrefferences sharedPrefferences=new SharedPrefferences(context);
        try {
            if (sharedPrefferences != null) {
                if (sharedPrefferences.getString(context.getResources().getString(R.string.page_number)) != null) {
                    pageNumber = Integer.valueOf(sharedPrefferences.getString(context.getResources().getString(R.string.page_number)));
                    pageNumber=pageNumber-1;
                }
            }
        }
        catch (Exception e)
        {
            pageNumber=0;
            e.getMessage();
        }
        return pageNumber;
    }

    public static String getAppMode(Context context)
    {
        String appMode="normal";
        SharedPrefferences sharedPrefferences=new SharedPrefferences(context);
        try {
            if (sharedPrefferences != null) {
                if (sharedPrefferences.getString(context.getResources().getString(R.string.enable_night_mode)) != null) {
                    appMode = (sharedPrefferences.getString(context.getResources().getString(R.string.enable_night_mode)));
                }
            }
        }
        catch (Exception e)
        {
            appMode="normal";
            e.getMessage();
        }
        return appMode;
    }

    public static String getScrollType(Context context)
    {
        String scrollType="vertical";
        SharedPrefferences sharedPrefferences=new SharedPrefferences(context);
        try {
            if (sharedPrefferences != null) {
                if (sharedPrefferences.getString(context.getResources().getString(R.string.enable_horizontal_scroll)) != null) {
                    scrollType= (sharedPrefferences.getString(context.getResources().getString(R.string.enable_horizontal_scroll)));
                }
            }
        }
        catch (Exception e)
        {
            scrollType="vertical";
            e.getMessage();
        }
        return scrollType;
    }

    public static void setAppInfo(Context context, TextView textViewAppInfo) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("App Ver:");
        sb.append(pInfo.versionName + "." + pInfo.versionCode);
        textViewAppInfo.setTextSize(12);
        textViewAppInfo.setTypeface(null, Typeface.ITALIC);
        textViewAppInfo.setText(sb.toString());
    }
}
