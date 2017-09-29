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

//    home?inviteCode=yunxuan
    @GET()
    fun get(@Url url: String): Observable<ResponseBody>

   /* @GET("{url}?")
    fun getTest(@Path(value = "url", encoded = true) url: String,
                @Query("inviteCode") name: String): Observable<ResponseBody>*/

    @GET("{url}?")
    fun get(@Path(value = "url", encoded = true) url: String,
                @QueryMap querys: Map<String,@JvmSuppressWildcards Any>): Observable<ResponseBody>
}