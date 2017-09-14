package com.haiqi.base.rx.rxsql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
     * 获取缓存数据库db对象
     *    sqLiteDatabase = ctx.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
     */
    override fun getReadableDatabase(): SQLiteDatabase {
        if(!path.isNullOrEmpty()) {
            val f = File(path + name)
            if (f.exists())
                return SQLiteDatabase.openDatabase(f.path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS)
        }
        return  super.getReadableDatabase()
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        if(!path.isNullOrEmpty()) {
            val f = File(path + name)
            if (f.exists())
                return SQLiteDatabase.openOrCreateDatabase(f.path, null)
        }
        return super.getWritableDatabase()
    }

    private var db: SQLiteDatabase? = null


    fun query(sql: String): Cursor?{
        db = readableDatabase
        return db?.rawQuery(sql,null)
    }

    fun insert(tablename: String,cv: ContentValues): Long?{
        db = writableDatabase
        return db?.insert(tablename, null, cv)
    }

    fun update(tablename: String,cv: ContentValues,
               whereClause: String,whereArg: Array<String>?): Int?{
        db = writableDatabase

        return db?.update(tablename, cv,whereClause,whereArg)
    }

    fun execSql(sql: String){
        db = writableDatabase
        db?.execSQL(sql)
    }

    fun delete(tablename: String,
               whereClause: String,whereArg: Array<String>?): Int?{
        db = writableDatabase
        return db?.delete(tablename,whereClause,whereArg)
    }


    fun closeDB(){
        db?.close()
    }

    fun <T> createTable(c: Class<T>) {
        val TABLE_NAME = JavaReflectUtil.getClassName(c)
        val column = JavaReflectUtil.getAttributeNames(c)
        val type = JavaReflectUtil.getAttributeType(c)
        var sql = "CREATE TABLE IF NOT EXISTS $TABLE_NAME("
        sql += "id  Integer PRIMARY KEY AUTOINCREMENT,"
        for (i in column.indices) {
            if ("id" != column[i]) {
                if (i != column.size - 1) {
                    sql += column[i] + " " + type.javaClass.simpleName + ","
                } else {
                    sql += column[i] + " " + type.javaClass.simpleName
                }
            }
        }
        sql += ")"
        db?.execSQL(sql)
    }

    fun drop(tablename: String){
        val sql = "DROP TABLE " +
                "IF EXISTS " +
                tablename
        db?.execSQL(sql)
    }

}
