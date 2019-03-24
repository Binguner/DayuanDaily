package com.nenguou.dayuandaily.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtil {

    /**
     * 透明化状态栏
     * @param activity
     */
    public static void transparentStatusbar(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, int colorId){
        // Android 5.0 提供的直接修改状态栏颜色的方法
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = activity.getWindow();
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            transparentStatusbar(activity);
            ViewGroup contentView = activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(colorId);
            contentView.addView(statusBarView,layoutParams);
        }
    }

    public static int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if(resourceId > 0){
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置状态栏颜色为黑色
     */
    public static void setStatusbarTextBlack(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}
