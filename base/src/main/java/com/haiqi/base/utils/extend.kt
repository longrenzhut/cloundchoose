package com.haiqi.base.utils

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.haiqi.base.R
import com.haiqi.base.common.adapter.viewpager.FraModel
import com.haiqi.base.common.application.BaseApp
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find
import java.text.DecimalFormat

/**
 * Created by zhutao on 2017/8/2.
 */

fun getResources(): Resources {
    return BaseApp.getApp().resources
}

fun getContext(): BaseApp{
    return BaseApp.getApp()
}

fun <T> Observable<T>.ObservableSet(): Observable<T> {
    subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    return this
}

@Throws(NoSuchFieldException::class, IllegalAccessException::class)
fun getField(toast: Any, fieldName: String): Any? {
    val field = toast.javaClass.getDeclaredField(fieldName)
    if (field != null) {
        field.isAccessible = true
        return field.get(toast)
    }
    return null
}

 fun showToast(msg: String?){
    if(msg.isNullOrEmpty())
        return
    val view = LayoutInflater.from(getContext()).inflate(R.layout.toast_text, null)
    view.find<TextView>(R.id.m_tv_text).text = msg
    val toast = Toast(getContext())
    toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, dip2px(120))
    toast.duration = Toast.LENGTH_SHORT
    try {
        val mTN: Any?
        mTN = getField(toast, "mTN")
        if (mTN != null) {
            val mParams = getField(mTN, "mParams")
            if (mParams != null && mParams is WindowManager.LayoutParams) {
                mParams.windowAnimations = R.style.AnimationToast
            }
        }
    } catch (e: Exception) {
        Log.d("UIToast", "Toast windowAnimations setting failed")
    }
    toast.view = view
    toast.show()
}


/**
 * 获取颜色值
 */
 fun getColor(color: Int): Int{

    return getResources().getColor(color)
}

 fun getActColor(color: Int): Int{

    return getResources().getColor(color)
}

 fun getDrawable(drawable: Int): Drawable
        = getResources().getDrawable(drawable)

 fun dip2px(value: Int):Int{
    return (value.toFloat() * CommonUtil.getScales()  + 0.5f).toInt()
}

fun dip2px(value: Float):Float{
    return (value * CommonUtil.getScales() + 0.5f)
}

fun px2dip(value: Int) : Int{
    return (value / CommonUtil.getScales() + 0.5f).toInt()
}


 fun getDimen(dimen: Int): Float{
    return getResources().getDimension(dimen)
}

 fun getDimenPixelOffset(dimen: Int): Int{
    return getResources().getDimensionPixelOffset(dimen)
}



fun String?.isEmpty(): Boolean {
    this?.let{
        if("" == it.trim())
            return true
        return false
    }
    return true
}

fun <E> MutableList<E>?.isEmpty(): Boolean{
    this?.let{
        if(size == 0)
            return true
        return false
    }
    return true
}


/*设置控件的显示和隐藏
* 1 显示  0 不显示 但是占位 -1 不显示
*/
 fun View?.setVisible(visible: Int){
    this?.let {
        val state = when (visible) {
            1 -> View.VISIBLE
            0 -> View.INVISIBLE
            else -> View.GONE
        }
        if (state == it.visibility)
            return
        it.visibility = state
    }
}



 fun Double?.double2Decimal(): Double{
    val l1 = Math.round((this?: 0.0) * 100) //四舍五入
    val ret = l1 / 100.0 //注意：使用 100.0 而不是 100
    return ret
}

 fun String?.str2Double(): Double{
    this?.let{
        return (it.toDouble()).double2Decimal()
    }
    return 0.00
}

 fun String?.saveto2DecimalStr(): String =
        this?.let{
            val d = it.str2Double()
            if (d < 1) {
                return "0" + DecimalFormat("#.00").format(d)
            }
            DecimalFormat("#.00").format(d)
        }?: "0.00"

 fun TextView?.setTvPrice(text: String,vararg values: String){
    this?.let {
        var text = ""
        for (i in values.indices) {
            if (i == 0)
                text = values[i] + "¥" + text.saveto2DecimalStr()
            else
                text += values[i]
        }
        it.text = text
    }
}

 fun TextView?.setTvColor(color: Int){
    if(0 == color)
        return
    this?.let{
        it.setTextColor(getColor(color))
    }
}


/**
 * dp 设置字体大小
 */
 fun TextView?.setTvSize(size: Int){
    if(0 == size)
        return
    this?.let{
        textSize = dip2px(size).toFloat()
    }
}

 fun <T> MutableList<T>?.size(): Int{
    this?.let{
        return it.size
    }
    return 0
}

/**
 * 设置view 的背景颜色
 */
fun View?.setBgColor(color: Int){
    this?.let{
        it.setBackgroundColor(resources.getColor(color))
    }
}
