package com.haiqi.base.common.fragment

import com.haiqi.base.common.listener.IBaseView
import com.haiqi.base.common.presenter.BasePt


/**
 * Created by zhutao on 2017/8/3.
 *  viewpager 时候用到
 */
abstract class LazyBaseFra<T: BasePt<H>,H: IBaseView,M>: LazyDelegateFra<T>() {

    internal val mPrsenter by lazy{
        getPresenter()
    }

    final override fun setPresenter() {
        super.setPresenter()
        mActivity?.let {
            mPrsenter.attachActivity(it)
        }
        mPrsenter.attachFragment(this)
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