package com.haiqi.base.http

import com.haiqi.base.utils.ObservableSet
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Created by zhutao on 2017/9/14.
 * 数据请求合并请求
 */
class ZipRequest<T,R>{


    data class Results<T,R>(var t: T,var r: R)

    fun zip(s: Observable<BaseResult<T>>,r: Observable<BaseResult<R>>){

        Observable.zip(s,r,object : BiFunction<BaseResult<T>, BaseResult<R>, Results<T,R>>{
            override fun apply(t1: BaseResult<T>, t2: BaseResult<R>): Results<T, R> {

                return Results(t1.data,t2.data)
            }

        })
                .ObservableSet()
                .subscribe({
                    it?.t

                })

    }
}