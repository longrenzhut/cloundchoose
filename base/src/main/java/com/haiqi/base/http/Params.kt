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


    constructor(){
        map.put("customerid", "5850")
        map.put("platform", "android")
        map.put("passkey", "91cf51846a5b10fc919949851001a691ad4a5d99")
    }

    /**
     * get请求
     */
    constructor(isGet: Boolean)


    fun put(key: String,value: Any): Params
            = this.apply { map.put(key,value) }

    fun getParams():MutableMap<String, Any> = map




    //---- 设置get请求的参数

    fun getParamsOnGet(name: String): String{
        val url = StringBuffer(name)
        var index = 0
        for((key,value) in map){
            if(index == 0)
                url.append("?$key=$value")
            else
                url.append("&$key=$value")
            index ++
        }
        return url.toString()
    }

}