package com.haiqi.yx

import com.alibaba.android.arouter.launcher.ARouter
import com.haiqi.base.Config
import com.haiqi.base.common.application.BaseApp
import com.haiqi.base.utils.Mylogger
import com.tencent.smtt.sdk.QbSdk
import org.jetbrains.anko.doAsync

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
        initTBS()

    }

    /**
     * 初始化TBS浏览服务X5内核
     */
    private fun initTBS() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.setDownloadWithoutWifi(true)//非wifi条件下允许下载X5内核
        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Mylogger.d(" onViewInitFinished is " + arg0)
            }

            override fun onCoreInitFinished() {}
        }

        doAsync {
            QbSdk.initX5Environment(applicationContext, cb)
        }

        //x5内核初始化接口
    }

}