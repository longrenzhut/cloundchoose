package com.haiqi.base.common.activity

import com.haiqi.base.common.listener.IBaseView
import com.haiqi.base.common.presenter.BasePt

/**
 * Created by zhtuao on 2017/8/3.
 * 封装mvp
 */
abstract class BaseAct<out T: BasePt<H>,H: IBaseView>: DelegateAct(){

    val mPrsenter by lazy{
        getPresenter()
    }

    final override fun setPresenter() {
        super.setPresenter()
        mPrsenter.attachActivity(this)
    }

    abstract fun getPresenter(): T


    override fun onDestroy() {
        super.onDestroy()
        mPrsenter.detachView()
    }


    override fun onResume() {
        super.onResume()
        mPrsenter.onResume()
    }
}
