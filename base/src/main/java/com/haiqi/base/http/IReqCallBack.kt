package com.haiqi.base.http

/**
 * Created by zhutao on 2017/5/27.
 */
interface IReqCallBack<T>{
     fun OnSuccess(model: T?)
    fun OnFailed(retcode: Int)
    fun onError(e: Throwable?)
    fun onCompleted()
}
