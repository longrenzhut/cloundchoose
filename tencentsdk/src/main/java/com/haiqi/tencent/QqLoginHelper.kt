package com.haiqi.tencent

import android.app.Activity
import android.content.Intent
import com.tencent.connect.UserInfo
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject

/**
 * Created by zhutao on 2017/8/31.
 * qq 登陆 获取信息
 */

class QqLoginHelper{

    companion object {
        private val instance by lazy{
            QqLoginHelper()
        }

        fun get(): QqLoginHelper = instance
    }

    private var mTencent: Tencent? = null
    private var mCallback: ShareCallback? = null

    private fun with(act: Activity) {
        mTencent?.let{
            mTencent = Tencent.createInstance(Consts.APP_ID, act)
        }
    }

    /**
     *  必须activity或者fragment里面的
     *  onActivityResult回调方面里面调用
     *  不然没有回调值
     */
    fun onActivityResultData(requestCode: Int, resultCode: Int
                             , data: Intent){
        if (requestCode == 11101) {
            mCallback?.let{
                Tencent.onActivityResultData(requestCode, resultCode,
                        data, it)
                Tencent.handleResultData(data, it)
            }
        }

    }

    /**
     * 拉起qq 获取openid,token
     */
    fun login(act: Activity,
              OnSuccess:(String,String)-> Unit,
              OnFailed:((UiError?)-> Unit)? = null){
        with(act)
        val call = ShareCallback({
            it?.let{
                val json = JSONObject(it.toString())

                val token = json.optString("access_token")
                val expires = json.optString("expires_in")
                //OPENID,作为唯一身份标识
                val openId = json.optString("openid")
                if(token.isNullOrEmpty() && expires.isNullOrEmpty())
                    mTencent?.setAccessToken(token, expires)
                if(openId.isNullOrEmpty())
                    mTencent?.openId = openId
                OnSuccess(token,openId)
            }
        },OnFailed,1)
        mTencent?.login(act, "all", call)
    }

    /**
     * 请求qq用户信息 昵称 以及 头像
     */
    fun requestUserInfo(act: Activity,
                        OnSuccess:(String,String)-> Unit,
                        OnFailed:((UiError?)-> Unit)? = null){
        mTencent?.let{
            val user = UserInfo(act, it.getQQToken())
            val call = ShareCallback({
                it?.let {
                    val json = JSONObject(it.toString())
                    val nickName = json.optString("nickname")
                    val headurl = json.optString("figureurl_qq_2")
                    OnSuccess(nickName,headurl)
                }
            },OnFailed,1);
            user.getUserInfo(call)
        }
    }

    /**
     * 退出qq登陆
     */
    fun logout(act: Activity) {
        mTencent?.let {
            it.logout(act)
        }
    }

}
