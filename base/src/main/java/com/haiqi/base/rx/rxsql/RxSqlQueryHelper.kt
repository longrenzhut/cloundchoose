package com.haiqi.base.rx.rxsql

import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable

/**
 * Created by zhutao on 2017/9/8.
 */
class RxSqlQueryHelper<T>(val curDeal: ICurDeal<T>): ObservableOnSubscribe<T>{


    fun setOpen(isOpen: Boolean):RxSqlQueryHelper<T>{
        DBManager.get().setOpenLoacalDb(isOpen)
        return this
    }

    override fun subscribe(e: ObservableEmitter<T>) {


        val sql = curDeal.getSql()
        val cur = DBManager.get().getConnection()?.query(sql)
        val result = curDeal.getDataFromCur(cur)
        cur?.close()
        DBManager.get().getConnection()?.closeDB()
        result?.let{
            e.onNext(it)
        }
        e.onComplete()

        e.setDisposable(object : Disposable{
            override fun isDisposed(): Boolean {
                return true
            }

            override fun dispose() {
                cur?.close()
                DBManager.get().getConnection()?.closeDB()
            }
        })
    }

}