package com.haiqi.yx.entry

import android.content.Context
import android.util.AttributeSet
import android.view.View
import io.reactivex.Observable

/**
 * Created by Administrator on 2017/9/13.
 */
class Test : View{
    constructor(ctx: Context) : super(ctx)
    private var attrs: AttributeSet? =null
    constructor(ctx: Context,attrs: AttributeSet) : super(ctx, attrs){
        this.attrs = attrs
    }


    init{
        val st = 12
        val s = st.toString()

    }

}
