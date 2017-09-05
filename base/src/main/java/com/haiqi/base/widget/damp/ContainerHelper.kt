package com.haiqi.base.widget.damp

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebView
import android.widget.ScrollView

/**
 * Created by zhutao on 2017/8/16.
 */
class ContainerHelper(){


    private var view: IContainer? = null


    fun setContainer(view: IContainer){
        this.view = view
    }

    fun getContainer(): View?{
        view?.let {
            return it.getContainer()
        }
        return null
    }

    fun isTop(): Boolean =
            getContainer()?.let{
                if(it is RecyclerView)
                    isRecyclerViewBottom(it)
                else if(it is ScrollView){
                    isScrollViewTop(it)
                }
                else if(it is WebView)
                    it.scrollY <= 0
                else
                    true
            } ?: false


    private fun isRecyclerViewTop(recyclerView: RecyclerView): Boolean {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val childAt = recyclerView.getChildAt(0)
            if (childAt == null || firstVisibleItemPosition == 0 && childAt.top == 0) {
                return true
            }
        }
        return false
    }


    private fun isScrollViewTop(it: ScrollView): Boolean{
        if(it.childCount > 0) {
            val contentView = it.getChildAt(0)
            return it.scrollY <= 0 || contentView.height < it.height + it.scrollY;
        }
        return true
    }


    fun isBottom(): Boolean =
            getContainer()?.let{
                if(it is RecyclerView)
                    isRecyclerViewTop(it)
                if(it is ScrollView)
                    isScrollViewBottom(it)
                else if(it is WebView)
                    isWebViewBottom(it)
                else
                    true
            } ?: false


    fun isRecyclerViewBottom(recyclerView: RecyclerView): Boolean {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            val count = recyclerView.adapter.itemCount
            if (count == 0)
                return true
            //                int y =recyclerView.get();
            val childAt = recyclerView.getChildAt(count - 1)
            if (childAt == null || lastVisibleItemPosition == count) {
                return true
            }
        }
        return false
    }

    fun isScrollViewBottom(it: ScrollView): Boolean{

        if(it.childCount > 0) {
            val contentView = it.getChildAt(0)
            return  contentView.height <= it.height + it.scrollY
        }
        return true
    }

    fun isWebViewBottom(wv: WebView): Boolean

            = wv.height + wv.scrollY >= wv.contentHeight * wv.scale;
}