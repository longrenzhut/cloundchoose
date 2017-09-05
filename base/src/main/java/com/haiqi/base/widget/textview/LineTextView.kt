package com.haiqi.base.widget.textview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.haiqi.base.R
import com.haiqi.base.utils.dip2px

/**
 * Created by zhutao on 2017/8/4.
 *
 * 价格加划线
 */
class LineTextView(ctx: Context,attrs: AttributeSet?): TextView(ctx,attrs){

    /*<attr name="ltv_line_color" format="reference|color"/>
    <attr name="ltv_line_height" format="dimension"/>
    <attr name="ltv_line" format="boolean"/>*/

    var lineColor = 0
    var lineH = 0f
    var isLine = true

    final val defalut_color: Int = 0x999999
    final val defalut_height: Float = 1f;

    val mPaint by lazy{
        Paint()
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.linetextview)
        lineColor = typedArray.getColor(R.styleable.linetextview_ltv_line_color,
                defalut_color)
        lineH = typedArray.getDimension(R.styleable.linetextview_ltv_line_height,
                dip2px(defalut_height))
        isLine = typedArray.getBoolean(R.styleable.linetextview_ltv_line,true)
        typedArray.recycle()

        mPaint.color = lineColor
        mPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(isLine){
            canvas?.let {
                it.save()
                it.translate(0f, ((height - lineH)/ 2).toFloat())
                it.drawLine(0f, 0f, width.toFloat(), 0f, mPaint)
                it.restore()
            }
        }
    }

    /**
     * 是否取消 划线
     */
    fun setText(text: String, isline: Boolean) {
        super.setText(text)
        this.isLine = isline
        invalidate()
    }
}