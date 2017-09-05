package com.haiqi.base.common.presenter

import com.haiqi.base.common.activity.DelegateAct
import com.haiqi.base.common.fragment.DelegateFra

/**
 * Created by zhtuao on 2017/8/3.
 */
interface IBasePt<T> {


    fun attachActivity(mAct: DelegateAct)

    fun attachFragment(mFra: DelegateFra)

    fun detachView()
    fun getView(): T?

    fun onResume()


}