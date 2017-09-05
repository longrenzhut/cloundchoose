package com.haiqi.base.widget.recyclerview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.haiqi.base.common.adapter.BaseAdapter
import com.haiqi.base.common.adapter.LoadMoreAdapter

/**
 * Created by zhutao on 2017/7/7.
 * 封装了通用的
 */
open class SuperRecyclerView(ctx: Context,attrs: AttributeSet?): RecyclerView(ctx,attrs){

    constructor(ctx: Context): this(ctx,null)

    private var isLoading =  false
    var page = 1;


    val adapters by lazy(LazyThreadSafetyMode.NONE){
        val layoutManager = VirtualLayoutManager(context);
        this.layoutManager = layoutManager;

        val viewPool = RecyclerView.RecycledViewPool();
        this.recycledViewPool = viewPool;
        viewPool.setMaxRecycledViews(0, 10);

        DelegateAdapter(layoutManager)
    }

    val mloadMoreAdapter by lazy(LazyThreadSafetyMode.NONE){
        LoadMoreAdapter()
    }


    /**
     * 设置无线加载更多的适配器
     */
    fun <T> setAdapter(adapter: BaseAdapter<T>, list: MutableList<T>?){
        adapter.clear()
        setLoadMore(adapter,list)
    }

    fun <T> setLoadMore(adapter: BaseAdapter<T>,list: MutableList<T>?){
        val size = list?.size ?: 0
        if (size >= 10) {
            mloadMoreAdapter.state = 1
        }
        else {
            mloadMoreAdapter.state  = 3
        }
        mloadMoreAdapter.notifyDataSetChanged()
        isLoading = false;
        adapter.notifyItems(list!!)
    }

    fun setloadMore(listener: OnLoadMoreListener) {
        setloadMore{
            listener.OnLoadMore(page)
        }
    }

//    private var OnLoadMore: ((page: Int) -> Unit)? = null

    fun setloadMore(loadMore: (Int)->(Unit)) {
        adapters.addAdapter(mloadMoreAdapter)
        adapter = adapters
        this.addOnScrollListener(object : OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val lm =  recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition = lm.findLastVisibleItemPosition()
                if(lastVisiblePosition == adapters.itemCount - 1){
                    if(mloadMoreAdapter.state == 1){
                        if(!isLoading){
                            isLoading = true;
                            loadMore(++page)
                        }
                    }
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {

            }
        });

        mloadMoreAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener<Int> {
            override fun onItemClick(itemView: View, pos: Int, model: Int) {
                if(mloadMoreAdapter.state == 2){
                    mloadMoreAdapter.state = 1;
                    isLoading = true;
                    mloadMoreAdapter.notifyDataSetChanged()
                    loadMore(page)
                }
            }

        })
    }

    interface OnLoadMoreListener{
        fun OnLoadMore(page: Int)
    }

    fun loadFailed() {
        isLoading = false
        mloadMoreAdapter.state = 2
        mloadMoreAdapter.notifyDataSetChanged()
    }
}
