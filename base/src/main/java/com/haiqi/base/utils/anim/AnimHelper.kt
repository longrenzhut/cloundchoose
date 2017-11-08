package com.haiqi.base.utils.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * Created by zhutao on 2017/8/15.
 * 动画帮助类
 */

class AnimHelper(val onAnimEnd: (()-> Unit)? = null,val OnAnimStart: (()-> Unit)? = null){


    fun alpha(view: View,duration: Long = 300,vararg values: Float) {
        anim(view, "alpha", duration,*values)
    }

    fun translationY(view: View,duration: Long = 300, vararg values: Float) {
        anim(view, "translationY",duration, *values)
    }

    fun translationX(view: View,duration: Long = 300, vararg values: Float) {
        anim(view, "translationX",duration, *values)
    }

    fun translateViewY(view: View,duration: Long = 300, vararg values: Float) {
        anim(view, "y",duration, *values)
    }


    fun anim(view: View,propertyName: String,duration: Long = 300,vararg values: Float){
        val anim = ObjectAnimator
                .ofFloat(view, propertyName, *values)
                .setDuration(duration)
        anim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                onAnimEnd?.let{
                    it()
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                OnAnimStart?.let{
                    it()
                }
            }

        })
        anim.start()
    }


    class ViewWrapper(val target: View,val onAniming: ((Int)-> Unit)? = null) {

        fun getWidth(): Int {
            return target.getLayoutParams().width
        }

        fun setWidth(width: Int) {
            onAniming?.let{
                it(width)
            }
            target.layoutParams.width = width
            target.requestLayout()
        }
    }

    /**
     * 拉伸 以及动画
     */
    fun stretching(view: View,width: Int,duration: Long = 800,onAniming: ((Int)-> Unit)? = null){
        val viewWrapper =  ViewWrapper(view,onAniming)
        val anim =  ObjectAnimator.ofInt(viewWrapper, "width",width)
        .setDuration(duration)
        anim.start()
    }


    fun startAnim(start: Float, end: Float,duration: Long = 1000, onAniming: ((Float)-> Unit)? = null,delay: Long = 10) {
        val animator = ObjectAnimator.ofFloat(start, end)
        animator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                onAniming?.let{
                    animation?.let{
                        onAniming(it.animatedValue.toString().toFloat())
                    }
                }
            }

        })
        animator.startDelay = delay   //设置延迟开始
        animator.duration = duration
        animator.interpolator = LinearInterpolator()   //动画匀速
        animator.start()
    }

}