package com.haiqi.wechat

import android.app.Activity
import com.haiqi.base.utils.showToast
import com.haiqi.wechat.model.WxPayModel
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * Created by zhtuao on 2017/8/31.
 * 微信支付
 */
class WxPayHelper(val act: Activity){


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
     * 发起请求支付
     */
    fun requestPay(model: WxPayModel){
        if(!isWXAppInstalled(act)) return
        model.appid = Consts.APP_ID
        val req = PayReq()
        with(model){
            req.appId = appid
            req.partnerId = partnerid
            req.prepayId = prepayid
            req.nonceStr = noncestr
            req.timeStamp = time
            req.packageValue = packageValue
            req.sign = sign
            if (req.checkArgs()) {
                api?.registerApp(appid)
                api?.sendReq(req)
            }
        }
    }
}