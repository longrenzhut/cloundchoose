package com.haiqi.base.common.adapter.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by zhutao on 2017/8/10.
 */
class BaseVpAdapter<T>(fm: FragmentManager, 
                       private val data: MutableList<FraModel<T>>)
    : FragmentPagerAdapter(fm) {


    fun getDatas(): MutableList<FraModel<T>> = data

    override fun getPageTitle(position: Int): CharSequence {
        return data[position].name
    }

    override fun getItem(position: Int): Fragment {
        return data[position].fra
    }

    override fun getCount(): Int {
        return data.size
    }
}
