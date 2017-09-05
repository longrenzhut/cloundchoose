package com.haiqi.base.common.adapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import org.jetbrains.anko.find

/**
 * Created by zhutao on 2017/5/27.
 */
open class BaseViewHolder(v : View): RecyclerView.ViewHolder(v){

    private val mViews by lazy { SparseArray<View>() }

    fun <T: View> getView(viewId: Int): T {
        var view = mViews.get(viewId)
        view.let{
            view = itemView.find(viewId)
            mViews.put(viewId, view)
        }

        return view as T
    }

}
