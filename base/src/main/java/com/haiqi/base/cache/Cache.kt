package com.haiqi.base.cache
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.haiqi.base.common.application.BaseApp
/**
 * Created by zhutao on 2017/6/6.
 */
class Cache(val name: String="clear"){

    val cache: SharedPreferences by lazy{
        val app = BaseApp.getApp()
        app.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    val cacheMap by lazy{
        mutableMapOf<String,String>()
    }

    fun putInt(key: String,value:Int = 1){
        put(key,value.toString())
    }

    fun put(key: String,value:String = "1"){
        val newValue = cacheMap.get(key)
        newValue?.let{
            if(newValue == value)
                return
        }
        cache.edit().putString(key,value).commit()
    }

    fun getString(key: String): String{
        val newValue = cacheMap.get(key)
        newValue?.let{
            return newValue
        }
        val value = cache.getString(key,"")
        cacheMap.put(key,value)
        return value
    }

    fun getInt(key: String): Int{
        return getString(key).toInt()
    }

    fun clear(){
        cacheMap.clear()
        cache.edit().clear().commit()
    }

    fun remove(key: String){
        cacheMap.remove(key)
        cache.edit().remove(key).commit()
    }

    fun <T> putModel(key: String, model: T) {
        put(key, Gson().toJson(model))
    }

    inline fun <reified T> getModel(key: String): T{
        val value = getString(key);
        return  Gson().fromJson<T>(value, T::class.java);
    }
}