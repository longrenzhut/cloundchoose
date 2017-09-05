package com.haiqi.base.common.adapter.viewpager

import com.haiqi.base.common.fragment.DelegateFra


/**
 * Created by Administrator on 2017/8/10.
 */
data class FraModel<T>(var name: String, var fra: DelegateFra, var data: T)