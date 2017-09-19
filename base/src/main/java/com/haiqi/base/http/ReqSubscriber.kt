package com.haiqi.base.http

import com.haiqi.base.utils.showToast
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * Created by Administrator on 2017/7/20.
 */
class ReqSubscriber<T>(val call: ReqCallBack<T>
                       ,mdisposables: ((Disposable) -> Unit)? = null)
    : BaseSubscriber<ResponseBody>(mdisposables) {




    override fun onError(e: Throwable) {
        super.onError(e)
        call.onError(e)
        call.onCompleted()
    }


//    val result: BaseResult<String> = Gson().fromJson(str,
    //                    object : TypeToken<BaseResult<T>>(){}.type)

    override fun onNext(value: ResponseBody) {
        val str = value.string()

        val json = JSONObject(str)
        value.close()

        if(json.has("retcode")) {
            val code: Int = json.optInt("retcode")
            if (code == 0) {
                call.OnNext(json.optString("data"))
            } else {
                showToast(json.optString("msg"))
                call.OnFailed(code)
            }
        }
        else{
            call.OnNext(str)
        }
        call.onCompleted()
    }



}