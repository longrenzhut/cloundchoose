package com.haiqi.base.widget.recyclerview.manager

import android.content.Context
import com.alibaba.android.vlayout.VirtualLayoutManager

/**
 * Created by Administrator on 2017/8/29.
 */

open class SuperLayoutMananger(ctx: Context, orientation: Int, reverseLayout
: Boolean): VirtualLayoutManager(ctx,orientation,reverseLayout) {

    constructor(ctx: Context, orientation: Int) : this(ctx, orientation, false)

    constructor(ctx: Context) : this(ctx, VERTICAL, false)

    init {

    }

    private var isScrollEnabled = true


    fun setScrollEnabled(flag: Boolean) {
        this.isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}