package com.haiqi.base.common.application

import android.content.res.Configuration
import android.content.res.Resources
import com.haiqi.base.Config
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by Administrator on 2017/8/2.
 */
open class BaseApp: AbsApp(){

    companion object {
        private lateinit var instance: BaseApp

        fun getApp(): BaseApp = instance
    }


    override fun onCreate() {
        super.onCreate()

        instance = this

        Logger.addLogAdapter(object : AndroidLogAdapter(){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return Config.DEBUG
            }
        })
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.fontScale != 1f)
        //非默认值
            resources
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        if (res.configuration.fontScale != 1f) {//非默认值
            val newConfig = Configuration()
            newConfig.setToDefaults()//设置默认
            res.updateConfiguration(newConfig, res.displayMetrics)
        }
        return res
    }
}