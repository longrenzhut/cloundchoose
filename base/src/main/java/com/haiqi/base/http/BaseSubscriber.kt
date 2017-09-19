package com.haiqi.base.http

import android.accounts.NetworkErrorException
import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.haiqi.base.utils.showToast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Created by zhutao on 2017/9/18.
 */

abstract class BaseSubscriber<T>
(val mdisposables: ((Disposable) -> Unit)? = null): Observer<T>{


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
    }


    override fun onComplete() {
    }



    override fun onSubscribe(d: Disposable) {
        if(!d.isDisposed)
            mdisposables?.let{
                it(d)
            }
    }

}