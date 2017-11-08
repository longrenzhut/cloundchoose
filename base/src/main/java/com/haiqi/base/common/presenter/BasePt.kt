package com.haiqi.base.common.presenter

import com.haiqi.base.common.activity.DelegateAct
import com.haiqi.base.common.fragment.DelegateFra
import com.haiqi.base.common.listener.IBaseView
import com.haiqi.base.http.BaseSubscriber
import com.haiqi.base.http.Params
import com.haiqi.base.http.ReqCallBack
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.lang.ref.WeakReference

/**
 * Created by zhutao on 2017/8/3.
 */


open class BasePt<T: IBaseView>: IBasePt<T>{


    private var mActivity: DelegateAct? = null


    var mView: WeakReference<T>? = null


    fun <H: BasePt<T>> attach(t: T) : H {
        mView =  WeakReference<T>(t)
        return this as H
    }

    private var mFrament: DelegateFra? = null

    override fun attachActivity(mAct: DelegateAct?) {
        this.mActivity = mAct
    }

    override fun attachFragment(mFra: DelegateFra) {
        this.mFrament = mFra
    }

    override fun detachView() {
        mView?.clear()
        mView = null
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

    fun <T> get(url: String, mParams: Params, req: ReqCallBack<T>){
        mFrament?.let{
            it.get(url,mParams,req)
        } ?:
                mActivity?.get(url,mParams,req)
    }

    fun <T> post(url: String, mParams: Params, req: ReqCallBack<T>){
        mFrament?.let{
            it.post(url,mParams,req)
        } ?:
                mActivity?.post(url,mParams,req)
    }

    fun request(observable: Observable<ResponseBody>,
                observer: BaseSubscriber<ResponseBody>){

        val event = mFrament?.let{
            it.bindUntilEvent<ResponseBody>(FragmentEvent.DESTROY)
        } ?:
            mActivity!!.bindUntilEvent(ActivityEvent.DESTROY)

        mActivity?.request(observable,observer,event)
    }

}