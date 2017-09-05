package com.haiqi.base.common.listener

import android.app.Activity

/**
 * Created by zhutao on 2017/8/2.
 */
interface ILifeCycle {

    fun OnCreate(mActivity: Activity)

    fun OnAttach(isUseisSwipeBackEnable: Boolean)

    fun onPostCreate()

    fun onStart()

    fun onRestart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun OnDestroy()

    fun onBackPressed(): Boolean
}