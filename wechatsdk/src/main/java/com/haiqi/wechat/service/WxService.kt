package com.haiqi.wechat.service

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Administrator on 2017/9/1.
 */
interface WxService {

    @GET("access_token?")
    fun requestToken(@Query("appid") appid: String,
                     @Query("secret") secret: String,
                     @Query("code") code: String,
                     @Query("grant_type") grant_type: String):
            Observable<ResponseBody>


    @GET("userinfo?")
    fun requestUser(@Query("access_token") access_token: String,
                    @Query("openid") openid: String):
            Observable<ResponseBody>

}