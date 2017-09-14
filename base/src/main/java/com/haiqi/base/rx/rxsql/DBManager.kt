package com.haiqi.base.rx.rxsql

import com.haiqi.base.common.application.BaseApp

/**
 * Created by zhutao on 2017/9/8.
 * 数据库管理工具
 */

class DBManager {

    companion object {

        const val VERSION = 1

        private val instance by lazy{
            DBManager()
        }

        fun get(): DBManager = instance
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

    private var dbname = "2017061201.db"

    fun setDbname(dbname: String): DBManager{
        this.dbname = dbname
        return this
    }

    private var isOpenLoacaldb = true
    /**
     * true访问的是本地数据库
     * false 缓存数据库
     */
    fun setOpenLoacalDb(isOpenLoacaldb: Boolean){
        this.isOpenLoacaldb = isOpenLoacaldb
    }

    /*val dpath = Environment.getExternalStorageDirectory().toString() + "/highup/"*/

    private val dbLoacalHeler by lazy{
        DbHelper(BaseApp.getApp(), dbname, null, VERSION,path)
    }

    private val dbHeler by lazy{
        DbHelper(BaseApp.getApp(), dbname, null, VERSION)
    }

    fun getConnection(): DbHelper?{
        if(isOpenLoacaldb)
            return dbLoacalHeler
        else
            return dbHeler
    }

}

