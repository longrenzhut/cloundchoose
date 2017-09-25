package com.haiqi.base.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.haiqi.base.R
import com.haiqi.base.utils.*
import org.jetbrains.anko.find

/**
 * Created by zhutao on 2017/8/2.
 * 统一的头部
 */
class UIHeaderLayout(ctx: Context,attrs: AttributeSet?): RelativeLayout(ctx,attrs){

    constructor(ctx: Context): this(ctx,null){}

    init {
        id = R.id.uiheader
        LayoutInflater.from(ctx)
                .inflate(R.layout.common_head, this, true)
    }

    val mTvTitle by lazy(LazyThreadSafetyMode.NONE){
        find<TextView>(R.id.tv_title);
    }

    val mIvLeft by lazy(LazyThreadSafetyMode.NONE){
        find<ImageView>(R.id.iv_left);
    }


    val mIvRight by lazy(LazyThreadSafetyMode.NONE){
        find<ImageView>(R.id.iv_right);
    }


    val mTvRight by lazy(LazyThreadSafetyMode.NONE){
        find<TextView>(R.id.tv_right);
    }



    fun initHeader(title: String,isLeft: Boolean = true,OnClickHeader: ((Int)->(Unit))? = null){
        mTvTitle.text = title
        if(isLeft){
            mIvLeft.setVisible(1)
            mIvLeft.setOnClickListener{
                OnClickHeader?.let {
                    it(id)
                }
            }
        }
    }

    fun setIvRight(drawble: Int, onIvRight: (Int) -> Unit, w: Int, h: Int) {
        mIvRight.setVisible(1)
        if(drawble != 0)
            mIvRight.setBackgroundResource(drawble)
        mIvRight.setOnClickListener{
            onIvRight(id)
        }
        if(w != 0 || h != 0) {
            val lp = mIvRight.layoutParams as RelativeLayout.LayoutParams
            lp.height = h
            lp.width = w
            mIvRight.layoutParams = lp
        }
    }

    fun setTvRight(text: String, onTvRight: (Int) -> Unit, textcolor: Int, size: Int) {
       mTvRight.setVisible(1)
        mTvRight.text = text
        mTvRight.setOnClickListener{
            onTvRight(id)
        }
        mTvRight.setTvColor(textcolor)
        mTvRight.setTvSize(size)
    }


    fun setTitle(title: String){
        mTvTitle.text = title
    }
}