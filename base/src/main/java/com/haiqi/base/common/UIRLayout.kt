package com.haiqi.base.common

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.haiqi.base.R
import com.haiqi.base.common.listener.ICommonLayout
import com.haiqi.base.thirdsdk.statusbar.StatusBarKitkatImpl
import com.haiqi.base.utils.dip2px
import com.haiqi.base.widget.common.StatusBarView

/**
 * Created by zhutao on 2017/8/3.
 * 相对布局
 */
class UIRLayout(ctx: Context,attrs: AttributeSet?): RelativeLayout(ctx,attrs), ICommonLayout {

    init {
        id = R.id.root
//        setBackgroundColor(getAbsColor(R.color.white))

    }
    val header by lazy(LazyThreadSafetyMode.NONE) {
        UIHeaderLayout(context)
    }

    private var hasStatusBarView = false;

    override fun setCommonHeader(title: String, isLeft: Boolean, Onleft: (Int) -> Unit) {

        val lp = RelativeLayout.LayoutParams(-1,getHeaderHeight())
        if(hasStatusBarView){
            lp.topMargin = StatusBarKitkatImpl.getStatusBarHeight(context)
        }
        header.layoutParams = lp
        header.initHeader(title,isLeft,Onleft)
        addView(header)
    }


    override fun setCommonHeader(layoutid: Int,headerHeigh: Int) {
        val mHeaderView = LayoutInflater.from(context)
                .inflate(layoutid,null)
        mHeaderView.id = R.id.uiheader
        val lp = RelativeLayout.LayoutParams(-1,headerHeigh)
        if(hasStatusBarView){
            lp.topMargin = StatusBarKitkatImpl.getStatusBarHeight(context)
        }
        addView(mHeaderView,0)
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

    override fun setUILoadLayout(reLoad: (Int) -> Unit, top: Int,  bottom: Int) {
        mUILoadLayout = UILoadLayout(context)
        val lp = RelativeLayout.LayoutParams(-1, -1)

        lp.topMargin = if(hasStatusBarView){
            dip2px(top) + StatusBarKitkatImpl.getStatusBarHeight(context)
        }
        else
            dip2px(top)
        lp.bottomMargin = dip2px(bottom)
        mUILoadLayout?.layoutParams = lp
        addView(mUILoadLayout)
    }


    override fun setStatusBarView(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val bar = StatusBarView(context)
            if(color != R.color.white)
                bar.setBackgroundColor(color)
            addView(bar,0)
            hasStatusBarView = true
        }
    }


    private var mUILoadLayout: UILoadLayout? = null

    override fun getUILoadLayout(): UILoadLayout? = mUILoadLayout

}