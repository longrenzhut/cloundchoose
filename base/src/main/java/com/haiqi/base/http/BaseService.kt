package com.haiqi.base.http

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by Administrator on 2017/7/20.
 */
interface BaseService {

    @FormUrlEncoded
    @POST("{url}")
    fun post(@Path("url") path: String,@FieldMap fields: Map<String,@JvmSuppressWildcards Any>):
            Observable<ResponseBody>

    @GET("home?inviteCode=yunxuan")
    fun get():
            Observable<ResponseBody>
}