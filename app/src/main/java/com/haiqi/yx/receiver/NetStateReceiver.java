package com.haiqi.yx.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



/**
 * 网络状态广播
 * L.d("网络改变：当前网络状态" + netState.getNetType());
 */
public class  NetStateReceiver extends BroadcastReceiver {

    /**
     * 未连接
     */
    private static final int NOTCONNECTED = -1;

    /**
     * 通过该标记解决网络变化时，广播通知会发送多次的问题
     */
    private static int lastType = NOTCONNECTED;


    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        if (networkInfo != null && networkInfo.isConnected()) {
            int currentType = networkInfo.getType();
            if (currentType != lastType) {
                lastType = currentType;
                change(context);
            }
        } else {
            if (lastType != NetStateReceiver.NOTCONNECTED) {
                lastType = NetStateReceiver.NOTCONNECTED;
                change(context);
            }
        }
    }

    /**
     * 网络改变
     * @param context
     */
    private void change(Context context) {
//        Mylogger("网络改变：当前网络状态");
    }

    /**
     * 获取当前已激活的网络链接信息
     *
     * @param context 应用程序上下文
     * @return 返回当前已激活的网络链接信息
     */
    private NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }
}
