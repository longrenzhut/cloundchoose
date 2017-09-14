package com.haiqi.base.http

import android.accounts.NetworkErrorException
import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.haiqi.base.common.application.BaseApp
import com.haiqi.base.utils.NetworkUtils
import com.haiqi.base.utils.showToast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Created by Administrator on 2017/7/20.
 */
class ReqSubscriber<T>(val call: ReqCallBack<T>
                       ,val mdisposables: ((Disposable) -> Unit)? = null)
    : Observer<ResponseBody>{

    override fun onComplete() {
    }

    /**
     * start
     */
    override fun onSubscribe(d: Disposable) {
        mdisposables?.let{
            it(d)
        }
    }


    fun handleException(e: Throwable): String =
            if (e is HttpException) {
                "网络请求失败,请检查网络设置"
            } else if (e is JsonParseException
                    || e is JSONException
                    || e is ParseException) {
                "解析错误"
            } else if (e is ConnectException) {
                "连接失败"
            } else if (e is javax.net.ssl.SSLHandshakeException) {
                "证书验证失败"
            } else if (e is NullPointerException) {
                "空指针异常"
            }
            else if (!NetworkUtils.isNetworkReachable(BaseApp.getApp())) {
                "网络请求失败,请检查网络设置"
            }
            else {
                "网络连接异常"
            }


    fun msg(t: Throwable): String =
            if (t is NetworkErrorException ||
                    t is UnknownHostException ||
                    t is ConnectException) {
                "网络异常"
            }
            else if (t is SocketTimeoutException ||
                    t is InterruptedIOException ||
                    t is TimeoutException) {
                "网络请求超时"
            } else if (t is JsonSyntaxException) {
                "请求不合法"
            } else if (t is JsonParseException
                    || t is JSONException
                    || t is ParseException) {   //  解析错误
                "解析错误"
            }
            else if (t is javax.net.ssl.SSLHandshakeException) {
                "证书验证失败"
            } else if (t is NullPointerException) {
                "空指针异常"
            }
            else {
                ""
            }

    override fun onError(e: Throwable) {
        showToast(msg(e))
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