package com.haiqi.base.widget.damp

/**
 * Created by zhutao on 2017/8/17.
 */
interface IDragContainer {

    fun onViewPositionChanged( left: Int, top: Int, dx: Int, dy: Int)

    fun onViewReleased(left: Int, top: Int, dx: Int, dy: Int)
}