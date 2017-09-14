package com.haiqi.base.rx.rxsql

import android.database.SQLException
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable

/**
 * Created by zhutao on 2017/9/12.
 * 执行sql语句 比如一些 删除数据库
 * 删除一条数据 插入一条数据
 */

class RxSqlHelper(val sql: String): ObservableOnSubscribe<Int> {


    fun setOpen(isOpen: Boolean):RxSqlHelper{
        DBManager.get().setOpenLoacalDb(isOpen)
        return this
    }

    override fun subscribe(e: ObservableEmitter<Int>) {

        try{
            DBManager.get().getConnection()?.execSql(sql)
            e.onNext(1)
        }
        catch (ex: SQLException){
            ex.printStackTrace()
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
        finally {
            DBManager.get().getConnection()?.closeDB()
            e.onComplete()
        }

        e.setDisposable(object : Disposable {
            override fun isDisposed(): Boolean {
                return true
            }

            override fun dispose() {
                DBManager.get().getConnection()?.closeDB()
            }
        })
    }

}
