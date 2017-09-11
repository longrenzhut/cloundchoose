package com.haiqi.base.rx.rxdb

import android.database.Cursor
import com.haiqi.base.utils.showToast
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

/**
 * Created by zhutao on 2017/9/8.
 */
class RxSqlQueryHelper<T>(val curDeal: ICurDeal<T>): ObservableOnSubscribe<T>{


    override fun subscribe(e: ObservableEmitter<T>) {
        val sql = curDeal.getSql()

        val cur = DBManager.get().getConnection()?.getSqlDb()?.rawQuery(sql,null)
        val result = curDeal.getDataFromCur(cur)

        result?.let{
            e.onNext(it)
        }
        e.onComplete()
    }

}