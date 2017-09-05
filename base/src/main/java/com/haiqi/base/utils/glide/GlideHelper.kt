package com.haiqi.base.utils.glide

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.haiqi.base.R
import com.haiqi.base.utils.dip2px
import com.haiqi.base.utils.getColor

/**
 * Created by zhutao on 2017/6/2.
 * 图片下载封装
 */
class GlideHelper {

    companion object{

        private val instance by lazy(LazyThreadSafetyMode.NONE){
            GlideHelper()
        }

        fun init(){
            instance()
        }

        fun instance():GlideHelper
                = instance
    }

    fun getUrl2Oss(url: String,w: Int,h: Int,q: Int = 100): String =
            if(/*"file" in url*/url.contains("file")){
                url
            }
            else{
                "$url?x-oss-process=image/resize,w_$w,h_$h/quality,q_$q"
            }


    /**
     *    enum class CornerType {
    ALL,
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
    TOP, BOTTOM, LEFT, RIGHT,
    OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
    DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
    }

     */
    fun loadCommon(iv: ImageView, url: String, radius: Int
                   ,type: CommonTransformation.CornerType
                   ,margin: Int = 0) {
        if (url.isNullOrEmpty()) {
            return
        }
        val v = dip2px(radius) * 2
        val changeString = getUrl2Oss(url, v, v)
        val trans = CommonTransformation(iv.context, radius, margin,type)
        loadbase(iv,changeString, trans)
    }


    fun load(iv: ImageView, url: String, w: Int,h: Int = w,req: RequestListener<Drawable>? = null){
        if (url.isNullOrEmpty() || !url.contains("http")) {
            return
        }

        val changeString = getUrl2Oss(url, dip2px(w), dip2px(h))
        loadbase(iv,changeString,null,req)
    }


    /**
     * 加载本地商品
     */
    fun loadLocal(iv: ImageView,url: String) {
        if (url.isNullOrEmpty() || !url.contains("http")) {
            return
        }
        loadbase(iv,url)
    }

    fun loadbase(iv: ImageView,url: String,trans: Transformation<Bitmap>? = null,
                 req: RequestListener<Drawable>? = null,
                 placeholder: Int = R.color.transparent,error: Int = placeholder) {
        val opt = RequestOptions()
        opt.centerCrop().dontAnimate()
                .placeholder(placeholder)
                .error(error)
        trans?.let{
            opt.transform(it)
        }
        Glide.with(iv.context).load(url)
                .listener(req)
                .apply(opt).into(iv)
    }

    fun loadasBitmap(act: Activity,url: String,w: Int, h: Int = w
                     ,req: RequestListener<Bitmap>? = null){
        val changeString = getUrl2Oss(url, dip2px(w), dip2px(h))
        Glide.with(act)
                .asBitmap()
                .load(changeString)
                .listener(req)
    }

}