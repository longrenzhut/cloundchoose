package com.haiqi.base.common.activity

import android.app.Activity
import com.haiqi.base.R
import com.haiqi.base.common.listener.ILifeCycle
import com.haiqi.base.thirdsdk.swipbackhelper.SwipeBackHelper
import com.haiqi.base.thirdsdk.swipbackhelper.SwipeListener
import com.haiqi.base.thirdsdk.swipbackhelper.Utils
import com.haiqi.base.utils.CommonUtil
import com.haiqi.base.utils.dip2px
import com.haiqi.base.utils.glide.GlideHelper

/**
 * Created by zhutao on 2017/8/2.
 * act 的生命周期 可以统一初始化一些类
 */
class LifeCycle: ILifeCycle{

    private var mActivity: Activity? = null

    private var isUseSwipeBack = false;//是否能手势滑动退出
    private var isBacking = false;//是否手势滑动中

    override fun OnCreate(mActivity: Activity) {
        this.mActivity = mActivity

        CommonUtil.init(mActivity)
        GlideHelper.init()
    }

    /**
     * 是否使用滑动退出
     */
    override fun OnAttach(isUseSwipeBack: Boolean) {
        this.isUseSwipeBack = isUseSwipeBack
        if(isUseSwipeBack) {
            SwipeBackHelper.onCreate(mActivity)
            val open = Utils.convertActivityFromTranslucent(mActivity)
            SwipeBackHelper.getCurrentPage(mActivity)
                    .setSwipeBackEnable(true)
                    .setSwipeRelateEnable(true)
                    .setSwipeSensitivity(0.6f)
                    .setSwipeRelateOffset(320)
                    .setSwipeEdge(dip2px(18))
                    .setPageTranslucent(!open)
        }
    }

    /**
     * 禁止手势滑动退出
     */
    fun setSwipeBackNoEnable(){
        SwipeBackHelper.getCurrentPage(mActivity)
                .setSwipeBackEnable(false)
    }

    override fun onPostCreate() {
        if(isUseSwipeBack)
            SwipeBackHelper.onPostCreate(mActivity)
    }

    fun setOnExitListener(onExit: (Int)-> Unit){
        if(isUseSwipeBack){
            SwipeBackHelper.getCurrentPage(mActivity)
                    .addListener(object : SwipeListener{
                        override fun onScroll(percent: Float, px: Int) {

                            isBacking = percent != 0f
                        }

                        override fun onEdgeTouch() {
                        }

                        override fun onScrollToClose() {
                            isBacking = false
                            onExit(R.id.iv_left)
                        }

                    })
        }
    }

    override fun onBackPressed(): Boolean {
        return isBacking
    }

    override fun onStart() {

    }

    override fun onRestart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }


    override fun OnDestroy() {
        if(isUseSwipeBack)
            SwipeBackHelper.onDestroy(mActivity)
        mActivity = null
    }


}