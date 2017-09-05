package com.haiqi.base.common.adapter.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by zhutao on 2017/8/10.
 */
class BaseVpAdapter<T>(fm: FragmentManager, val list: MutableList<FraModel<T>>): FragmentPagerAdapter(fm) {


    fun getDatas(): MutableList<FraModel<T>> = list

    override fun getPageTitle(position: Int): CharSequence {
        return list.get(position).name
    }

    override fun getItem(position: Int): Fragment {
        return list.get(position).fra
    }

    override fun getCount(): Int {
        return list.size
    }
}
