package com.haiqi.base.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import android.util.MutableBoolean
import com.haiqi.base.common.application.BaseApp

/**
 * Created by zhutao on 2017/6/16.
 * 通用的帮助类
 */
object CommonUtil{


    private var width: Int = 0;
    private var height: Int = 0;

    fun init(act: Activity) {
        if(width != 0)
            return
        val dm = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(dm)
        width = px2dip(dm.widthPixels)
        height = px2dip(dm.heightPixels)
    }

    public fun getSWidth() = width
    public fun getSHeight() = height


    private val scale by lazy(LazyThreadSafetyMode.NONE) {
        BaseApp.getApp().resources.displayMetrics.density
    }

    public fun getScales(): Float = scale

    /**
     * 版本号1.12
     */
    fun getVersionName(): String {
        val manager = BaseApp.getApp().packageManager
        try {
            val info = manager.getPackageInfo(BaseApp.getApp().packageName, 0)
            return info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()

        }

        return ""
    }


    fun getVersionCode(): Int {
        val manager = BaseApp.getApp().packageManager
        try {
            val info = manager.getPackageInfo(BaseApp.getApp().packageName, 0)
            return info.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()

        }

        return 1
    }

    /**
     * 将list 转换成 用逗号分开的 string
     */
    fun listToString(data: MutableList<String>?): String {

        return listToString(data,",")
    }

    fun listToString(data: MutableList<String>?,splite: String): String {
        if (data == null) {
            return ""
        }
        val result = StringBuilder()
        var flag = false
        for (string in data) {
            if (flag) {
                result.append(splite) // 分隔符
            } else {
                flag = true
            }
            result.append(string)
        }
        return result.toString()
    }


}