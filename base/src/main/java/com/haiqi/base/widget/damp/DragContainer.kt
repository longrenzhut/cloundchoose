package com.haiqi.base.widget.damp

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.haiqi.base.R
import com.haiqi.base.utils.anim.AnimHelper
import com.haiqi.base.utils.dip2px

/**
 * Created by zhutao on 2017/8/16.
 * 阻尼效果
 */

class DragContainer(ctx: Context,attrs: AttributeSet?): ViewGroup(ctx,attrs){


    private lateinit var mDragHelper: ViewDragHelper


    var contentView: View? = null

    var moreView: View? = null

    var containerWidth = 0
    var containerHeight = 0

    var dragleft = false
    var dragtop = false
    var dragright = false
    var dragbottom = false

    var direction = 1

    private val helper by lazy(LazyThreadSafetyMode.NONE) {
        ContainerHelper()
    }

    init{

        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.dragcontainer)
        dragleft = typedArray.getBoolean(R.styleable.dragcontainer_drag_left,false)
        dragtop = typedArray.getBoolean(R.styleable.dragcontainer_drag_top,false)
        dragright = typedArray.getBoolean(R.styleable.dragcontainer_drag_right,false)
        dragbottom = typedArray.getBoolean(R.styleable.dragcontainer_drag_bottom,false)
        direction = typedArray.getInteger(R.styleable.dragcontainer_drag_direction,1)
        typedArray.recycle()

        mDragHelper = ViewDragHelper.create(this,
                object : ViewDragHelper.Callback(){

                    override fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
                        // 修整 child 水平方向上的坐标，left 指 child 要移动到的坐标，dx 相对上次的偏移量

                        if((dragleft && delayX < 0) || (dragright && delayX > 0)) {

                            moreView?.let {
                                val newValue =
                                        if (direction == 0) {
                                            if (left > it.width)
                                                it.width
                                            else
                                                (child?.left ?: 0) + dx / 2
                                        }
                                        else if(direction == 2){
                                            if (-left > it.width)
                                                -it.width
                                            else
                                                (child?.left ?: 0) + dx / 2
                                        }
                                        else{
                                            (child?.left ?: 0) + dx / 2
                                        }

                                return newValue
                            }

                            return (child?.left ?: 0) + dx / 2
                        }
                        return 0
                    }


                    override fun clampViewPositionVertical(child: View?, top: Int, dy: Int): Int {
                        // 修整 child 垂直方向上的坐标，top 指 child 要移动到的坐标，dy 相对上次的偏移量
                        if((dragtop && delayY < 0 && helper.isTop())
                                || (dragbottom && delayY > 0 && helper.isBottom())) {

                            moreView?.let {
                                val newValue =
                                        if (direction == 1) {
                                            if (top > it.height)
                                                it.height
                                            else
                                                (child?.top ?: 0) + dy / 2
                                        }
                                        else if(direction == 3){
                                            if (-top > it.height)
                                                -it.height
                                            else
                                                (child?.top ?: 0) + dy / 2
                                        }
                                        else{
                                            (child?.top ?: 0) + dy / 2
                                        }

                                return newValue
                            }

                            return (child?.top ?: 0) + dy / 2
                        }

                        return 0
                    }


                    override fun tryCaptureView(child: View?, pointerId: Int): Boolean {
                        // 决定了是否需要捕获这个 child，只有捕获了才能进行下面的拖拽行为
                        return child == contentView
                    }

                    // 手指释放时的回调
                    override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
                        super.onViewReleased(releasedChild, xvel, yvel)

                        //滑动到原位
                        mDragHelper.settleCapturedViewAt(0,0);
//                        mDragHelper.smoothSlideViewTo(contentView, 0, 0)
                        invalidate();
                    }

                    override fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
                        moreView?.let{
                            it.offsetLeftAndRight(dx)
                            it.offsetTopAndBottom(dy)
                        }

                        // 如果不重绘，拖动的时候，其他View会不显示
//                        ViewCompat.postInvalidateOnAnimation(this@DragContainer)
                    }

                    override fun onViewCaptured(capturedChild: View?, activePointerId: Int) {
                        //重定位 child
                        super.onViewCaptured(capturedChild, activePointerId)
//                        capturedChild?.let{
//                            mDragOriLeft = it.left;
//                            mDragOriTop = it.top;
//                        }
                    }

                    override fun getViewVerticalDragRange(child: View?): Int {
                        // 这个用来控制拖拽过程中松手后，自动滑行的速度
                        return dip2px(50)
                    }
                })

        helper.setContainer(object : IContainer{
            override fun getContainer(): View? {
                return contentView
            }

        })
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        contentView = getChildAt(0)
        if(childCount == 2)
            moreView = getChildAt(1)
    }



    /*   switch (mode){
           case MeasureSpec.UNSPECIFIED: //wrap_content
           // 没有指定大小，设置为默认大小
           properSize = defaultSize;
           break;
           case MeasureSpec.EXACTLY: //50dp
           // 固定大小，无需更改其大小
           properSize = size;
           break;
           case MeasureSpec.AT_MOST:// match_content
           // 此处该值可以取小于等于最大值的任意值，此处取最大值的1/4
           properSize = size / 4;
       }*/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        this.measureChildren(widthMeasureSpec, heightMeasureSpec)

        contentView?.let{
            val widths = if (widthMode == MeasureSpec.EXACTLY)
                widthSize
            else
                it.measuredWidth

            val heights = if (heightMode == MeasureSpec.EXACTLY)
                heightSize
            else
                it.measuredHeight

            this.setMeasuredDimension(widths, heights)
        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.containerWidth = w
        this.containerHeight = h
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        contentView?.layout(0, 0, this.containerWidth, this.containerHeight)
        moreView?.let{
            val loactions = when(direction){
                0->{//left
                    listOf(-it.measuredWidth,0,0,containerHeight)
                }
                1->{//top
                    listOf(0,-it.measuredHeight,containerWidth,0)
                }
                2->{//right
                    listOf(containerWidth,0,containerWidth + it.measuredWidth,containerHeight)
                }
                else->{//bottom
                    listOf(0,containerHeight,containerWidth,containerHeight + it.measuredHeight)
                }
            }

            it.layout(loactions[0],loactions[1],loactions[2], loactions[3])
        }
    }

    var curX = 0f
    var curY = 0f

    var delayX = 0f //负数向右拉
    var delayY = 0f//负数向下拉

    /**
     * 获取滑动方向
     */
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean{

        event?.let{
            when(it.action){

                MotionEvent.ACTION_DOWN->{
                    curX = it.rawX
                    curY = it.rawY
                }
                MotionEvent.ACTION_MOVE->{
                    delayX = curX - it.rawX
                    delayY = curY - it.rawY
                }
                MotionEvent.ACTION_UP->{

                }
            }
        }

        return super.dispatchTouchEvent(event)
    }


    /** 是否应该拦截 children 的触摸事件，
     *只有拦截了 ViewDragHelper 才能进行后续的动作
     *
     *将它放在 ViewGroup 中的 onInterceptTouchEvent() 方法中就好了
     **/
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {


        return mDragHelper.shouldInterceptTouchEvent(ev)
    }

    /** 处理 ViewGroup 中传递过来的触摸事件序列
     *在 ViewGroup 中的 onTouchEvent() 方法中处理
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragHelper.processTouchEvent(event)
        return true
    }


    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }
}