package com.haiqi.tencent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.haiqi.base.model.ShareModel
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzoneShare
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError

/**
 * Created by zhutao on 2017/8/31.
 * qq 分享帮助类
 */
class QqShareHelper {

    companion object {
        private val instance by lazy{
            QqShareHelper()
        }

        fun get(): QqShareHelper = instance
    }


    private var mTencent: Tencent? = null
    private var mCallback: ShareCallback? = null

    /**
     *  必须activity或者fragment里面的
     *  onActivityResult回调方面里面调用
     *  不然分享后没有回调
     */
    fun onActivityResultData(requestCode: Int, resultCode: Int
                             , data: Intent){
        mCallback?.let {
            Tencent.onActivityResultData(requestCode, resultCode,
                    data,it)
        }
    }


    private fun with(act: Activity): Bundle{
        mTencent?.let{
            mTencent = Tencent.createInstance(Consts.APP_ID, act)
        }
        return Bundle()
    }

    /**
     * 分享到qq
     */
    fun shareToQQ(act: Activity,model: ShareModel,
                onComplete: ((Any?)-> Unit)? = null,
                onError: ((UiError?)-> Unit)? = null){
        val params =  with(act)
        with(model) {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title)
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content)
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, linkUrl)
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgurl)
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "云选商城")
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 111)
        }
        mTencent?.shareToQQ(act, params,ShareCallback(onComplete,onError))
    }

    /**
     * 分享到qq空间
     */
    fun shareToQzone(act: Activity,model: ShareModel,
                   onComplete: ((Any?)-> Unit)? = null,
                   onError: ((UiError?)-> Unit)? = null){
        val params = with(act)
        with(model){
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title)//必填
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content)
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, linkUrl)//必填
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,
                    arrayListOf<String>(imgurl))
        }
        mCallback =  ShareCallback(onComplete,onError)
        mTencent?.shareToQzone(act, params, mCallback)

    }


    /**
     * 单独分享图片到qq
     */
    fun sharePicToqq(act: Activity,imgurl: String,
                        onComplete: ((Any?)-> Unit)? = null,
                        onError: ((UiError?)-> Unit)? = null){

        val params = with(act)

        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgurl)
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "LPS CRM")
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
                QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE)
        mCallback =  ShareCallback(onComplete,onError)
        mTencent?.shareToQQ(act, params, mCallback)
    }

    /**
     * 单独分享图片到qq 空间
     */
    fun sharePicToQzone(act: Activity,imgurl: String,
                     onComplete: ((Any?)-> Unit)? = null,
                     onError: ((UiError?)-> Unit)? = null){
        val params = with(act)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgurl)
        //params.putString(QQShare.SHARE_TO_QQ_APP_NAME,name);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN)
        mCallback =  ShareCallback(onComplete,onError)
        mTencent?.shareToQQ(act, params, mCallback)
    }

}