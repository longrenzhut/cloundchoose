package com.haiqi.yx

import com.alibaba.android.arouter.launcher.ARouter
import com.haiqi.base.Config
import com.haiqi.base.common.application.BaseApp


/**
 * Created by Administrator on 2017/8/2.
 */
class YxApp: BaseApp(){


    override fun onCreate() {
        super.onCreate()
        if(Config.DEBUG){
            ARouter.openLog()    // 打印日志
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

}