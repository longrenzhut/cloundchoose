package com.haiqi.base.widget.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.haiqi.base.R
import com.haiqi.base.utils.Mylogger
import com.haiqi.base.utils.anim.AnimHelper
import com.haiqi.base.utils.dip2px
import javax.xml.datatype.Duration

/**
 * Created by zhutao on 2017/8/15.
 * 动态的饼图
 */

/*
<attr name="pie_outner_radius" format="dimension"/>
<attr name="pie_inner_color" format="color|reference"/>
<attr name="pie_inner_radius" format="dimension"/>
<attr name="pie_start_angle" format="float"/>*/
class AnimPieCircleView(ctx: Context,attrs: AttributeSet?): View(ctx,attrs){

    var innerradius = 0f
    var innercolor = 0xffffffff.toInt()
    var startangle = 0f

    val mRectf by lazy(LazyThreadSafetyMode.NONE){
        val z = (if(width > height) height else width).toFloat()
        RectF(0f,0f,z,z)
    }
    val mPaint by lazy(LazyThreadSafetyMode.NONE){
        Paint()
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.animpiecircleview)
        innerradius = typedArray.getDimension(R.styleable.animpiecircleview_pie_inner_radius,dip2px(10f))
        innercolor = typedArray.getColor(R.styleable.animpiecircleview_pie_inner_color,innercolor)
        startangle = typedArray.getFloat(R.styleable.animpiecircleview_pie_start_angle,
                120f)
        typedArray.recycle()

        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
        mPaint.isDither = true
    }

    data class Pie(val color: Int,val num: Int,val name: String)

    var pieList: MutableList<Pie>? = null

    var progress = 0f



    val anglesList by lazy{
        mutableListOf<Float>()
    }

    var colors:List<Int>? = null

    val speed = 5f

    fun setPies(list: MutableList<Pie>?){
        this.pieList = list

        list?.let{
            var total = 0f
            for(pie in list){
                total += pie.num
            }
//           val total = it.sumBy{
//                it.num
//            }.toFloat()

            //计算比例  根据比例 算出 所占度数
            val angles = it.map{
                it.num/total * 360
            }

            colors = it.map{
                it.color
            }

            anglesList.clear()
            for(i in angles.indices){
                var newValue = 0f
                for(j in 0..i){
                    newValue += angles[j]
                }
                anglesList.add(newValue)
            }

        }

        postInvalidate()

//        AnimHelper().startAnim(0f,360f,duration,{
//            progress += 1f
//            postInvalidate()
//        },100)

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(anglesList.isEmpty())
            return

        canvas?.let{
            //根据progress所在的区间判断用几次cavas
//            mPaint.color = colors!![0]
//            it.drawArc(mRectf, 120f, 10f, true, mPaint)

            for(i in anglesList.indices) {
                colors?.let {
                    mPaint.color = it[i]
                }
                if (i == 0) {
                    if (progress <=  anglesList[i]) {
                        it.drawArc(mRectf, startangle, progress, true, mPaint)
                        break
                    }
                    else{
                        it.drawArc(mRectf, startangle, anglesList[i], true, mPaint)
                    }
                }
                else {
                    if (progress in anglesList[i - 1]..anglesList[i]) {
                        it.drawArc(mRectf, startangle + anglesList[i - 1], progress - anglesList[i - 1], true, mPaint)
                        break
                    } else {
                        it.drawArc(mRectf, startangle + anglesList[i - 1], anglesList[i] - anglesList[i - 1], true, mPaint)
                    }
                }
            }
            mPaint.color = innercolor
            it.drawCircle((width / 2).toFloat(), (height / 2).toFloat(),
                    innerradius, mPaint)

            if(progress < 360f) {
                progress += speed
                postInvalidate()
            }
        }
    }


}