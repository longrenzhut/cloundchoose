package com.haiqi.base.http

/**
 * Created by Administrator on 2017/5/31.
 */
class Params {

  /*  val map by lazy {
        hashMapOf<String, Any>();
    }
*/
    private val map by lazy {
      mutableMapOf<String, Any>()
  }

    init {
        map.put("customerid", "5850")
        map.put("platform", "android")
        map.put("passkey", "91cf51846a5b10fc919949851001a691ad4a5d99")
    }

    fun put(key: String,value: Any): Params
     = this.apply { map.put(key,value) }

    fun getParams():MutableMap<String, Any> = map

}