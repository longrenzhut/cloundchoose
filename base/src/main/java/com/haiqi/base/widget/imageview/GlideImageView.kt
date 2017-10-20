package com.haiqi.base.widget.imageview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.haiqi.base.R
import com.haiqi.base.utils.dip2px
import com.haiqi.base.utils.glide.CommonTransformation
import com.haiqi.base.utils.glide.GlideHelper
import com.haiqi.base.widget.shape.ShapeView

/**
 * Created by zhutao on 2017/8/10.
 * glide 加载图片 以及加载动画
 */
class GlideImageView(ctx: Context,attr: AttributeSet?): RelativeLayout(ctx,attr){

    constructor(ctx: Context): this(ctx,null)

    private var stokecolor = 0
    private var stokewidth = 0f
    private var corner = 0f



    private var sv_top_left = false;
    private var sv_top_right = false;
    private var sv_bottom_left = false;
    private var sv_bottom_right = false;


    private val mpbLoad by lazy(LazyThreadSafetyMode.NONE){
        ProgressBar(ctx)
    }

    private val mIvIcon by lazy(LazyThreadSafetyMode.NONE){
        ImageView(ctx)
    }

 /*   <attr name="sv_radius" format="dimension"/>
    <attr name="sv_stoke_color" format="reference|color"/>
    <attr name="sv_stoke_width" format="dimension"/>
    <attr name="sv_soild_color" format="reference|color"/>
    <attr name="sv_top_left" format="boolean"/>
    <attr name="sv_top_right" format="boolean"/>
    <attr name="sv_bottom_left" format="boolean"/>
    <attr name="sv_bottom_right" format="boolean"/>*/

    init{
        val typedArray = context.obtainStyledAttributes(attr,
                R.styleable.shapeview)

        corner = typedArray.getDimension(R.styleable.shapeview_sv_radius,0f)
        stokecolor = typedArray.getColor(R.styleable.shapeview_sv_stoke_color,R.color.transparent)
        stokewidth = typedArray.getDimension(R.styleable.shapeview_sv_stoke_width,0f)
        sv_top_left = typedArray.getBoolean(R.styleable.shapeview_sv_top_left,false)
        sv_top_right = typedArray.getBoolean(R.styleable.shapeview_sv_top_right,false)
        sv_bottom_left = typedArray.getBoolean(R.styleable.shapeview_sv_bottom_left,false)
        sv_bottom_right = typedArray.getBoolean(R.styleable.shapeview_sv_bottom_right,false)


        typedArray.recycle()

        if(stokewidth != 0f){
            val mShapeView = ShapeView(context,attr)
            addView(mShapeView)
        }

//        setBackgroundColor(getAbsColor(R.color.cl_f0f0f0))
        val iconlp = RelativeLayout.LayoutParams(-1,-1)
        mIvIcon.scaleType = ImageView.ScaleType.FIT_XY
        mIvIcon.layoutParams = iconlp
        addView(mIvIcon)

        val pblp = RelativeLayout.LayoutParams(dip2px(15),dip2px(15))
        pblp.addRule(RelativeLayout.CENTER_IN_PARENT)
        mpbLoad.layoutParams = pblp
        addView(mpbLoad)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setUrl(imgurl,w,h)
    }

    private var imgurl = ""
    private var loadingSuc = false

    fun setUrl(url: String,w: Int = 0, h: Int = 0){
        this.imgurl = url
        if(url.isNullOrEmpty())return
        if(w == 0)return
        if(loadingSuc)
            return

        val oss =  GlideHelper.instance().getUrl2Oss(url,w,h)
        var trans: CommonTransformation? = null
        if(corner != 0f)
            trans = CommonTransformation(context, corner.toInt() - stokewidth.toInt() , stokewidth.toInt())

        GlideHelper.instance().loadbase(mIvIcon,oss,trans,object : RequestListener<Drawable>{
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                mpbLoad.visibility = GONE
                loadingSuc = true
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                mpbLoad.visibility = GONE
                loadingSuc = true
                return false
            }

        })
    }

}