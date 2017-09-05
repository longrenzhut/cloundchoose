package com.haiqi.base.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.haiqi.base.R
import com.haiqi.base.utils.getColor
import com.haiqi.base.utils.setVisible
import org.jetbrains.anko.find
import java.util.jar.Attributes

/**
 * Created by zhutao on 2017/7/7.
 * 进入界面加载动画
 */
class UILoadLayout(ctx: Context,attrs: AttributeSet?): LinearLayout(ctx,attrs){

    constructor(ctx: Context): this(ctx,null){}

    init {
        id = R.id.uilayout
        setBackgroundColor(getColor(R.color.white))
        this.orientation = VERTICAL
        gravity = Gravity.CENTER
        isClickable = true
        LayoutInflater.from(ctx)
                .inflate(R.layout.common_ui_load, this, true)
    }

    val mPbloading by lazy(LazyThreadSafetyMode.NONE){
        find<ProgressBar>(R.id.pb_loading)
    }

    val mllyFailed by lazy(LazyThreadSafetyMode.NONE) {
        find<LinearLayout>(R.id.lly_failed)
    }

    val mTvLoadAgain by lazy(LazyThreadSafetyMode.NONE) {
        find<TextView>(R.id.tv_load_again)
    }



    fun setReload(loadData: () -> (Unit)){
        mTvLoadAgain.setOnClickListener{
            mllyFailed.setVisible(-1)
            mPbloading.setVisible(1)
            loadData()
        }
    }

    fun loadFailed(){
        mPbloading.setVisible(-1)
        mllyFailed.setVisible(1)
    }

    fun loadok(){
        this.setVisible(-1)
    }

    fun resetUi(){
        this.setVisible(1)
        mPbloading.setVisible(1)
        mllyFailed.setVisible(-1)
    }
}