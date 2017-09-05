package com.haiqi.wechat

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.haiqi.base.common.application.BaseApp
import com.haiqi.base.model.ShareModel
import com.haiqi.base.utils.getResources
import com.haiqi.base.utils.glide.GlideHelper
import com.haiqi.base.utils.showToast
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import org.jetbrains.anko.doAsync

/**
 * Created by zhutao on 2017/8/31.
 * 微信分享帮组类
 */
class WxShareHelper {

    companion object {
        private val instance by lazy{
            WxShareHelper()
        }

        fun get(): WxShareHelper = instance
    }

    /**
     * 获取图片的大小
     */
    fun getBitmapsize(bitmap: Bitmap): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            bitmap.byteCount.toLong()
        }
        else
            (bitmap.rowBytes * bitmap.height).toLong()

    }

    private fun obtainBitmap(act: Activity,url: String?,w: Int = 45, h: Int = w,
         getBitmap: (Bitmap)-> Unit){
       val newUrl =  if(url.isNullOrEmpty()){
           "appicon"
        }
        else{
           url!!
       }

        GlideHelper.instance().loadasBitmap(act,newUrl,w,h,
                object : RequestListener<Bitmap>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {

                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        resource?.let {
                            if (getBitmapsize(it) / 1024 <= 25) {
                                getBitmap(it)
                            } else {
                               doAsync {
                                  val result =  CompUtils.comp(resource,w,h)
                                   Thread{
                                       getBitmap(result)
                                   }
                                }
                            }
                        }
                        return false
                    }
                })
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
     *  scene 0是朋友圈，1是好友，
     */
    fun shareWx(act: Activity,model: ShareModel,scene: Int = 0){
        if(!isWXAppInstalled(act)) return
        obtainBitmap(act,model.imgurl,45,45){

            val webpage = WXWebpageObject()
            val msg = WXMediaMessage(webpage)
            with(model){
                webpage.webpageUrl = linkUrl
                msg.title = title
                msg.description = content
            }
            msg.setThumbImage(it)

            val req = SendMessageToWX.Req()
            req.transaction = System.currentTimeMillis().toString()
            req.message = msg
            req.scene = scene
            api?.sendReq(req)

        }
    }


}