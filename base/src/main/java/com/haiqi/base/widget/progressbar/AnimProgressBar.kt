package com.haiqi.base.widget.progressbar

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.haiqi.base.R
import com.haiqi.base.utils.anim.AnimHelper
import com.haiqi.base.utils.getColor
import com.haiqi.base.widget.shape.ShapeView

/**
 * Created by zhutao on 2017/8/15.
 * 动画
 */

/*<declare-styleable name="animprogressbar">

<attr name="anim_radius" format="dimension"/>
<attr name="anim_bg_color" format="color|reference"/>
<attr name="anim_pre_color" format="color|reference"/>
<attr name="anim_duration" format="dimension"/>

</declare-styleable>*/

class AnimProgressBar(ctx: Context,attrs: AttributeSet?): RelativeLayout(ctx,attrs){

    constructor(ctx: Context): this(ctx,null)

    private var radius = 0f
    private var bgsoild = 0;
    private var presoild = 0;
    private var duration = 2000L;


    private val mlineBg by lazy(LazyThreadSafetyMode.NONE){
        ShapeView(context)
    }

    private val mlinePre by lazy(LazyThreadSafetyMode.NONE){
        ShapeView(context)
    }

    private val helper by lazy(LazyThreadSafetyMode.NONE){
        AnimHelper()
    }


    private var total = 100;
    private var progress = 0;
    private var isAnim = false;

    init{
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.animprogressbar)
        radius = typedArray.getDimension(R.styleable.animprogressbar_anim_radius,0f)
        bgsoild = typedArray.getColor(R.styleable.animprogressbar_anim_bg_color,
                getColor(R.color.transparent))
        presoild = typedArray.getColor(R.styleable.animprogressbar_anim_pre_color,
                getColor(R.color.transparent))
        duration = typedArray.getInteger(R.styleable.animprogressbar_anim_duration,1500).toLong()
        typedArray.recycle()

        val lp = RelativeLayout.LayoutParams(-1,-1)
        mlineBg.layoutParams = lp
        mlineBg.soild = bgsoild
        mlineBg.radius = radius
        addView(mlineBg)

        val lppre = RelativeLayout.LayoutParams(0,-1)
        mlinePre.layoutParams = lppre
        mlinePre.soild = presoild
        mlinePre.radius = radius
        addView(mlinePre)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        setProgress(total,progress,duration)
    }

    fun setProgress(total: Int,progress: Int,duration: Long = 2000){
        this.total = total
        this.progress = progress
        this.duration = duration
        if(width == 0 || isAnim)
            return
        isAnim = true
        val x = progress * width/total

        helper.stretching(mlinePre,x,duration){
            if(radius > 0){
                val limit = width - radius
                if(it < limit){
                   mlinePre.sv_bottom_left = true
                   mlinePre.sv_top_left = true
                }
                else{
                    mlinePre.sv_bottom_left = false
                    mlinePre.sv_top_left = false
                }
            }
        }
    }

}
