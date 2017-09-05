package com.haiqi.base.http

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Administrator on 2017/7/20.
 */
interface BaseService {

    @FormUrlEncoded
    @POST("{url}")
    fun post(@Path("url") path: String,@FieldMap fields: Map<String,@JvmSuppressWildcards Any>):
            Observable<ResponseBody>
}