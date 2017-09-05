package com.haiqi.base.utils

import android.util.Log
import com.haiqi.base.Config
import okhttp3.internal.platform.Platform
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.Logger.addLogAdapter



/**
 * Created by zhutao on 2017/8/2.
 */
object Mylogger{

    fun d(msg: String) {
        Logger.d(msg)
    }
}