package com.haiqi.base.rx.rxsql

import android.database.SQLException
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable

/**
 * Created by zhutao on 2017/9/12.
 * 删除数据库
 */

class RxSqlDeleteHelper(val table: String): ObservableOnSubscribe<Int?> {


    fun setOpen(isOpen: Boolean): RxSqlDeleteHelper {
        DBManager.get().setOpenLoacalDb(isOpen)
        return this
    }

    private var where = ""
    private var whereArg: Array<String>? = null

    /**
     *  根据条件删除数据
     *  两种写法
     *  setWhere("id=?",Array<String>(”12"))
     *  setWhere("id=123",null)
     */
    fun setWhere(where: String, whereArg: Array<String>? = null): RxSqlDeleteHelper {
        this.where = where
        this.whereArg = whereArg
        return this
    }

    override fun subscribe(e: ObservableEmitter<Int?>) {
        try {
            val result = DBManager.get().
                    getConnection()?.delete(table, where, whereArg)
            result?.let {
                e.onNext(it)
            }
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