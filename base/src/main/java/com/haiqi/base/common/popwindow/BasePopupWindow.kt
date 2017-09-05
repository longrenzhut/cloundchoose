package com.haiqi.base.common.popwindow

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow

/**
 * Created by zhutao on 2017/8/18.
 */
open class BasePopupWindow(val mActivity: Activity) : PopupWindow(){


    init {

        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
//        setAnimationStyle(R.style.popwindow_anim_style);
        isOutsideTouchable = true
        isFocusable = true
        update()
        val dw = ColorDrawable(0)
        setBackgroundDrawable(dw)

        setOnDismissListener { setAlpha(1f) }
    }


    private fun setAlpha(alpha: Float) {
        val lp = mActivity.window.attributes
        lp.alpha = alpha
        mActivity.window.attributes = lp
    }

    override fun showAsDropDown(anchor: View) {
        postDelayed()
        super.showAsDropDown(anchor)
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        postDelayed()
        super.showAtLocation(parent, gravity, x, y)
    }

    private fun postDelayed() {
        Handler().postDelayed({ setAlpha(0.7f) }, 100)
    }
}