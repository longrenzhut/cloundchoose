package com.haiqi.base.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.WindowManager
import com.haiqi.base.R
import com.haiqi.base.thirdsdk.statusbar.StatusBarCompat
import com.haiqi.base.utils.getColor

/**
 * Created by zhutao on 2017/8/18.
 */
open class BaseDialog(ctx: Context,theme: Int): Dialog(ctx,theme){

    constructor(ctx: Context): this(ctx,R.style.CommonDialogTheme)

    init {
        setStatusBar()
    }

    fun setStatusBar(color: Int = R.color.transparent) {
        StatusBarCompat.setStatusBarColor(window,
                getColor(color), true,true)
    }


    /**
     * 设置透明度
     */
    fun setAttr(alph: Float = 0.5f) {
        val windowLP = window.attributes
        windowLP.dimAmount = alph
        window.attributes = windowLP
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }


}