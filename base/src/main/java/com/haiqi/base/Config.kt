package com.haiqi.base

import android.os.Build

/**
 * Created by Administrator on 2017/8/2.
 */
object Config {
    /**
     * false  正式  true  为测试
     */
    const val DEBUG = true
    const val BASE_URL = "https://haiqihuocang.com/api/"
    const val BASE_URL_TEST = "http://test.haiqihuocang.com/api/"//测试服务器

//    http://182.92.204.193:8081/v1/
    //状态栏侵染 sdk大于android5.0 上 使用自定义的状态栏
     var STATUSBAR: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

}