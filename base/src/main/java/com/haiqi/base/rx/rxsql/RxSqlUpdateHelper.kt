package com.haiqi.base.rx.rxsql

import android.content.ContentValues
import android.database.SQLException
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable

/**
 * Created by zhutao on 2017/9/12.
 * 修改数据库
 */
class RxSqlUpdateHelper(val table: String): ObservableOnSubscribe<MutableList<Int?>> {


    fun setOpen(isOpen: Boolean): RxSqlUpdateHelper {
        DBManager.get().setOpenLoacalDb(isOpen)
        return this
    }

    private var cv: ContentValues? = null

    fun update(cv: ContentValues): RxSqlUpdateHelper {
        this.cv = cv
        return this
    }

    private var datas: MutableList<ContentValues>? = null

    fun updates(datas: MutableList<ContentValues>): RxSqlUpdateHelper {
        this.datas = datas
        return this
    }


    private val results by lazy {
        mutableListOf<Int?>()
    }

    private var where = ""
    private var whereArg: Array<String>? = null

    /**
     *  根据条件修改数据库
     *  两种写法
     *  setWhere("id=?",Array<String>(”12"))
     *  setWhere("id=123",null)
     */
    fun setWhere(where: String, whereArg: Array<String>? = null): RxSqlUpdateHelper {
        this.where = where
        this.whereArg = whereArg
        return this
    }

    override fun subscribe(e: ObservableEmitter<MutableList<Int?>>) {
        try {
            cv?.let {
                val result = DBManager.get().getConnection()?.update(table, it, where, whereArg)
                results.add(result)
            }
            datas?.let {
                for (data in it) {
                    val result = DBManager.get().getConnection()?.update(table, data, where, whereArg)
                    results.add(result)
                }
            }
            e.onNext(results)
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
