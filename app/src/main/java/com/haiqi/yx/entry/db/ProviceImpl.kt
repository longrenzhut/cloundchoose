package com.haiqi.yx.entry.db

import android.database.Cursor
import com.haiqi.base.rx.rxdb.ICurDeal

/**
 * Created by Administrator on 2017/9/11.
 */


class ProviceImpl: ICurDeal<MutableList<AddrModel>>{
    override fun getSql(): String {
        return "select country_id,name from hb_country"
    }

    override fun getDataFromCur(cur: Cursor?): MutableList<AddrModel>? {
        cur?.let{
            val l = mutableListOf<AddrModel>()
            var model: AddrModel
            while (it.moveToNext()) {
                if ("香港" != it.getString(1) && "澳门" != it.getString(1)
                        && "台湾" != it.getString(1)) {
                    model = AddrModel(it.getInt(0),it.getString(1))
                    l.add(model)
                }
            }
            return l
        }
        return null
    }


}