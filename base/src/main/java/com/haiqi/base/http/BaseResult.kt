package com.haiqi.base.http

/**
 * Created by Administrator on 2017/5/27.
 */
 class BaseResult<T>(val retcode: Int,val msg: String,val data: T)