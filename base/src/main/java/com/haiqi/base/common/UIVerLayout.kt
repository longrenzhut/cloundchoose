package com.haiqi.base.common

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.haiqi.base.R
import com.haiqi.base.common.listener.ICommonLayout
import com.haiqi.base.widget.common.StatusBarView

/**
 * Created by zhutao on 2017/8/3.
 * 线性布局
 */
class UIVerLayout(ctx: Context,attrs: AttributeSet?): LinearLayout(ctx,attrs), ICommonLayout {

    init {
        id = R.id.root
//        setBackgroundColor(getAbsColor(R.color.white))
        orientation = VERTICAL

    }
    val header by lazy(LazyThreadSafetyMode.NONE) {
        UIHeaderLayout(context)
    }

    private var mUILoadLayout: UILoadLayout? = null

    private var childcount = 0 //加载

    override fun setCommonHeader(title: String, isLeft: Boolean, Onleft: (Int) -> Unit) {
        val lp = LinearLayout.LayoutParams(-1,getHeaderHeight())
        header.layoutParams = lp
        header.initHeader(title,isLeft,Onleft)
        addView(header,childcount)
        childcount ++
    }

    override fun setCommonHeader(layoutid: Int,headerHeigh: Int) {
        val mHeaderView = LayoutInflater.from(context)
                .inflate(layoutid,null)
        mHeaderView.id = R.id.uiheader
        val lp = LinearLayout.LayoutParams(-1,headerHeigh)
        header.layoutParams = lp
        addView(mHeaderView,childcount)
        childcount ++
    }


    override fun setTitle(title: String) {
        header.setTitle(title)
    }

    override fun setIvRight(drawble: Int, onIvRight: (Int) -> Unit, w: Int, h: Int) {
        header.setIvRight(drawble,onIvRight,w,h)
    }

    override fun setTvRight(text: String, onTvRight: (Int) -> Unit, textcolor: Int, size: Int) {
        header.setTvRight(text,onTvRight,textcolor,size)
    }

    override fun setStatusBarView(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val bar = StatusBarView(context)
            if(color != R.color.white)
                bar.setBackgroundColor(color)
            addView(bar, childcount)
            childcount ++
        }
    }

    override fun setUILoadLayout(reLoad: (Int) -> Unit,top: Int,  bottom: Int) {

        mUILoadLayout = UILoadLayout(context)
        val lp = LinearLayout.LayoutParams(-1, -1)
        mUILoadLayout?.layoutParams = lp
        addView(mUILoadLayout, childcount)
    }

    override fun getUILoadLayout(): UILoadLayout? = mUILoadLayout

}