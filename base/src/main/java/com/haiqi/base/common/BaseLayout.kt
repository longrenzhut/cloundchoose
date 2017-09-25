package com.haiqi.base.common

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.haiqi.base.Config
import com.haiqi.base.R
import com.haiqi.base.common.application.BaseApp
import com.haiqi.base.thirdsdk.statusbar.StatusBarKitkatImpl
import com.haiqi.base.utils.getDimen
import com.haiqi.base.utils.getDimenPixelOffset
import com.haiqi.base.widget.common.StatusBarView

/**
 * Created by zhutao on 2017/9/22.
 *
 */
class BaseLayout(ctx: Context, attrs: AttributeSet?): RelativeLayout(ctx,attrs){


    private var statusAdd = true
    private var headerAdd = true

    private val header by lazy(LazyThreadSafetyMode.NONE) {
        UIHeaderLayout(context)
    }

    private val mStatusBarView by lazy(LazyThreadSafetyMode.NONE){
        StatusBarView(context)
    }

    init {
        id = R.id.root
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.baselayout)

        headerAdd = typedArray.getBoolean(R.styleable.baselayout_bl_header,false)
        statusAdd = typedArray.getBoolean(R.styleable.baselayout_bl_status,false)

        typedArray.recycle()

        if(statusAdd && Config.STATUSBAR){
            addView(mStatusBarView)
        }

        if(headerAdd){
            val lp = RelativeLayout.LayoutParams(-1, getDimenPixelOffset(R.dimen.common_header_height))
            if(statusAdd && Config.STATUSBAR){
                lp.topMargin = StatusBarKitkatImpl.getStatusBarHeight(BaseApp.getApp())
            }
            header.layoutParams = lp
            addView(header)
        }
    }


    fun setStatusBarColor(color: Int) {
        if(statusAdd && Config.STATUSBAR){
            mStatusBarView.setBackgroundColor(color)
        }
    }

    fun getCommonHeader(): UIHeaderLayout?{
        if(headerAdd)
            return header
        return null
    }
}