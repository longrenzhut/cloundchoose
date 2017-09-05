package com.haiqi.wechat

import android.app.Activity
import com.haiqi.base.http.HttpProvider
import com.haiqi.base.http.ReqCallBack
import com.haiqi.base.http.ReqSubscriber
import com.haiqi.base.rx.rxbinding.ObservableSet
import com.haiqi.base.utils.showToast
import com.haiqi.wechat.service.WxService
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.reactivex.Observable
import org.json.JSONObject

/**
 * Created by zhutao on 2017/8/31.
 * 微信登陆
 */
class WxLoginHelper{

    companion object {
        private val instance by lazy{
            WxLoginHelper()
        }

        fun get(): WxLoginHelper = instance
    }

    private var api: IWXAPI? = null


    private fun isWXAppInstalled(act: Activity): Boolean{
        if(null == api)
            api = WXAPIFactory.createWXAPI(act, Consts.APP_ID, true)
        api?.let{
            if(!it.isWXAppInstalled)
                showToast("您还未安装微信客户端")
            return it.isWXAppInstalled
        }
        return true
    }


    /**
     * 调起微信支付 WXEntryActivity 回调
     * 获取code
     */
    fun login(act:Activity) {
        if(!isWXAppInstalled(act)) return
        api?.let {
            it.registerApp(Consts.APP_ID)

            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = "wechat_sdk_demo_test"
            it.sendReq(req)
        }
    }

    /**
     * 根据code 获取token 等数据
     */
    fun requestToken(code: String){
        val url = "https://api.weixin.qq.com/sns/oauth2/"
        HttpProvider.get(url)
                .createService<WxService>()
                .requestToken(Consts.APP_ID,Consts.APP_SECRET,code,"authorization_code")
                .ObservableSet()
                .subscribe(ReqSubscriber(object : ReqCallBack<JSONObject>(){
                    override fun OnSuccess(model: JSONObject?) {
//                        if (json.has("openid")) run {
//                            val openid = json.optString("openid")
//                            val unionid = json.optString("unionid")
//                            if (null != listener)
//                                listener.OnLogin(openid, json.optString("access_token"), unionid)
//                        }

                    }
                }))
    }

    /**
     * 请求用户信息
     */
    fun requestUser(token: String,openid: String){
        val url = "https://api.weixin.qq.com/sns/"
        HttpProvider.get(url)
                .createService<WxService>()
                .requestUser(token,openid)
                .ObservableSet()
                .subscribe(ReqSubscriber(object : ReqCallBack<JSONObject>(){
                    override fun OnSuccess(model: JSONObject?) {
//                        listener.OnUserInfo(json.optString("nickname"), json.optString("headimgurl"))

                    }
                }))
    }

}