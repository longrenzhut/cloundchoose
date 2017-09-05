package com.haiqi.base.cache



/**
 * Created by zhutao on 2017/6/7.
 */
object CacheHelper{


    fun get():Cache =
            Cache()

    /**
     * 不清除缓存
     */
    fun getVal():Cache = Cache("unclear")



    ///////////////用来表示状态

    fun change(key: String){
        getVal().put(key)
    }

    fun isChanged(key: String): Boolean {
        val v = getVal().getString(key).isNullOrEmpty()
        if(v)
            getVal().remove(key)
        return v
    }

    fun isEmpty(key: String): Boolean = getVal().getString(key).isNullOrEmpty()
}