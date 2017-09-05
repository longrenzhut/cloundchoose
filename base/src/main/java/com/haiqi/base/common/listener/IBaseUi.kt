package com.ssdf.highup.base.interf

/**
 * Created by Administrator on 2017/5/26.
 */
interface IBaseUi {

    fun getLayoutId():Int

    fun initView()

    open fun setPresenter(){}

    fun requestData()

}