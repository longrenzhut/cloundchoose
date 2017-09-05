package com.haiqi.base.widget.textview

import android.text.SpannableString
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import android.widget.TextView

/**
 * Created by zhutao on 2017/8/14.
 * 设置textview 中 文本字体的样式
 */
class TextSpan(val text: String,val tv: TextView){

    private val span by lazy(LazyThreadSafetyMode.NONE){
         SpannableString(text)
    }

    fun setTextSapn(tagText: String,style: Int): TextSpan{
        val index = text.indexOf(tagText)
        span.setSpan(TextAppearanceSpan(tv.getContext(),
                style), index, index + tagText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return this
    }

    fun setText(){
        tv.text = span
    }
}