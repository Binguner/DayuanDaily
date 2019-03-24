package com.nenguou.dayuandaily.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by binguner on 2017/8/24.
 */

public class NetworkUtils {
    /*
     * 获取活动网路信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    public static NetworkInfo getActiveNetworkInfo(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }
    /*
     *
     * 判断网络是否可用
     * 需添加权限 android.permission.ACCESS_NETWORK_STATE
     */
    public static boolean isAvailable(Context context){
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null &&info.isAvailable();
    }


}
