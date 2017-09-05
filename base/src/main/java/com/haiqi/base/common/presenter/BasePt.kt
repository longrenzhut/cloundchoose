package com.haiqi.base.common.presenter

import com.haiqi.base.common.activity.DelegateAct
import com.haiqi.base.common.fragment.DelegateFra
import com.haiqi.base.http.Params
import com.haiqi.base.http.ReqCallBack
import java.lang.ref.WeakReference

/**
 * Created by zhutao on 2017/8/3.
 */


open class BasePt<T>: IBasePt<T>{


    private lateinit var mActivity: DelegateAct



    private var mView: WeakReference<T>? = null


    fun attach(t: T){
        mView =  WeakReference<T>(t)
    }

    private var mFrament: DelegateFra? = null

    override fun attachActivity(mAct: DelegateAct) {
        this.mActivity = mAct
    }

    override fun attachFragment(mFra: DelegateFra) {
        this.mFrament = mFra
    }



    override fun detachView() {
        mView?.clear()
    }

    override fun getView(): T? {
        mView?.let {
            it.get()?.let{
                return it
            }
        }
        return null
    }

    override fun onResume() {

    }

    fun <T> setRequest(url: String, mParams: Params, req: ReqCallBack<T>){
        mFrament?.let{
            it.setRequest(url,mParams,req)
        } ?:
                mActivity.setRequest(url,mParams,req)
    }

}