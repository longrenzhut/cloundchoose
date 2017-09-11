package com.haiqi.base.rx.rxdb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import com.haiqi.base.common.application.BaseApp

/**
 * Created by zhutao on 2017/9/8.
 * 数据库管理工具
 */

class DBManager {

    companion object {

        const val VERSION = 1
        const val dbname = "2017061201.db"

        private val instance by lazy{
            DBManager()
        }

        fun get(): DBManager = instance
    }

    private lateinit var ctx: Context

    fun init(ctx: Context): DBManager{
        this.ctx = BaseApp.getApp()
        return this
    }


    private var path: String? = ""
    /**
     * 设置本地数据库路径
     *  没有设置 默认缓存里面的数据库路径
     */
    fun setPath(path: String): DBManager{
        this.path = path
        return this
    }

//    private var dbname = ""

    fun setDbname(path: String): DBManager{
        this.path = path
        return this
    }

    fun setDbname(){}

    private val dbHeler by lazy{
        val dpath = Environment.getExternalStorageDirectory().toString() + "/highup/"
        DbHelper(ctx, dbname, null, VERSION,dpath)
    }

    fun getConnection(): DbHelper?{
        return dbHeler
    }

    fun closeConnection(){
        dbHeler.getSqlDb()?.let{
            it.close()
        }
    }

}

