package com.haiqi.base.widget.common

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.haiqi.base.R
import com.haiqi.base.thirdsdk.statusbar.StatusBarKitkatImpl

/**
 * Created by zhutao on 2017/8/17.
 *
 * 头部的状态栏
 */
class StatusBarView(ctx: Context,attrs: AttributeSet?): View(ctx,attrs){

    constructor(ctx: Context): this(ctx,null)


    private final val backgroundcolors = 0xffffffff.toInt()

    init {
        id = R.id.statusbar
        setBackgroundColor(backgroundcolors)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val h =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                    StatusBarKitkatImpl.getStatusBarHeight(context)
                } else 0

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        setMeasuredDimension(widthSize, h)
    }
}