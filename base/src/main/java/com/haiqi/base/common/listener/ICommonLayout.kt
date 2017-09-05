package com.haiqi.base.common.listener

import com.haiqi.base.R
import com.haiqi.base.common.UILoadLayout
import com.haiqi.base.utils.dip2px

/**
 * Created by zhutao on 2017/8/3.
 * 线性布局 和 相对布局添加头部 和 cover
 */
interface ICommonLayout {

    fun setCommonHeader(title: String,isLeft: Boolean = true,Onleft: (Int)->(Unit))
    fun setTitle(title: String)
    fun setIvRight(drawble: Int = 0,onIvRight: (Int)->(Unit),w: Int = 0, h: Int = w)
    fun setTvRight(text: String,onTvRight: (Int)->(Unit),textcolor: Int = 0, size: Int = 15)

    fun setUILoadLayout(reLoad: (Int)->(Unit), top: Int = 45,bottom: Int = 0)

    fun getUILoadLayout(): UILoadLayout?


    fun setStatusBarView(color: Int = R.color.white)

    //动态设置头部
    fun setCommonHeader(layoutid: Int,headerHeigh: Int = dip2px(45))

    //获取头部的高度
    fun getHeaderHeight(): Int =  dip2px(45)
}