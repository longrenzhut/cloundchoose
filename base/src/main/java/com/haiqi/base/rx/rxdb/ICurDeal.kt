package com.haiqi.base.rx.rxdb

import android.database.Cursor

/**
 * Created by Administrator on 2017/9/11.
 */
interface ICurDeal<T>{
    fun  getSql(): String

    fun getDataFromCur(cur: Cursor?): T?
}