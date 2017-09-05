package com.haiqi.base.utils;

/**
 * Created by Administrator on 2017/7/14.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *  网络相关工具类
 */
public  class NetworkUtils {

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return current.isAvailable();
    }
}
