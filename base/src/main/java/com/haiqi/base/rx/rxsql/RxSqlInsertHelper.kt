package com.haiqi.base.rx.rxsql

import android.content.ContentValues
import android.database.SQLException
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable

/**
 * Created by Administrator on 2017/9/11.
 */
class RxSqlInsertHelper(val table: String): ObservableOnSubscribe<MutableList<Long?>> {


    fun setOpen(isOpen: Boolean): RxSqlInsertHelper{
        DBManager.get().setOpenLoacalDb(isOpen)
        return this
    }

    private var cv: ContentValues? = null

    fun insert(cv: ContentValues): RxSqlInsertHelper{
        this.cv = cv
        return this
    }

    private var datas: MutableList<ContentValues>? = null

    fun inserts(datas: MutableList<ContentValues>): RxSqlInsertHelper{
        this.datas = datas
        return this
    }


    private val results by lazy {
        mutableListOf<Long?>()
    }


    override fun subscribe(e: ObservableEmitter<MutableList<Long?>>) {
        try {
            cv?.let {
                val result = DBManager.get().getConnection()?.insert(table, it)
                results.add(result)
            }
            datas?.let{
                for(data in it){
                    val result = DBManager.get().getConnection()?.insert(table, data)
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