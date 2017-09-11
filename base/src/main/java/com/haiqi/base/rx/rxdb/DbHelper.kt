package com.haiqi.base.rx.rxdb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Environment
import java.io.File

/**
 * Created by zhutao on 2017/9/8.
 * 帮组类
 */

class DbHelper(ctx: Context,val name: String,
               factory: SQLiteDatabase.CursorFactory?,
               val version: Int,val path: String? = ""): SQLiteOpenHelper(ctx,name,factory,version){



    override fun onCreate(db: SQLiteDatabase?) {
        db?.let{
            onUpgradeDatabase(it,db.version,version)
        }
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let{
            onUpgradeDatabase(it,oldVersion,newVersion)
        }
    }


    /**
     * 数据库更新,根据旧版本更新到新版本,这样做的优点是数据库更新更灵活
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    private fun onUpgradeDatabase(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //从旧版本N更新到到新版本M,
        //如从旧版本1更新到新版本4,就会执行1,2,3的数据库操作
        //从旧版本2更新到3,就会执行2的数据库操作
    }

    /**
     * Environment.getExternalStorageDirectory().toString()
     * + "/highup/"
     */
    override fun getReadableDatabase(): SQLiteDatabase {
        if(path.isNullOrEmpty()){
            val f =  File(path + name)
            if(f.exists())
                return SQLiteDatabase.openDatabase(f.path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS)
        }
        return  super.getReadableDatabase()
    }

//    override fun getWritableDatabase(): SQLiteDatabase {
//        return SQLiteDatabase.openOrCreateDatabase(f.getPath(), null)
//
//    }

    private var db: SQLiteDatabase? = null

    fun getSqlDb(): SQLiteDatabase? = db


    fun query(sql: String){
        db = readableDatabase
        db?.rawQuery(sql,null)
        db?.close()
    }

    fun insert(tablename: String,cv: ContentValues){
        db = writableDatabase
        db?.insert(tablename, null, cv)
        db?.close()
    }


}
