package com.haiqi.yx.entry

import com.alibaba.android.arouter.facade.annotation.Route
import com.haiqi.base.common.activity.DelegateAct
import com.haiqi.yx.R

/**
 * Created by zhutao on 2017/9/13.
 */

class TestMDAct: DelegateAct(){

    override fun getLayoutId(): Int {
        return R.layout.act_test_md
    }

    override fun initView() {
    }

    override fun requestData() {
    }

}