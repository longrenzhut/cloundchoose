package com.haiqi.base.widget.common

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.haiqi.base.Config
import com.haiqi.base.R
import com.haiqi.base.common.application.BaseApp
import com.haiqi.base.thirdsdk.statusbar.StatusBarKitkatImpl
import com.haiqi.base.utils.getActColor

/**
 * Created by zhutao on 2017/8/17.
 *
 * 头部的状态栏
 */
class StatusBarView(ctx: Context,attrs: AttributeSet?): View(ctx,attrs){

    constructor(ctx: Context): this(ctx,null)



    init {
        id = R.id.statusbar
        setBackgroundColor(R.color.colorPrimaryDark)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val h =
                if (Config.STATUSBAR) {
                    StatusBarKitkatImpl.getStatusBarHeight(BaseApp.getApp())
                } else 0

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        setMeasuredDimension(widthSize, h)
    }

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(getActColor(color))
    }
}