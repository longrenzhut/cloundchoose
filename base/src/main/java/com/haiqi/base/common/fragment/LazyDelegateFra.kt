package com.haiqi.base.common.fragment

import android.os.Bundle
import android.view.View

/**
 * Created by zhutao on 2017/8/8.
 * 实现viewpager 懒加载
 */
abstract class LazyDelegateFra<T>: DelegateFra(){

    //Fragment的View加载完毕的标记
    private var isPrepared: Boolean = false

    //Fragment对用户可见的标记
    private var isUIVisible: Boolean = false


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true
            load()
        } else {
            isUIVisible = false
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPrepared = true
        load()
    }


    private fun load() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,
        // 并且会在onCreateView执行前回调,
        // 必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isPrepared && isUIVisible) {
            lazyLoad(list,isSuccess)
            //数据加载完毕,恢复标记,防止重复加载
            isPrepared = false
            isUIVisible = false

        }
    }

    private var list:MutableList<T>? = null
    private var isSuccess = false;

    fun setLazyData(list: MutableList<T>?){
        this.list = list;
        isSuccess = false;
    }

    /**
     * 懒加载
     * 当fragment的控件加载完毕并展示的时候调用
     */
    abstract fun lazyLoad(list: MutableList<T>?,isSuccess: Boolean)

}