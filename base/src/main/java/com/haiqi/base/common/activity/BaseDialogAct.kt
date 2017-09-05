package com.haiqi.base.common.activity

import android.os.Bundle
import android.view.WindowManager
import com.haiqi.base.R
import com.ssdf.highup.base.interf.IBaseUi

/**
 * Created by zhutao on 2017/8/2.
 * 弹出框activity
 *
 */
abstract class BaseDialogAct: AbsAct(),IBaseUi{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        /**
         * 没有添加stustasBarView
         */
        setStatusBar()
        initView()
    }

    /**
     * 设置窗口变灰
     */
    fun setAttr(apla: Float = 0.5f) {
        val windowLP = window.attributes
        windowLP.dimAmount = apla
        window.attributes = windowLP
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

//    ctx.overridePendingTransition(R.anim.dialog_in_bottom, R.anim.anim_no)
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_no, R.anim.slide_out_bottom)
    }
}