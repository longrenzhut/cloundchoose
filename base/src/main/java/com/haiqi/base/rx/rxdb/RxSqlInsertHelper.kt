package com.haiqi.base.rx.rxdb

import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

/**
 * Created by Administrator on 2017/9/11.
 */
class RxSqlInsertHelper<T>(val curDeal: ICurDeal<T>): ObservableOnSubscribe<T> {


    override fun subscribe(e: ObservableEmitter<T>) {
        val sql = curDeal.getSql()
        val params: Array<String>? = null
        val cur = DBManager.get().getConnection()?.getSqlDb()?.rawQuery(sql,params)
        val result = curDeal.getDataFromCur(cur)
        result?.let{
            e.onNext(it)
        }
        e.onComplete()
    }

}